package com.xianyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xianyu.domain.LoginSysUser;
import com.xianyu.mapper.LoginSysUserMapper;
import com.xianyu.strategy.IdentityTypeFactory;
import com.xianyu.strategy.IdentityTypeGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


import java.util.Set;

import static com.xianyu.constant.AuthConstant.*;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.service.impl
 * @Author: xianyu
 * @CreateTime: 2023-06-16  17:18
 * @Description: UserDetailsService是Security提供的用于封装认证用户信息的接口，
 *     该接口提供的loadUserByUsername(String s)方法用于通用户名加载信息。
 *     使用UserDetailsService进行身份认证时，自定义一个UserDetailsService接口的实现类，
 *     通过loadUserByUsername(String s)方法封装用户详情信息并返回UserDetails对象供Security认证使用
 * @Version: 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private LoginSysUserMapper userMapper;
    @Autowired
    private IdentityTypeFactory factory;

    // 【通过此方法查询数据库中的权限信息并封装至loginSysUser(实现了UserDetails)中】
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 【获取请求头】
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String loginType = request.getHeader(LOGIN_TYPE);
        if (!StringUtils.hasText(loginType)){
            throw new RuntimeException("非法的登录类型");
        }
        // 【策略模式+工厂模式】todo 未经过测试
        IdentityTypeGranter granter = factory.getGranter(loginType);
        LoginSysUser loginSysUser =granter.loginType(username);
        return loginSysUser;
        // 【判断登录角色(模块完成后弃用,改用策略模式+工厂模式)】
        // switch (loginType){
        //     case SYS_USER:{
        //         // 【根据用户名查询数据库表中数据】
        //         LoginSysUser loginSysUser = userMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>().eq(LoginSysUser::getUsername, username));
        //         if (!ObjectUtils.isEmpty(loginSysUser)){
        //             // 【查询用户权限】
        //             Set<String> perms = userMapper.selectPermsByUserId(loginSysUser.getUserId());
        //             // 【将权限赋予当前用户】
        //             if (!CollectionUtils.isEmpty(perms)){
        //                 loginSysUser.setPerms(perms);
        //             }
        //         }
        //         return loginSysUser;
        //     }
        //     case MEMBER:{
        //
        //     }
        // }
        //
        // return null;
    }
}
