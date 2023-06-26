package com.xianyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xianyu.domain.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    Page<SysRole> loadSysRolePage(Page<SysRole> page, SysRole sysRole);

    Integer addSysRole(SysRole sysRole);

    SysRole selectSysRoleById(Long id);

    Integer updateSysRoleInfo(SysRole sysRole);

    Integer deleteSysRole(List<Long> ids);
}
