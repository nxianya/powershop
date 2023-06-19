package com.xianyu.interceptor;

import com.xianyu.constant.AuthConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.interceptor
 * @Author: xianyu
 * @CreateTime: 2023-06-19  11:52
 * @Description: Feign
 *  主要用于服务之间的调用,在做服务之间的调用时,
 *  修改请求头部或编码信息可通过实现RequestInterceptor接口的apply方法记录请求发送的时间
 * @Version: 1.0
 */
public class TokenFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 【获取请求特性】
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // 【获取请求的对象】
        if (!ObjectUtils.isEmpty(requestAttributes)) {
            HttpServletRequest request = requestAttributes.getRequest();
            // 【获取Token与LoginType】
            String Token =  request.getHeader(AuthConstant.AUTHORIZATION);
            String loginType = request.getHeader(AuthConstant.LOGIN_TYPE);
            if (StringUtils.hasText(Token)){
                // 【用于服务之间的调用时传递凭证】
                // 【传递Token】
                requestTemplate.header(AuthConstant.AUTHORIZATION,Token);
                // 【传递用户类型】
                requestTemplate.header(AuthConstant.LOGIN_TYPE,loginType);
            }
        }
    }
}
