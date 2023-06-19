package com.xianyu.constant;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.filter
 * @Author: xianyu
 * @CreateTime: 2023-06-18  15:05
 * @Description: 此类专门用于定义访问路径
 * @Version: 1.0
 */

public interface ResourceConstant {
    // 【api接口文档或者监控的url】
    String[] RESOURCE_ALLOW_URLS={
            // 【不同版本的文档】
            "/v2/api-docs",
            "/v3/api-docs",
            // 【用来获取支持的动作】
            "/swagger-resources/configuration/ui",
            // 【用来获取api-docs的url】
            "/swagger-resources",
            // 【安全配置的选项】
            "/swagger-resources/configuration/security",
            // 【web】
            "/webjars/**",
            "/swagger-ui/**",
            // 【数据源】
            "/druid/**",
            // 【监控】
            "/actuator/**"
    };

    //对以下资源进行身份的验证
    String[] SECURITY_ALLOW_URLS={
            // 【监控】
            "/actuator/**",
            // 【数据源】
            "/druid/**",
            // 【不同版本的文档】
            "/v2/api-docs",
            "/v3/api-docs",
            // 【用来获取支持的动作】
            "/swagger-resources/configuration/ui",
            // 【用来获取api-docs的url】
            "/swagger-resources",
            // 【安全配置的选项】
            "/swagger-resources/configuration/security",
            // 【web】
            "/webjars/**",
            "/swagger-ui/**",
            // 【abc起类似占位的作用,代表与此结构相似的/add路径如(/admin/add)】
            "/abc/add",
            "/pay/toPay",
            "/prod/prod/changeStock",
            "/p/order/notifyPay",
            "/p/myOrder/changeOrderStatus"
    };
}
