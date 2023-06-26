package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.ProdTag;
import com.xianyu.model.Result;

import java.util.List;

public interface ProdTagService extends IService<ProdTag> {
    // 【条件分页查询标签列表】
    Page<ProdTag> loadProdTagWithPage(Page<ProdTag> page,ProdTag prodTag);

    List<ProdTag> loadAllProdTag();

    boolean addProdTag(ProdTag prodTag);

    ProdTag selectProdTagById(Long id);
}
