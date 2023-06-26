package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.SysRole;
import com.xianyu.domain.SysRoleMenu;
import com.xianyu.mapper.SysRoleMapper;
import com.xianyu.mapper.SysRoleMenuMapper;
import com.xianyu.service.SysRoleMenuService;
import com.xianyu.service.SysRoleService;
import com.xianyu.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-25  11:14
 * @Description: SysRoleService实现类
 * @Version: 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysRoleMenuService SysRoleMenuService;

    @Override
    public Page<SysRole> loadSysRolePage(Page<SysRole> page, SysRole sysRole) {
        return sysRoleMapper.selectPage(page,null);
    }

    @Transactional
    @Override
    public Integer addSysRole(SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        sysRole.setCreateUserId(AuthUtil.getSysUserId());
        int insert = sysRoleMapper.insert(sysRole);
        List<Long> menuIdList = sysRole.getMenuIdList();
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        for (Long menuId : menuIdList) {
            sysRoleMenu.setId(null);
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return insert;
    }

    @Override
    public SysRole selectSysRoleById(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        sysRole.setMenuIdList(sysRoleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId,id))
                .stream().map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList()));
        return sysRole;
    }

    @Transactional
    @Override
    public Integer updateSysRoleInfo(SysRole sysRole) {
        int count = sysRoleMapper.updateById(sysRole);
        // 【删除中间表信息】
        if (count>0){
            sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                    .eq(SysRoleMenu::getRoleId,sysRole.getRoleId()));
            // 【重新插入新数据】
            List<Long> menuIdList = sysRole.getMenuIdList();
            List<SysRoleMenu> menus = new ArrayList<>();
            if (!ObjectUtils.isEmpty(menuIdList)){
                menuIdList.forEach(menuId->{
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(sysRole.getRoleId());
                    sysRoleMenu.setMenuId(menuId);
                    menus.add(sysRoleMenu);
                });
                boolean flag = SysRoleMenuService.saveBatch(menus);
                if (!flag){
                    throw new RuntimeException("插入SysRoleMenu表格错误");
                }
            }
        }
        return count;
    }

    @Transactional
    @Override
    public Integer deleteSysRole(List<Long> ids) {
        int count = sysRoleMapper.deleteBatchIds(ids);
        if (count>0){
            SysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>()
                    .in(SysRoleMenu::getRoleId, ids));
        }else {
            throw  new RuntimeException("删除失败");
        }
        return count;
    }
}
