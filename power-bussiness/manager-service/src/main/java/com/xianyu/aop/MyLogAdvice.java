package com.xianyu.aop;

import com.alibaba.fastjson.JSON;
import com.xianyu.domain.SysLog;
import com.xianyu.service.SysLogService;
import com.xianyu.utils.AuthUtil;
import com.xianyu.utils.IpUtil;
import com.xianyu.utils.ThreadPoolUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.nio.ch.ThreadPool;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.aop
 * @Author: xianyu
 * @CreateTime: 2023-06-23  10:46
 * @Description: 切面类,用于动态代理记录操作日志
 * @Version: 1.0
 */
@Aspect
@Component
public class MyLogAdvice {

    @Autowired
    private SysLogService sysLogService;

    @Around(value = "@annotation(com.xianyu.aop.MyLog)")
    public Object doLogAround(ProceedingJoinPoint pjp) {
        Object result =null;
        // 【获取方法签名】
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 【通过反射获取方法】
        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        // 【分别获取不同类的方法】
        Class<?> myClass = method.getDeclaringClass();
        String myClassName = myClass.getName();
        // 【获取目标方法的参数】
        Object[] params = pjp.getArgs();
        // 【获取用户id】
        Long userId = AuthUtil.getSysUserId();
        // 【获取用户IP】
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();
        String ip = IpUtil.getIpAddress(httpServletRequest);
        // 【获取注解中的操作说明】
        String operation =method.getAnnotation(MyLog.class).operation();
        // 【获取操作时间(ms)】
        long start = System.currentTimeMillis();
        // 【调用目标方法】
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        // 【将获取到的所有信息写入日志表中】
        SysLog sysLog =SysLog.builder()
                .userId(userId)
                .operation(operation)
                .method(myClassName + "." + methodName)
                .params(ObjectUtils.isEmpty(params)?"[]": JSON.toJSONString(params))
                .time(time)
                .ip(ip)
                .createDate(new Date())
                .build();
        ThreadPoolUtil.getThreadPool().submit(()->{
            sysLogService.save(sysLog);
        });
        return result;
    }
}
