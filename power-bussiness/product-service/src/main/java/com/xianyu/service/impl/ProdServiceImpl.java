package com.xianyu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.constant.BizConstant;
import com.xianyu.constant.CategoryConstant;
import com.xianyu.domain.Prod;
import com.xianyu.domain.ProdTag;
import com.xianyu.domain.ProdTagReference;
import com.xianyu.domain.Sku;
import com.xianyu.dto.ProdDto;
import com.xianyu.mapper.ProdMapper;
import com.xianyu.mapper.ProdTagMapper;
import com.xianyu.mapper.ProdTagReferenceMapper;
import com.xianyu.service.ProdService;
import com.xianyu.service.ProdTagReferenceService;
import com.xianyu.service.ProdTagService;
import com.xianyu.service.SkuService;
import com.xianyu.utils.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-25  19:05
 * @Description: ProdService实现类
 * @Version: 1.0
 */
@Service
@CacheConfig(cacheNames = "com.xianyu.service.impl.ProdServiceImpl")
public class ProdServiceImpl extends ServiceImpl<ProdMapper, Prod> implements ProdService {
    @Autowired
    private ProdMapper prodMapper;
    @Autowired
    private SkuService skuService;
    @Autowired
    private ProdTagService prodTagService;
    @Autowired
    private ProdTagReferenceService prodTagReferenceService;
    // @Autowired
    // private ProdTagMapper prodTagMapper;
    @Autowired
    private ProdTagReferenceMapper prodTagReferenceMapper;
    @Override
    @Cacheable(key = CategoryConstant.CATEGORY_ALL_KEY)
    public Page<Prod> loadProdWithPage(Page<Prod> page, Prod prod) {
        return prodMapper.selectPage(page,new LambdaQueryWrapper<Prod>()
                .eq(!ObjectUtils.isEmpty(prod.getStatus()),Prod::getStatus,prod.getStatus())
                .like(StringUtils.hasText(prod.getProdName()),Prod::getProdName,prod.getProdName())
                .orderByDesc(Prod::getUpdateTime));
    }

    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Integer addProd(ProdDto prodDto) {
        prodDto.setCreateTime(new Date());
        prodDto.setUpdateTime(new Date());
        // 【上架时间】
        if (prodDto.getStatus().equals(1)){
            prodDto.setPutawayTime(new Date());
        }
        prodDto.setShopId(AuthUtil.getShopId());
        prodDto.setVersion(1);
        prodDto.setSoldNum(0);
        prodDto.setDeliveryMode(JSON.toJSONString(prodDto.getDeliveryModeVo()));
        // 【插入商品表】
        int count = prodMapper.insert(prodDto);
        if (count>0){
            // 【插入Sku表】
            for (Sku sku : prodDto.getSkuList()) {
                sku.setCreateTime(new Date());
                sku.setProdId(prodDto.getProdId());
                sku.setUpdateTime(new Date());
                sku.setVersion(1);
            }
            boolean flag = skuService.saveBatch(prodDto.getSkuList());
            if (!flag){
                throw  new RuntimeException("插入Sku表失败");
            }
            List<ProdTagReference> tagReferenceList = new ArrayList<>();
            prodDto.getTagList().forEach(tagId->{
                ProdTagReference prodTagReference = new ProdTagReference();
                prodTagReference.setProdId(prodDto.getProdId());
                prodTagReference.setCreateTime(new Date());
                prodTagReference.setShopId(AuthUtil.getShopId());
                prodTagReference.setStatus((byte) 1);
                prodTagReference.setTagId(tagId);
                tagReferenceList.add(prodTagReference);
            });
            flag = prodTagReferenceService.saveBatch(tagReferenceList);
            if (!flag){
                throw  new RuntimeException("插入prodTagReference表(标签关联表)失败");
            }
        }
        return count;
    }
    @Transactional
    @Override
    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    public boolean deleteProd(Long id) {
        int count = prodMapper.deleteById(id);
        boolean remove=false;
        if (count>0){
            remove= skuService.remove(new LambdaQueryWrapper<Sku>()
                    .eq(Sku::getProdId, id));
            if (!remove){
                throw new RuntimeException("删除sku表失败");
            }
            remove = prodTagReferenceService.remove(new LambdaQueryWrapper<ProdTagReference>()
                    .eq(ProdTagReference::getProdId, id));
            if (!remove){
                throw new RuntimeException("删除ProdTagReference表失败");
            }
        }
        return remove;
    }

    @Transactional
    @Override
    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    public void updateProd(ProdDto prodDto) {
        boolean flag = updateById(prodDto);
        if (flag){
            List<Sku> skuList = prodDto.getSkuList();
            List<Long> tagList = prodDto.getTagList();
            // 【先删除再添加】
            skuService.remove(new LambdaQueryWrapper<Sku>()
                    .eq(Sku::getProdId, prodDto.getProdId()));
            prodTagReferenceService.remove(new LambdaQueryWrapper<ProdTagReference>()
                    .eq(ProdTagReference::getProdId, prodDto.getProdId()));
            for (Sku sku : skuList) {
                sku.setUpdateTime(new Date());
                sku.setVersion(sku.getVersion()+1);
            }
            skuService.saveBatch(skuList);
            List<ProdTagReference> tagReferenceList = new ArrayList<>();
            tagList.forEach(tagId->{
                ProdTagReference prodTagReference = new ProdTagReference();
                prodTagReference.setProdId(prodDto.getProdId());
                prodTagReference.setCreateTime(new Date());
                prodTagReference.setShopId(AuthUtil.getShopId());
                prodTagReference.setStatus((byte) 1);
                prodTagReference.setTagId(tagId);
                tagReferenceList.add(prodTagReference);
            });
            prodTagReferenceService.saveBatch(tagReferenceList);
        }
    }

    @Override
    public ProdDto selectProdById(Long id) {
        Prod prod = prodMapper.selectById(id);
        ProdDto prodDto =BeanUtil.copyProperties(prod,ProdDto.class);
        prodDto.setSkuList(skuService.loadSkuBySkuId(id));
        List<ProdTagReference> referenceList = prodTagReferenceMapper.selectList(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, id));
        prodDto.setTagList(referenceList.stream()
                .map(ProdTagReference::getTagId).collect(Collectors.toList()));
        // System.out.println(prod);
        return prodDto;
    }

    // @Override
    // public Prod selectProdById(Long id) {
    //     ProdDto prodDto = (ProdDto) prodMapper.selectById(id);
    //     prodDto.setSkuList(skuService.skuList(id));
    //     List<ProdTagReference> referenceList = prodTagReferenceMapper.selectList(new LambdaQueryWrapper<ProdTagReference>()
    //             .eq(ProdTagReference::getProdId, id));
    //     prodDto.setTagList(referenceList.stream()
    //             .map(ProdTagReference::getTagId).collect(Collectors.toList()));
    //     // System.out.println(prod);
    //     return prodDto;
    // }
    //
    // @Transactional
    // @Override
    // public Integer addProd(ProdDto prodDto) {
    //     Long shopId = AuthUtil.getShopId();
    //     prodDto.setShopId(shopId);
    //     prodDto.setSoldNum(0);
    //     prodDto.setCreateTime(new Date());
    //     prodDto.setUpdateTime(new Date());
    //     prodDto.setPutawayTime(new Date());
    //     prodDto.setVersion(0);
    //     for (Sku sku : prodDto.getSkuList()) {
    //         sku.setProdId(prodDto.getProdId());
    //         sku.setActualStocks(prodDto.getTotalStocks());
    //         sku.setUpdateTime(new Date());
    //         sku.setCreateTime(new Date());
    //         // sku.setPartyCode();
    //         // sku.setModel_id();
    //         sku.setVersion(0);
    //         sku.setIsDelete((byte) 0);
    //         skuService.save(sku);
    //     }
    //
    //     ProdTagReference prodTagReference = new ProdTagReference();
    //     for (Long tagId : prodDto.getTagList()) {
    //         prodTagReference.setReferenceId(null);
    //         prodTagReference.setShopId(shopId);
    //         // prodTagReference.setProdId(); todo
    //         prodTagReference.setStatus((byte) 1);
    //         prodTagReference.setCreateTime(new Date());
    //         prodTagReference.setTagId(tagId);
    //     }
    //     return null;
    // }
}
