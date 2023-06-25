package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.SysUser;

public interface SysUserService extends IService<SysUser> {
    // 【分页查询用户列表数据】
    Page<SysUser> loadSysUserPage(Page<SysUser> page,SysUser sysUser);
}
