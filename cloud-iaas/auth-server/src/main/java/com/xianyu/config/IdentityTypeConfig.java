package com.xianyu.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.config
 * @Author: xianyu
 * @CreateTime: 2023-06-18  11:11
 * @Description: 策略模式配置
 * @Version: 1.0
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "identity")
public class IdentityTypeConfig {
    private Map<String,String> types;
}