package com.xianyu.config;

import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianyu.constant.ResourceConstant;
import com.xianyu.filter.TokenTranslateFilter;
import com.xianyu.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.config
 * @Author: xianyu
 * @CreateTime: 2023-06-19  09:24
 * @Description: 后端的每一个服务(商品服务 、 订单服务 、 门店服务等)都需要授权才能访问
 * @Version: 1.0
 */
@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenTranslateFilter tokenTranslateFilter;

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     super.configure(auth);
    // }
    //
    // @Override
    // public void configure(WebSecurity web) throws Exception {
    //     super.configure(web);
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 【禁用跨域请求】
        http.csrf().disable();
        // 【添加Token解析过滤器,在security身份拦截之前执行】
        http.addFilterBefore(tokenTranslateFilter, UsernamePasswordAuthenticationFilter.class);
        // 【访问被拒绝的处理】
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        // 【配置权限的拦截】
        http.authorizeHttpRequests()
                // 【过滤不拦截的资源(api文档或者被监控的文档的url以及定时任务和直接访问的任何请求等都需要经过身份验证)】
                .antMatchers(ResourceConstant.SECURITY_ALLOW_URLS)
                // 【无权限的访问路径(无论是否登录)】
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    // 【访问被拒绝的处理方法】
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
            response.setContentType(MediaType.APPLICATION_JSON);
            // 【处理中文乱码】
            response.setCharacterEncoding("utf-8");
            Result<Object> result = Result.fail(HttpStatus.FORBIDDEN.value(), "权限不足,访问被拒绝");
            // 【输出结果】
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            // 【获取输出流】
            PrintWriter writer = response.getWriter();
            writer.write(resultStr);
            writer.flush();
            writer.close();
        });
    }
}
