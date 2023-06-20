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
    String BEARER="bearer ";

    //【放行地址】
    List<String> ALLOW_URLS= Arrays.asList("/auth-server/doLogin");

    // 【登录地址】
    String LOGIN_URL="/doLogin";

    // 【退出地址】
    String LOGOUT_URL="/doLogout";

    // 【Token过期时间
    Long TOKEN_EXPIRE_TIME = 7200L;

    String ACCESS_TOKEN="access:token";

    // 【凭证有效时间】
    String EXPIRES_IN="expires:in";
    String TYPE="type";

    // 【监控地址】
    String[] MONITOR_URLS={"/actuator/**","/druid/**"};

    // 【登录类型】
    String LOGIN_TYPE="loginType";
    // 【管理员】
    String SYS_USER ="sysUser";
    // 【会员】
    String MEMBER = "member";

    // 【Token快要过期的临界值】
    Long RENEW_EXPIRE_THRESHOLD=300L;

}
