package com.yan.mall.controller;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.service.UmsAdminService;
import com.yan.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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


    @ApiOperation("根据用户名获取用户详情信息")
    @RequestMapping(value = "loadByUsername",method = RequestMethod.GET)
    public CommonResult loadUserByUsername(@RequestParam String username){
        UserDto userDto = umsAdminService.loadUserByUsername(username);
        return CommonResult.success(userDto);
    }

}
