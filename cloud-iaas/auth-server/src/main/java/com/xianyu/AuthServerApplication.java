package com.xianyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu
 * @Author: xianyu
 * @CreateTime: 2023-06-15  16:21
 * @Description: 服务安全校验模块启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
// 【扫描mapper映射】
@MapperScan("com.xianyu.mapper")
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
