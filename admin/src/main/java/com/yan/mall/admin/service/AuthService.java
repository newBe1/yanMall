package com.yan.mall.admin.service;

import com.yan.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-11
 * Time: 15:39
 */
@FeignClient("mall-auth")
public interface AuthService {

    @PostMapping(value = "oauth/token")
    CommonResult getAccessToken(@RequestParam Map<String,String> params);

    @GetMapping(value = "rsa/publicKey")
    Map<String, Object> getPubKey();
}
