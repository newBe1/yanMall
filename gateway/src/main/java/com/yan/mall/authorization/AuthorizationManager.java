package com.yan.mall.authorization;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.config.IgnoreUrlsConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.text.ParseException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:鉴权管理器  用于判断是否有资源的访问权限
 * User: Ryan
 * Date: 2020-11-16
 * Time: 16:23
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();  //获取当前请求
        PathMatcher pathMatcher = new AntPathMatcher();
        URI uri = request.getURI();

        //白名单直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        //对跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        //不同用户体系登陆不允许相互访问
        String token = request.getHeaders().getFirst("AuthConstant.JWT_TOKEN_HEADER");

        if (StrUtil.isEmpty(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }
        try {
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
