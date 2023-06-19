package com.xianyu.strategy;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xianyu.domain.LoginSysUser;
import com.xianyu.mapper.LoginSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.strategy
 * @Author: xianyu
 * @CreateTime: 2023-06-18  10:51
 * @Description: 管理员登录策略
 * @Version: 1.0
 */
@Component
public class UserGranter implements IdentityTypeGranter {


    @Autowired
    private LoginSysUserMapper userMapper;


    @Override
    public LoginSysUser loginType(String username) {
        // 【根据用户名查询数据库表中数据】
        LoginSysUser loginSysUser = userMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>().eq(LoginSysUser::getUsername, username));
        if (!ObjectUtils.isEmpty(loginSysUser)){
            // 【查询用户权限】
            Set<String> perms = userMapper.selectPermsByUserId(loginSysUser.getUserId());
            // 【将权限赋予当前用户】
            if (!CollectionUtils.isEmpty(perms)){
                loginSysUser.setPerms(perms);
            }
        }
        return loginSysUser;
    }

}
