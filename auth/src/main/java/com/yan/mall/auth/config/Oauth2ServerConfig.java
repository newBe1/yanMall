package com.yan.mall.auth.config;

import com.yan.mall.auth.component.JwtTokenEnhancer;
import com.yan.mall.auth.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:认证服务器配置  （客户端信息、令牌生产规则）
 * User: Ryan
 * Date: 2020-11-06
 * Time: 15:26
 *
 * AuthorizationServerConfigurerAdapter中:
 * ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
 * AuthorizationServerSecurityConfigurer：用来配置令牌端点(Token Endpoint)的安全约束.
 * AuthorizationServerEndpointsConfigurer：用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 配置两个客户端 admin-app、portal-app
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.inMemory()
                .withClient("admin-app")                        //客户端id
                .secret(passwordEncoder.encode("123456")) //密钥
                .scopes("all")                                          //客户端范围
                .authorizedGrantTypes(                                  //该client允许的授权类型
                        "password" ,                                    //密码认证方式
                        "refresh_token",                                //刷新令牌
                        "authorization_code")                           //授权码模式
                .accessTokenValiditySeconds(3600*24)                    //访问令牌有效期
                .refreshTokenValiditySeconds(3600*24*7)                //刷新令牌有效期
                //.redirectUris("http://localhost");                    //配置redirect_uri，用于授权成功后跳转
                .and()
                .withClient("portal-app")
                .secret(passwordEncoder.encode("123456"))
                .scopes("all")
                .authorizedGrantTypes("password" , "refresh_token","authorization_code")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7);
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)  使用密码模式登陆需要
     * @param endpointsConfiguration
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfiguration) throws Exception{
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);                          //配置自定义的jwt
        delegates.add(accessTokenConverter());                    //定义令牌生成规则
        enhancerChain.setTokenEnhancers(delegates);
        endpointsConfiguration.authenticationManager(authenticationManager) //指定认证管理器
                .userDetailsService(userDetailsService)           //配置加载用户信息
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    /**
     * 自定义token 生成规则 通过私钥加密token
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 通过密码 读取jwt.jks中的公钥密钥 并注入 客户端可通过此方法获取公钥密钥
     * @return
     */
    @Bean
    public KeyPair keyPair(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }
}
