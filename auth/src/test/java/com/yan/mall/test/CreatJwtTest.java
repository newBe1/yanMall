package com.yan.mall.test;

import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.yan.mall.MallAuthApplication;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.service.UmsAdminService;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-11
 * Time: 15:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallAuthApplication.class)
public class CreatJwtTest {

    @Resource
    private UmsAdminService umsAdminService;


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

        //RSA非对称加密 用此私钥作为盐对token进行加密 生成token
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(rsaPrivateKey));

        //取出token
        //System.out.println(jwt.getEncoded());

        //获取公钥
        System.out.println(Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));
    }

    /**
     * 通过公钥解析token
     */
    @Test
    public void parseJwtTest(){
        //加密后的token
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJSeWFuIiwiaWQiOiIxIn0.GG8z6iKQW6u8Iwv1rj-SoLT4uF_o2Cy-7si5KJ0uUGuOYFiqICg9SypZwdmgcKNjWHXFBbcVCMzgqZyxTbDM9BYLdTt0JYW-INtkjZAk-XV7P-zOLylHIG3U5rch_H4A_cKPh3ETQ9gqhcdTbrlCZXy88YdjcvgCW-aGbGcoWcg";

        //公钥 要带上前缀、后缀
        String publickey = "-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmsE4DrtLBLU9oMe8ZK5CoZEF/M5NplS3Il1py+Ex0wT80lTNhcDQO0xwKTzg8dnUT9DpVCMbjOCKbe8EBoZwocM7ZzpyaHfDzetynZ6q8ITO7Ny8gmKIwyI5bB0N0huJs+khO/VlARDJYRXfwxli/FCl0FKSCtkV879lr8Kzg6QIDAQAB-----END PUBLIC KEY-----";

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

}
