package com.xianyu.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.utils
 * @Author: xianyu
 * @CreateTime: 2023-06-23  11:22
 * @Description: Ip信息工具类
 * @Version: 1.0
 */
@Component
public class IpUtil {
    public static String getIpAddress(HttpServletRequest request){
        // 【通过请求头获取Ip地址,XFF头,即代表客户端http请求的真实地址,只有在通过了http代理或是负载均衡服务器时才会添加】
        String ip = request.getHeader("x-forwarded-for");
        if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            // 【用apache的http做代理时,一般会加上Proxy-Client-IP这个请求头】
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            // 【WL-Proxy-Client-IP这是weblogic插件加上的请求头】
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            // 【getRemoteAddr(): 获取客户端的IP地址】
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
