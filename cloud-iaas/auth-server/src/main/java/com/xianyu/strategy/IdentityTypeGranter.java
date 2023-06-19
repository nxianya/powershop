package com.xianyu.strategy;


import com.xianyu.domain.LoginSysUser;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.strategy
 * @Author: xianyu
 * @CreateTime: 2023-06-18  10:33
 * @Description: 抽象策略类
 * @Version: 1.0
 */
public interface IdentityTypeGranter {

    LoginSysUser loginType(String username);
}
