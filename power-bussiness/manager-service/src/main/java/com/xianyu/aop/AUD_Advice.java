package com.xianyu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.aop
 * @Author: xianyu
 * @CreateTime: 2023-06-24  17:14
 * @Description: TODO
 * @Version: 1.0
 */
// @Component
// @Aspect
public class AUD_Advice {
    // @Pointcut("execution(* com.xianyu.*)")
    private void AUDpt(){}

    // @Around("AUDpt()")
    // public Object deleteInfo(ProceedingJoinPoint pjp){
    //
    // }
}
