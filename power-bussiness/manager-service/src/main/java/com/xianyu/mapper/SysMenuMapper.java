package com.xianyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianyu.domain.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    // 【根据用户Id获取菜单】
    List<SysMenu> selectMenuByUserId(Long userId);
}
