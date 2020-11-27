package com.yan.mall.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:客户端获取RSA公钥接口
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:16
 */
@RestController
@Api(tags = "认证中心开放公钥获取")
public class KeyPairController {
    @Autowired
    private KeyPair keyPair;

    @GetMapping("/rsa/publicKey")
    @ApiOperation("获取公钥")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
