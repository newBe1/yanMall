package com.yan.mall.admin.controller;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.admin.dao.TestParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-06
 * Time: 11:00
 */
@RestController
@RequestMapping("test")
@Api(tags = "管理端测试接口")
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

    @RequestMapping(value = "testParam",method=RequestMethod.POST)
    public CommonResult testParam(@Validated @RequestBody TestParam test){
        CommonResult commonResult;
        commonResult = CommonResult.success(test);
        return commonResult;
    }
}
