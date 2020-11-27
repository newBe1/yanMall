package com.yan.mall.test;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.yan.mall.auth.MallAuthApplication;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.common.service.RedisService;
import com.yan.mall.auth.service.UmsAdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-11
 * Time: 15:53
 */

public class CreatJwtTest {

    @Resource
    private UmsAdminService umsAdminService;

    @Resource
    private RedisService redisService;

    /**
     * 通过密钥创建token
     */
    @Test
    public void testCreateToken(){
        //证书路径
        String key_location = "jwt.jks";

        //秘钥库密码 访问jks文件的密码
        String key_password = "123456";

        //秘钥密码  读取文件内容的密码
        String keypwd = "123456";

        //秘钥别名
        String alias = "jwt";

        //读取jks文件获取流对象
        ClassPathResource classPathResource = new ClassPathResource(key_location);

        //创建密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, key_password.toCharArray());

        //读取密钥对（公钥、密钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keypwd.toCharArray());

        //获取私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();

        //获取公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();

        //定义Payload
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "1");
        tokenMap.put("name", "Ryan");
        tokenMap.put("roles", "ROLE_VIP,ROLE_USER");

        //RSA非对称加密 用此私钥作为盐对自定义载荷进行加密 生成token
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivateKey));

        //取出token
        System.out.println(jwt.getEncoded());

        //获取公钥
        //System.out.println(Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));
    }

    /**
     * 通过公钥解析token
     */
    @Test
    public void parseJwtTest(){
        //加密后的token
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl0sImlkIjoxLCJleHAiOjE2MDU1ODA4NDEsImF1dGhvcml0aWVzIjpbIjVf6LaF57qn566h55CG5ZGYIl0sImp0aSI6ImYzN2NkNTY3LTk5MjAtNDljMy1iMTRkLTA3NDhjMzYyODFmZiIsImNsaWVudF9pZCI6ImFkbWluLWFwcCJ9.B82awbpg_j2C-0Fas4abTTZt6sB4VclQK4To5gHqolE8V40GZmst6Z3k6Kvzq7yPwgBTaUiv2zDOdbIUZGg10EGv0NmlHs_4tOi84kzqgKKfWlxagWgP4rasuqC6BkBgpjZ8JHsE0vQvAuGoxGmki_zB_GZMyk6jiYaqK3p8UuE";

        //公钥 要带上前缀、后缀
        String publickey = "prBOA67SwS1PaDHvGSuQqGRBfzOTaZUtyJdacvhMdME_NJUzYXA0DtMcCk84PHZ1E_Q6VQjG4zgim3vBAaGcKHDO2c6cmh3w83rcp2eqvCEzuzcvIJiiMMiOWwdDdIbibPpITv1ZQEQyWEV38MZYvxQpdBSkgrZFfO_Za_Cs4Ok";

        //验证token
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取封装入token的数据
        String claims = jwt.getClaims();
        System.out.println(claims);
    }

    @Test
    public void loadUser(){
        String username = "test";
        UserDto userDto = umsAdminService.loadUserByUsername(username);
        System.out.println(userDto.getPassword());
    }

    @Test
    public void fasdfasd(){
        float fa = (float) 0;
        DecimalFormat df = new DecimalFormat("0.00%");
        System.out.println(df.format(fa));
    }

    @Test
    public void getResurces(){
        try {
            JWSObject jwsObject = JWSObject.parse("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ0ZXN0Iiwic2NvcGUiOlsiYWxsIl0sImlkIjoxLCJleHAiOjE2MDYyNzI1ODYsImF1dGhvcml0aWVzIjpbIjVf6LaF57qn566h55CG5ZGYIl0sImp0aSI6ImU5NzZiNDU4LTNhNzEtNDllZC1iYjhlLTY3N2Q3MGE4Y2UxNSIsImNsaWVudF9pZCI6ImFkbWluLWFwcCJ9.ka8VpEbiRwroRzuSHH_FHuMsl-6x3Zq8nfg6l9g8ZL11kyRPAfGDr54nBnyJRwEPg5XhIfgUS2Nm9SxT3XG8AJb_L0wk0WNSnV46HfDPfDhMzSKEuvjunkGbodCcVCy_RjyzdM0SvSFnDd2lHN0j1aa3oj2AQyUFql9YMsER854");
            String userStr = jwsObject.getPayload().toString();
            System.out.println(userStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
