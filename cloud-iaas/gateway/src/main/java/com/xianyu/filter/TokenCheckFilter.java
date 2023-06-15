package com.xianyu.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianyu.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static com.xianyu.constant.AuthConstant.*;

/**
 * 进入服务的请求都必须携带Token
 */
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 【获取请求对象】
        ServerHttpRequest request = exchange.getRequest();
        // 【获取请求URL】
        String url = request.getURI().getPath();
        // 【判断当前请求是否为放行路径】
        if (ALLOW_URLS.contains(url)){
            // 【放行】
            return chain.filter(exchange);
        }
        // 【获取Token】
        List<String> authorization = request.getHeaders().get(AUTHORIZATION);
        if (!CollectionUtils.isEmpty(authorization)){
            String authToken = authorization.get(0);
            // 【处理Token】
            if (StringUtils.hasText(authToken)){
                String realToken = authToken.replaceFirst(BEARER,"");
                // 【获取Redis中的Token进行校验】
                Boolean flag = stringRedisTemplate.hasKey(LOGIN_TOKEN_PREFIX+realToken);
                if (StringUtils.hasText(realToken)&&Boolean.TRUE.equals(flag)){
                    //todo 检查Token是否过期、刷新TokenTTL

                    // 【放行】
                    return chain.filter(exchange);
                }
            }
        }
        // 【校验失败,拦截】
        ServerHttpResponse response = exchange.getResponse();
        // 【设置消息主体是序列化之后的JSON字符串】
        /*
         * MediaType:媒体类型
         * 决定浏览器以什么形式,什么编码对获取的资源进行解析
         */
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 【处理结果】
        /*
        * getReasonPhrase():
        *   以字符串的形式,返回状态码的标识符
        * ObjectMapper:
        *   Jackson库的主要类
        * */
        Result<Object> result= Result.fail(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            // 【将结果转换为字节】
            bytes=objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 【输出结果】
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        /*
         * just():
         *  可以指定所包含的全部元素,创建出来的Mono序列在发布这些元素之后会自动结束
         */
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
