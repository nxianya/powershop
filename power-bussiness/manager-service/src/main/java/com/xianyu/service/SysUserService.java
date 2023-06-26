package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
    // 【分页查询用户列表数据】
    Page<SysUser> loadSysUserPage(Page<SysUser> page,SysUser sysUser);

    boolean addSysUser(SysUser sysUser);

    SysUser selectSysUserById(Long id);

    boolean updateUserInfo(SysUser sysUser);

    boolean deleteUserByIds(List<Long> ids);
}
