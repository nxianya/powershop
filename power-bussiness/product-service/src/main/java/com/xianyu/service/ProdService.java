package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.Prod;
import com.xianyu.dto.ProdDto;

public interface ProdService extends IService<Prod> {
    Page<Prod> loadProdWithPage(Page<Prod> page, Prod Prod);

    ProdDto selectProdById(Long id);

    Integer addProd(ProdDto prodDto);

    boolean deleteProd(Long id);

    void updateProd(ProdDto prodDto);
}
