package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.ProdProp;

import java.util.List;

public interface ProdPropService extends IService<ProdProp> {
    // 【分页显示商品的属性规格】
    Page<ProdProp> loadProdPropWithPage(Page<ProdProp> page,ProdProp prodProp);

    // 【添加属性规格】
    Integer addProdProp(ProdProp prodProp);

    // 【加载所有的属性规则】
    List<ProdProp> loadAllProdProp();

    boolean deleteProdPropWhitValueById(Long id);
}
