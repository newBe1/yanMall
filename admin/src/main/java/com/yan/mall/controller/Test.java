package com.yan.mall.controller;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.constant.AuthConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-06
 * Time: 11:00
 */
@RestController
@RequestMapping("test")
@Api(tags = "test",description = "测试接口")
public class Test {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("hello world")
    @RequestMapping(value = "hello" , method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }

    @ApiOperation("获取资源角色配置信息")
    @RequestMapping(value = "getResourceRole",method = RequestMethod.GET)
    public CommonResult getResourceRole(){
        Map<Object, Object> resourcesRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        return CommonResult.success(resourcesRolesMap);
    }
}
