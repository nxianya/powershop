package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.ProdComm;

public interface ProdCommService extends IService<ProdComm> {
    Page<ProdComm> loadProdCommPage(Page<ProdComm> page, ProdComm prodComm);

    boolean updateProdCommInfo(ProdComm prodComm);
}
