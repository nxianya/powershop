package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.LoginSysUser;
import com.xianyu.domain.SysUser;
import com.xianyu.domain.SysUserRole;
import com.xianyu.mapper.SysRoleMapper;
import com.xianyu.mapper.SysUserMapper;
import com.xianyu.mapper.SysUserRoleMapper;
import com.xianyu.service.SysUserService;
import com.xianyu.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.xianyu.constant.BizConstant.ADMIN_ID;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-22  10:24
 * @Description: SysUserService实现类
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public Page<SysUser> loadSysUserPage(Page<SysUser> page, SysUser sysUser) {
        // 【shopId=1？ 1-管理员 :  店铺管理员】
        Long shopId = AuthUtil.getShopId();
        return userMapper.selectPage(page, new LambdaQueryWrapper<SysUser>().eq(shopId!=ADMIN_ID,SysUser::getShopId,shopId)
                .like(StringUtils.hasText(sysUser.getUsername()),SysUser::getUsername,sysUser.getUsername())
                .orderByDesc(SysUser::getCreateTime));
    }

    @Transactional
    @Override
    public boolean addSysUser(SysUser sysUser) {
        LoginSysUser loginSysUser = AuthUtil.getLoginSysUser();
        sysUser.setCreateUserId(loginSysUser.getUserId());
        sysUser.setCreateTime(new Date());
        boolean flag = save(sysUser);
        SysUserRole sysUserRole = new SysUserRole();
        List<Long> roleIdList = sysUser.getRoleIdList();
        for (Long role : roleIdList) {
            sysUserRole.setId(null);
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(role);
            sysUserRoleMapper.insert(sysUserRole);
        }
        return flag;
    }

    @Override
    public SysUser selectSysUserById(Long id) {
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, id));
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, sysUser.getUserId()));
        List<Long> roles = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        sysUser.setRoleIdList(roles);
        return sysUser;
    }

    @Transactional
    @Override
    public boolean updateUserInfo(SysUser sysUser) {
        boolean result1 = update(sysUser, new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserId, sysUser.getUserId()));
        // 【先删除再重新添加,避免重复添加数据】
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId,sysUser.getUserId()));
        SysUserRole sysUserRole = new SysUserRole();
        List<Long> roleIdList = sysUser.getRoleIdList();
        boolean result2 =false;
        for (Long role : roleIdList) {
            sysUserRole.setId(null);
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(role);
            int insert = sysUserRoleMapper.insert(sysUserRole);
            if (insert>0){
                result2=true;
            }
        }
        return result1==result2;
    }

    @Transactional
    @Override
    public boolean deleteUserByIds(List<Long> ids) {
        int count1 = userMapper.deleteBatchIds(ids);
        int count2 = sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .in(SysUserRole::getUserId, ids));
        return count1>0||count2>0;
    }
}
