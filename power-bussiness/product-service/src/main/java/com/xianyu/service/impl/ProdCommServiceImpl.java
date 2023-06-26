package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.constant.BizConstant;
import com.xianyu.constant.CategoryConstant;
import com.xianyu.domain.ProdComm;
import com.xianyu.mapper.ProdCommMapper;
import com.xianyu.service.ProdCommService;
import com.xianyu.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-25  22:42
 * @Description: ProdCommService实现类
 * @Version: 1.0
 */
@Service
@CacheConfig(cacheNames = "com.xianyu.service.impl.ProdCommServiceImpl")
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {
    @Autowired
    private ProdCommMapper prodCommMapper;
    @Override
    @Cacheable(key = CategoryConstant.CATEGORY_ALL_KEY)
    public Page<ProdComm> loadProdCommPage(Page<ProdComm> page, ProdComm prodComm) {
        Long shopId = AuthUtil.getShopId();
        return prodCommMapper.selectPage(page,new LambdaQueryWrapper<ProdComm>()
                .eq(!Objects.equals(shopId, BizConstant.ADMIN_ID),ProdComm::getProdCommId,prodComm.getProdId())
                .eq(!Objects.isNull(prodComm.getStatus()),ProdComm::getStatus,prodComm.getStatus())
                        .like(StringUtils.hasText(prodComm.getProdName()),ProdComm::getProdName,prodComm.getProdName())
                .orderByAsc(ProdComm::getUsefulCounts));
    }

    @Override
    @CacheEvict(key = CategoryConstant.CATEGORY_ALL_KEY,allEntries = true)
    public boolean updateProdCommInfo(ProdComm prodComm) {
        return saveOrUpdate(prodComm);
    }
}
