package com.xianyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu
 * @Author: xianyu
 * @CreateTime: 2023-06-20  09:19
 * @Description: 服务管理启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ManagerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerServiceApplication.class,args);
    }
}
