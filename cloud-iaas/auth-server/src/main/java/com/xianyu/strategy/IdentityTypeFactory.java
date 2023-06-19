package com.xianyu.strategy;


import com.xianyu.config.IdentityTypeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.strategy
 * @Author: xianyu
 * @CreateTime: 2023-06-18  11:05
 * @Description: 身份类型工厂类
 * @Version: 1.0
 */
@Component
@Slf4j
public class IdentityTypeFactory implements ApplicationContextAware {

    private static Map<String, IdentityTypeGranter> granterPool = new ConcurrentHashMap<>();

    @Autowired
    private IdentityTypeConfig identityTypeConfig;

    // 【从配置文件中读取策略信息存储到Map中】
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        identityTypeConfig.getTypes().forEach((beanName,bean)->{
            granterPool.put(beanName,(IdentityTypeGranter) applicationContext.getBean(bean));
        });
    }

    // 【对外提供具体策略】
    public IdentityTypeGranter getGranter(String grantType){
        IdentityTypeGranter identityTypeGranter = granterPool.get(grantType);
        return identityTypeGranter;
    }
    @PostConstruct
    public void init(){
        log.warn("granterPool已完成初始化:{}",granterPool);
    }
}
