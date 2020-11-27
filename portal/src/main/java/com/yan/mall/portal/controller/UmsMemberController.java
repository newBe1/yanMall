package com.yan.mall.portal.controller;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.constant.ComConstant;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.portal.domain.UmsMemberLoginParam;
import com.yan.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-27
 * Time: 10:24
 */
@RestController
@RequestMapping("/sso")
@Api(tags = "客户端用户管理")
public class UmsMemberController {
    @Resource
    private UmsMemberService memberService;

    @ApiOperation("客户端用户登陆")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public CommonResult login(@Validated @RequestBody UmsMemberLoginParam umsMemberLoginParam){
        return memberService.login(umsMemberLoginParam);
    }

    @ApiOperation("通过用户名获取用户信息")
    @RequestMapping(value = "loadByUsername" , method = RequestMethod.GET)
    public UserDto loadUserByUsername(@RequestParam String username){
        return memberService.loadUserByUsername(username);
    }

    @ApiOperation("获取验证码")
    @RequestMapping(value = "getAuthCode" , method = RequestMethod.GET)
    public CommonResult getAuthCode(@RequestParam String phoneNum){
        boolean isPhone = Pattern.compile(ComConstant.PHONE_PATTERN).matcher(phoneNum).matches();

        if(isPhone){
            String authCode = memberService.generateAuthCode(phoneNum);
            return CommonResult.success(authCode, "验证码生成成功，有效时间为一分钟");
        }
        return CommonResult.failed("请输入11位手机号码");
    }

    @ApiOperation("获取登陆客户信息")
    @RequestMapping(value = "userInfo",method = RequestMethod.GET)
    public CommonResult userInfo(){
        return CommonResult.success(memberService.getCurrentMember());
    }
}
