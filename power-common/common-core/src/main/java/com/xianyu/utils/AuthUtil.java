package com.xianyu.utils;

import com.xianyu.domain.LoginSysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.utils
 * @Author: xianyu
 * @CreateTime: 2023-06-20  14:41
 * @Description: 获取跟权限有关的信息
 * @Version: 1.0
 */
public class AuthUtil {
    // 【获取用户Id】
    public static Long getSysUserId(){
        return  getLoginSysUser().getUserId();
    }
    // 【获取用户权限的菜单】
    public static Set<String> getSysUserAuth(){
        return getLoginSysUser().getPerms();
    }
    // 【获取用户】
    public static LoginSysUser getLoginSysUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginSysUser) authentication.getPrincipal();
    }
    // 【获取用户的shopId】
    public static Long getShopId(){
        return getLoginSysUser().getShopId();
    }
}
