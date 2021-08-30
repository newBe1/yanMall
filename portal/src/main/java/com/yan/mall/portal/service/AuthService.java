package com.yan.mall.portal.service;

import com.yan.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 调用认证服务器
 * User: Ryan
 * Date: 2020-11-27
 * Time: 10:48
 */
@FeignClient("mall-auth")
public interface AuthService {

    @PostMapping("oauth/token")
    CommonResult getAccessToken(@RequestParam Map<String,String> parameters);
}
