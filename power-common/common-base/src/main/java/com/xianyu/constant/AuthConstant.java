package com.xianyu.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 常量类
 */
public interface AuthConstant {
    // 【Token请求头的key】
    String AUTHORIZATION = "Authorization";

    // 【登录Token前缀】
    String LOGIN_TOKEN_PREFIX="login:token:";

    // 【请求Token的前缀】
    String BEARER="bearer";

    //【放行地址】
    List<String> ALLOW_URLS= Arrays.asList("/auth-server/doLogin");
}
