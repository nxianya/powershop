package com.xianyu.config;

import com.xianyu.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.xianyu.constant.AuthConstant.AUTHORIZATION;
import static com.xianyu.constant.AuthConstant.LOGIN_TYPE;

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
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
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
                swaggerProperties.getLicense(),
                swaggerProperties.getLicenseUrl(),
                new HashSet<>()
        );
    }

    private List<SecurityScheme> securitySchemes(){
        List<SecurityScheme> schemes = new ArrayList<>();
        schemes.add(new ApiKey(AUTHORIZATION,AUTHORIZATION,"header"));
        schemes.add(new ApiKey(LOGIN_TYPE,LOGIN_TYPE,"header"));
        return schemes;
    }

    private List<SecurityContext> securityContexts(){
        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o->o.requestMappingPattern().matches("/.*"))
                        .build()
        );
        return contexts;
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope =new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences =new ArrayList<>();
        securityReferences.add(new SecurityReference(AUTHORIZATION,authorizationScopes));

        AuthorizationScope globalAuthorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] globalAuthorizationScopes =new AuthorizationScope[1];
        globalAuthorizationScopes[0] =globalAuthorizationScope;
        securityReferences.add(new SecurityReference(LOGIN_TYPE,globalAuthorizationScopes));
        return securityReferences;
    }
}
