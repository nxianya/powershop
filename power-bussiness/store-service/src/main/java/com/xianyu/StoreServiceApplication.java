package com.xianyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu
 * @Author: xianyu
 * @CreateTime: 2023-06-26  15:11
 * @Description: StoreServiceApplication启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StoreServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class,args);
    }
}
