package com.xianyu.config;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianyu.model.LoginSuccess;
import com.xianyu.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.UUID;

import static com.xianyu.constant.AuthConstant.*;

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
        /*
        * loginProcessingUrl():
        *   前台提交表单后跳转到此路径进行userDetailsService的验证
        * */
        http.formLogin().loginProcessingUrl(LOGIN_URL)
                // 【成功】
                .successHandler(authenticationSuccessHandler())
                // 【失败】
                .failureHandler(authenticationFailureHandler());
        // 【配置退出登录(注销)】
        /*
        * logoutUrl:
        *   指定Spring security拦截的注销的url路径
        * */
        http.logout().logoutUrl(LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler());
        // 【请求都要通过身份验证】
        http.authorizeHttpRequests().anyRequest().authenticated();
        super.configure(http);
    }

    //【登录成功后的处理方法】
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return ((request,response,authentication)->{
            // 【产生Token】 todo 后续采用jjwt令牌完成
            String Token = UUID.randomUUID().toString();
            // 【将登陆成功的身份对象转换为json字符串】
            String userStr = JSONUtils.toJSONString(authentication);
            // 【将身份信息写入Redis,过期时间2H+随机时间(预防缓存雪崩)】
            stringRedisTemplate.opsForValue().set(
                    LOGIN_TOKEN_PREFIX+Token,userStr, Duration.ofSeconds(TOKEN_EXPIRE_TIME+ RandomUtil.randomLong(1,100))
            );
            LoginSuccess loginSuccess = new LoginSuccess();
            loginSuccess.setType(BEARER);
            loginSuccess.setAccessToken(Token);
            loginSuccess.setExpiresIn(TOKEN_EXPIRE_TIME.toString());
            // 【输出结果】
            ObjectMapper objectMapper = new ObjectMapper();
            // 【将传入的对象序列化为Json,返回给调用者】
            String resultStr = objectMapper.writeValueAsString(loginSuccess);
            // 【将结果写回页面】
            /*
            * MediaType.APPLICATION_JSON_VALUE:
            *   表示反回给接口请求的是Json数据
            * response.getWriter():
            *   获取打印输出流
            * */
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = response.getWriter();
            writer.write(resultStr);
            // 【清空缓存,关闭资源】
            writer.flush();
            writer.close();
        });
    }

    //【登录失败后的处理方法】
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return ((request,response,authenticationException)->{
            // 【以Json格式响应】
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            // 【指定字符编码】
            response.setCharacterEncoding("utf-8");
            // 【定义失败之后的输出结果】
            Result<String> result = new Result<>();
            // 【未授权,登陆失败】
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            if (authenticationException instanceof LockedException){
                result.setMsg("账号已被锁定,登录失败");
            }else if(authenticationException instanceof BadCredentialsException){
                result.setMsg("密码错误,登录失败");

            }else if (authenticationException instanceof DisabledException){
                result.setMsg("账户已被禁用,无法登录");
            }else if (authenticationException instanceof AccountExpiredException){
                result.setMsg("账户已过期,请重新登录");
            }else if (authenticationException instanceof CredentialsExpiredException){
                result.setMsg("密码已过期,无法登录");
            }else {
                result.setMsg("登陆失败");
            }
            // 【写回结果】
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(resultStr);
            // 【清空缓存,关闭资源】
            writer.flush();
            writer.close();
        });
    }

    // 【注销的处理程序】
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return ((request,response,authentication)->{
            // 【获取Token】
            String authorization = request.getHeader(AUTHORIZATION);
            // 【authorization的形式:BEARER+Token】
            String Token = authorization.replaceFirst(BEARER, "");
            // 【根据删除Redis中的用户信息】
            stringRedisTemplate.delete(LOGIN_TOKEN_PREFIX+Token);
            Result<String> result =Result.success("注销成功");
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(resultStr);
            writer.flush();
            writer.close();
        });
    }

    // 【采用BCrypt加密方式替换原本的加密方式】
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
