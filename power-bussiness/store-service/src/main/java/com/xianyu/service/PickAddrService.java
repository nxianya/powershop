package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.PickAddr;

public interface PickAddrService extends IService<PickAddr> {
    Page<PickAddr> loadPickAddrWithPage(Page<PickAddr> page,PickAddr pickAddr);
}
