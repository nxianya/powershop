package com.xianyu.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianyu.domain.LoginSysUser;
import com.xianyu.model.Result;
import com.xianyu.utils.PathMatchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.xianyu.constant.AuthConstant.*;
import static com.xianyu.constant.ResourceConstant.RESOURCE_ALLOW_URLS;

/**
 * @BelongsProject: powershop
 * @BelongsPackage: com.xianyu.filter
 * @Author: xianyu
 * @CreateTime: 2023-06-18  11:58
 * @Description: 继承springboot的OncePerRequestFilter过滤器抽象类, 该过滤器在每次请求时只执行一次过滤
 * 1.Token自动续约
 * 2.赋予相应用户security权限
 * @Version: 1.0
 */
@Component
public class TokenTranslateFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 【获取用户请求的URL并判断是否为放行路径】
        String url = request.getRequestURI();
        if (PathMatchUtil.isMatch(RESOURCE_ALLOW_URLS, url)) {
            // 【放行】
            filterChain.doFilter(request, response);
            return;
        }
        // 【获取用户的授权信息(authorization)】
        String auth = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(auth)) {
            // 【获取Token】
            // String Token = auth.replaceFirst(BEARER, "");
            String Token = renewToken(auth, response);
            if (!ObjectUtils.isEmpty(Token)) {

                String userStr = stringRedisTemplate.opsForValue().get(LOGIN_TOKEN_PREFIX + Token);
                // 【获取用户类型】
                String userType = request.getHeader(LOGIN_TYPE);
                // 【解析数据】
                /*
                 * 经过UsernamePasswordAuthenticationToken验证后,会生成Authentication交由AuthenticationManager进行管理
                 */
                UsernamePasswordAuthenticationToken authenticationToken = JSON.parseObject(userStr, UsernamePasswordAuthenticationToken.class);
                UsernamePasswordAuthenticationToken realToken = null;
                switch (userType) {
                    case SYS_USER: {
                        // 【获取用户身份(管理员)】
                        LoginSysUser loginSysUser = JSON.parseObject(authenticationToken.getPrincipal().toString(), LoginSysUser.class);
                        // 【给当前这个用户赋予security权限】
                        List<SimpleGrantedAuthority> authorityList = loginSysUser.getPerms().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                        realToken = new UsernamePasswordAuthenticationToken(loginSysUser, null, authorityList);
                        break;
                    }
                    // 【会员】
                }
                // 【将当前的身份对象信息保存至security上下文中】
                SecurityContextHolder.getContext().setAuthentication(realToken);
                filterChain.doFilter(request, response);
                return;
            }
        }
    }

    // 【Token续约方法】
    private String renewToken(String auth, HttpServletResponse response) {
        if (StringUtils.hasText(auth)) {
            // 【获取Token】
            String Token = auth.replaceFirst(BEARER, "");
            if (StringUtils.hasText(Token)) {
                // 【判断Token是否需要续期】
                Long expire = stringRedisTemplate.getExpire(LOGIN_TOKEN_PREFIX + Token, TimeUnit.SECONDS);
                // 【剩余时间小于5分钟触发,若已过期则不续期(重新登录)】
                if (!ObjectUtils.isEmpty(expire) && !expire.equals(-2L) && expire <= RENEW_EXPIRE_THRESHOLD) {
                    stringRedisTemplate.expire(LOGIN_TOKEN_PREFIX + Token, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
                }
                return Token;
            }
            // 【无合法身份信息,写回失败结果】
            Result<String> result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "没有合法的身份信息");
            ObjectMapper objectMapper = new ObjectMapper();
            try (PrintWriter writer = response.getWriter()) {
                String str = objectMapper.writeValueAsString(result);
                writer.write(str);
                writer.flush();
                // writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
