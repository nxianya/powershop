package com.xianyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu
 * @Author: xianyu
 * @CreateTime: 2023-06-24  09:17
 * @Description: ProductServiceApplication启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
// 【开启spring事务支持】
@EnableTransactionManagement
// 【开启Spring框架缓存】
@EnableCaching
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class,args);
    }
}
