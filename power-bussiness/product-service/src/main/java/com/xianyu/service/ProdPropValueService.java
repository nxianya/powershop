package com.xianyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.ProdPropValue;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service
 * @Author: xianyu
 * @CreateTime: 2023-06-25  11:23
 * @Description: ProdPropValue业务层
 * @Version: 1.0
 */
public interface ProdPropValueService extends IService<ProdPropValue> {

    List<ProdPropValue> loadProdPropValuesByProdId(Long prodPropId);
}
