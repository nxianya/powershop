package com.xianyu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.config
 * @Author: xianyu
 * @CreateTime: 2023-06-19  10:55
 * @Description: TODO
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableOpenApi
public class SwaggerAutConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;
    @Autowired
    private Environment environment;

    // 【创建Docket对象的方法】
    @Bean
    public Docket docket(){
        boolean flag=false;
        String[] activeFiles = environment.getActiveProfiles();
        for (String active:activeFiles){
            if (active.equals("dev")||active.equals("test")){
                flag = true;
            }
        }
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(null)
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact =new Contact(
                swaggerProperties.getAuthor(),
                swaggerProperties.getUrl(),
                swaggerProperties.getEmail()
        );
        return new ApiInfo(
                swaggerProperties.getTitle(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                swaggerProperties.getTermsOfServiceUrl(),
                contact,
                swaggerProperties.getLicense()
                ,
                swaggerProperties.getLicenseUrl(),
                new HashSet<>()
        );
    }

}
