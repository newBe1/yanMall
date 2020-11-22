package com.yan.mall.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import com.yan.mall.common.constant.AuthConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created with IntelliJ IDEA.
 * Description: 将登陆返回的token 信息转化为用户信息 过滤器  Order排序优先执行此拦截器
 * User: Ryan
 * ssr://dXMyLmRhd2FuZ2lkYy50b3A6MTcxOTM6YXV0aF9jaGFpbl9hOm5vbmU6dGxzMS4yX3RpY2tldF9hdXRoOlRXbEVlbmh5UVZVLz9vYmZzcGFyYW09JnJlbWFya3M9UkdGWFlXNW5MVlZUTFV4dmMwRnVaMlZzWlhNdE1nJmdyb3VwPVUxTlM2YXVZNTdxbjVhV1g2YVNRNUxpQTVibTA
 * Date: 2020-11-19
 * Time: 15:01
 */
@Component
public class AuthGlobalFilter implements GlobalFilter , Ordered {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        //获取token
        String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);

        //为空拦截
        if(StrUtil.isEmpty(token)){
            return chain.filter(exchange);
        }

        try {
            //从token中解析用户信息 并设置到Header中
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();
            LOGGER.info("AuthGlobalFilter.filter() user:{}",userStr);

            request = exchange.getRequest().mutate().header(AuthConstant.USER_TOKEN_HEADER, userStr).build();
            exchange = exchange.mutate().request(request).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
