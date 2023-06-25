package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.ProdPropValue;
import com.xianyu.mapper.ProdPropValueMapper;
import com.xianyu.service.ProdPropValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-25  11:25
 * @Description: ProdPropValueService实现类
 * @Version: 1.0
 */
@Service
@Slf4j
public class ProdPropValueServiceImpl extends ServiceImpl<ProdPropValueMapper, ProdPropValue> implements ProdPropValueService {
    @Autowired
    private ProdPropValueMapper prodPropValueMapper;
    @Override
    public List<ProdPropValue> loadProdPropValuesByProdId(Long prodPropId) {
        return prodPropValueMapper.selectList(
                new LambdaQueryWrapper<ProdPropValue>()
                        .eq(ProdPropValue::getPropId,prodPropId)
        );
    }
}
