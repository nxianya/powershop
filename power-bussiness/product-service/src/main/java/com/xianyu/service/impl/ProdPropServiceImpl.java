package com.xianyu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.constant.BizConstant;
import com.xianyu.domain.ProdProp;
import com.xianyu.domain.ProdPropValue;
import com.xianyu.mapper.ProdPropMapper;
import com.xianyu.mapper.ProdPropValueMapper;
import com.xianyu.service.ProdPropService;
import com.xianyu.service.ProdPropValueService;
import com.xianyu.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-24  16:42
 * @Description: ProdPropService实现类
 * @Version: 1.0
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "com.xianyu.service.impl.ProdPropServiceImpl")
public class ProdPropServiceImpl extends ServiceImpl<ProdPropMapper, ProdProp> implements ProdPropService {
    @Autowired
    private ProdPropMapper prodPropMapper;

    @Autowired
    private ProdPropValueMapper prodPropValueMapper;

    @Autowired
    private ProdPropValueService prodPropValueService;
    @Override
    public Page<ProdProp> loadProdPropWithPage(Page<ProdProp> page, ProdProp prodProp) {
        // 【属性依赖于当前店铺的管理员,需要根据身份区分】
        Long shopId = AuthUtil.getShopId();

        Page<ProdProp> prodPropPage = prodPropMapper.selectPage(page, new LambdaQueryWrapper<ProdProp>()
                // 【admin/系统管理员】
                .eq(!Objects.equals(shopId, BizConstant.ADMIN_ID), ProdProp::getShopId, shopId)
                // 【模糊查询】
                .like(StringUtils.hasText(prodProp.getPropName()), ProdProp::getPropName, prodProp.getPropName())
        );
        // 【给每个属性规格加载属性值的集合】
        List<ProdProp> prodPropList = prodPropPage.getRecords();
        List<Long> prodIds = prodPropList.stream().map(
                ProdProp::getPropId
        ).collect(Collectors.toList());
        List<ProdPropValue> prodPropValues = prodPropValueMapper.selectList(new LambdaQueryWrapper<ProdPropValue>()
                .in(ProdPropValue::getPropId,prodIds));
        prodPropList.forEach(prod->{
            List<ProdPropValue> prodPropValueList = prodPropValues.stream()
                    .filter(prodPropValue -> prodPropValue.getPropId().equals(prod.getPropId()))
                    .collect(Collectors.toList());
            prod.setProdPropValues(prodPropValueList);
        });
        return prodPropPage;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer addProdProp(ProdProp prodProp) {
        // 【获取店铺Id】
        Long shopId = AuthUtil.getShopId();
        log.info("新增属性{},当前用户:{}", JSON.toJSONString(prodProp),AuthUtil.getShopId());
        prodProp.setRule(1);
        prodProp.setShopId(shopId);

        int count = prodPropMapper.insert(prodProp);
        if (count>0){
            // 【批量插入】
            prodProp.getProdPropValues().forEach(
                    prodPropValue ->
                            prodPropValue.setPropId(prodProp.getPropId())
            );
            boolean flag = prodPropValueService.saveBatch(prodProp.getProdPropValues());
            if (!flag){
                throw new RuntimeException("添加失败！请刷新后重试");
            }
        }
        return count;
    }

    @Override
    public List<ProdProp> loadAllProdProp() {
        Long shopId = AuthUtil.getShopId();
        return prodPropMapper.selectList(
                new LambdaQueryWrapper<ProdProp>()
                        .eq(!Objects.equals(shopId,BizConstant.ADMIN_ID),ProdProp::getPropId,shopId)
        );
    }

    @Transactional
    @Override
    public boolean deleteProdPropWhitValueById(Long id) {
        boolean result = removeById(id);
        if (result){
            result = prodPropValueService.remove(new LambdaQueryWrapper<ProdPropValue>()
                    .eq(ProdPropValue::getPropId, id));
        }
        return result;
    }

}
