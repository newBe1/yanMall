package com.yan.mall.authorization;

import cn.hutool.core.convert.Convert;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:鉴权管理器  用于判断是否有资源的访问权限
 *  AuthenticationManager 负责校验
 *  Authentication(身份验证) 对象在 AuthenticationManager 的 authenticate 函数中，开发人员实现对 Authentication 的校验逻辑。
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
        URI uri = request.getURI();     //当前请求地址



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


        //不同用户体系登陆不允许相互访问  取出token中的客户端id 和 请求路径的前缀进行比较
        String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);

        if (StrUtil.isEmpty(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }
        try {
            //String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            //JWSObject jwsObject = JWSObject.parse(realToken);
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();
            UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
            if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }
            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }



        //非管理端的路径直接放行  请求接口前缀不是mall-admin
        if(!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN , uri.getPath())){
            return Mono.just(new AuthorizationDecision(true));
        }




        //管理端路径需要检验  获取redis中存储所有路径对应的所需的角色权限（map）
        Map<Object, Object> resourcesRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        Iterator<Object> iterator = resourcesRolesMap.keySet().iterator();
        List<String> authorities = new ArrayList<>();  //当前请求所需的角色权限

        //遍历权限资源 和当前请求进行匹配
        while (iterator.hasNext()){
            String pattern = (String) iterator.next();
            if(pathMatcher.match(pattern,uri.getPath())){
                authorities.addAll(Convert.toList(String.class,resourcesRolesMap.get(pattern)));
            }
        }

        //将每个角色字符串 加上ROLE_ 前缀
        authorities = authorities.stream().map(i -> i=AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());

        //认证通过 且用户的角色和authorities匹配可以访问
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
