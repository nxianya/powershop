package com.xianyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getSysMenusByUserId(Long userId);
}
