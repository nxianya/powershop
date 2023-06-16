package com.xianyu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static com.xianyu.constant.AuthConstant.LOGIN_URL;
import static com.xianyu.constant.AuthConstant.LOGOUT_URL;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.config
 * @Author: xianyu
 * @CreateTime: 2023-06-15  16:53
 * @Description: 继承WebSecurityConfigurerAdapter自定义configure方法自定义安全访问策略
 * @Version: 1.0
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //【使用自定义的登录业务类】
        auth.userDetailsService(userDetailsService);
    }

    // @Override
    // public void configure(WebSecurity web) throws Exception {
    //     super.configure(web);
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 【禁用csrf】
        /*
        * csrf:
        *   跨域请求伪造,容易造成Post请求无法通过
        * */
        http.csrf().disable();
        // 【配置登录处理的URL】
        http.formLogin().loginProcessingUrl(LOGIN_URL)
                // 【成功】
                .successHandler(null)
                // 【失败】
                .failureHandler(null);
        // 【配置退出登录(注销)】
        http.logout().logoutUrl(LOGOUT_URL)
                .logoutSuccessHandler(null);
        // 【请求都要通过身份验证】
        http.authorizeHttpRequests().anyRequest().authenticated();
        super.configure(http);
    }
}
