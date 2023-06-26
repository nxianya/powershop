package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.ProdTag;
import com.xianyu.mapper.ProdTagMapper;
import com.xianyu.service.ProdTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-25  16:12
 * @Description: ProdTagService实现类
 * @Version: 1.0
 */
@Service
public class ProdTagServiceImpl extends ServiceImpl<ProdTagMapper, ProdTag> implements ProdTagService {
    @Autowired
    private ProdTagMapper prodTagMapper;
    @Override
    public Page<ProdTag> loadProdTagWithPage(Page<ProdTag> page, ProdTag prodTag) {
        return prodTagMapper.selectPage(page,new LambdaQueryWrapper<ProdTag>()
                        .eq(!ObjectUtils.isEmpty(prodTag.getStatus()),ProdTag::getStatus,prodTag.getStatus())
                        .like(StringUtils.hasText(prodTag.getTitle()),ProdTag::getTitle,prodTag.getTitle())
                        .orderByAsc(ProdTag::getSeq)
                );
    }

    @Override
    public List<ProdTag> loadAllProdTag() {
        return prodTagMapper.selectList(new LambdaQueryWrapper<ProdTag>()
                .eq(ProdTag::getStatus,1)
                .orderByAsc(ProdTag::getSeq));
    }

    @Override
    public boolean addProdTag(ProdTag prodTag) {
        return save(prodTag);
    }

    @Override
    public ProdTag selectProdTagById(Long id) {
        return getById(id);
    }
}
