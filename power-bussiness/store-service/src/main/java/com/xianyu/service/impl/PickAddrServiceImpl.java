package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.PickAddr;
import com.xianyu.mapper.PickAddrMapper;
import com.xianyu.service.PickAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-26  19:40
 * @Description: PickAddrService实现类
 * @Version: 1.0
 */
@Service
public class PickAddrServiceImpl extends ServiceImpl<PickAddrMapper, PickAddr> implements PickAddrService {
    @Autowired
    private PickAddrMapper pickAddrMapper;

    @Override
    public Page<PickAddr> loadPickAddrWithPage(Page<PickAddr> page, PickAddr pickAddr) {
        return pickAddrMapper.selectPage(page, new LambdaQueryWrapper<PickAddr>()
                .like(StringUtils.hasText(pickAddr.getAddrName()), PickAddr::getAddrName, pickAddr.getAddrName())
                .orderByAsc(PickAddr::getAddrId));
    }
}
