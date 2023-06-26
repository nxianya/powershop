package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.Sku;
import com.xianyu.mapper.SkuMapper;
import com.xianyu.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-25  20:36
 * @Description: SkuService实现类
 * @Version: 1.0
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<Sku> loadSkuBySkuId(Long prodId) {
        return skuMapper.selectList(new LambdaQueryWrapper<Sku>()
                .eq(Sku::getProdId, prodId)
                // .eq(Sku::getStatus,1)
                // .eq(Sku::getIsDelete,0)
                // .in(Sku::getSkuId,skuId)
        );
    }
}
