package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianyu.domain.SysUser;
import com.xianyu.mapper.SysUserMapper;
import com.xianyu.service.SysUserService;
import com.xianyu.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import static com.xianyu.constant.BizConstant.ADMIN_ID;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-22  10:24
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Override
    public Page<SysUser> loadSysUserPage(Page<SysUser> page, SysUser sysUser) {
        // 【shopId=1？ 1-管理员 :  店铺管理员】
        Long shopId = AuthUtil.getShopId();
        return userMapper.selectPage(page, new LambdaQueryWrapper<SysUser>().eq(shopId!=ADMIN_ID,SysUser::getShopId,shopId)
                .like(StringUtils.hasText(sysUser.getUsername()),SysUser::getUsername,sysUser.getUsername())
                .orderByDesc(SysUser::getCreateTime));
    }
}
