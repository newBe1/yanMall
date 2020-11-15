package com.yan.mall.controller;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.dto.UmsAdminLoginParam;
import com.yan.mall.service.UmsAdminService;
import com.yan.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:54
 */
@RestController
@Api(tags = "UmsAdminController", description = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Resource
    private UmsRoleService umsRoleService;

    @Resource
    private UmsAdminService umsAdminService;

    @ApiOperation("登陆返回token")
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam){
        return umsAdminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
    }

    @ApiOperation("根据用户名获取用户详情信息")
    @RequestMapping(value = "loadByUsername",method = RequestMethod.GET)
    public UserDto loadUserByUsername(@RequestParam String username){
        UserDto userDto = umsAdminService.loadUserByUsername(username);
        return userDto;
    }

    @ApiOperation("获取公钥")
    @RequestMapping(value = "getPubKey",method = RequestMethod.GET)
    public Map<String, Object> getPubKey(){
        return umsAdminService.getPubKey();
    }

    @GetMapping("test")
    public String test(){
        return "cehgng";
    }

}
