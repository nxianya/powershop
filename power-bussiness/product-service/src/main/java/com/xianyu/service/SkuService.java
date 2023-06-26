package com.xianyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.Sku;

import java.util.List;

public interface SkuService extends IService<Sku> {

    // List<Sku> skuList(Long prodId);
    List<Sku> loadSkuBySkuId(Long prodId);

}
