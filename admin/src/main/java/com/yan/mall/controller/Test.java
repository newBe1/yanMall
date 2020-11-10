package com.yan.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("hello world")
    @RequestMapping(value = "hello" , method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }
}
