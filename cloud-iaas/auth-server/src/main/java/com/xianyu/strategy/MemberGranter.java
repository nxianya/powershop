package com.xianyu.strategy;

import com.xianyu.domain.LoginSysUser;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.strategy
 * @Author: xianyu
 * @CreateTime: 2023-06-18  10:57
 * @Description: 会员登录策略
 * @Version: 1.0
 */
@Component
public class MemberGranter implements IdentityTypeGranter {


    @Override
    public LoginSysUser loginType(String username) {
        return null;
    }


}