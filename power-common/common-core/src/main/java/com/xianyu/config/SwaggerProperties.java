package com.xianyu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.config
 * @Author: xianyu
 * @CreateTime: 2023-06-19  10:37
 * @Description: 此类为Swagger的配置类
 *     Swagger: 用于生成、描述、调用、和可视化Restful风格的web服务的接口文档
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "swagger3")
public class SwaggerProperties {

    // 【基础包】
    private String basePackage;
    // 【作者】
    private String author;
    // 【文档url】
    private String url;
    // 【作者邮箱】
    private String email;
    // 【标题文档】
    private String title;
    // 【描述】
    private String description;
    // 【团队】
    private String termsOfServiceUrl;
    // 【授权】
    private String license;
    // 【授权的url】
    private String licenseUrl;
    // 【版本】
    private String version;
}
