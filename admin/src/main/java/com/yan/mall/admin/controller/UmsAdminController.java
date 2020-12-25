package com.yan.mall.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.admin.domain.UmsAdminLoginParam;
import com.yan.mall.admin.domain.UmsAdminParam;
import com.yan.mall.model.UmsAdmin;
import com.yan.mall.model.UmsRole;
import com.yan.mall.admin.service.UmsAdminService;
import com.yan.mall.admin.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:54
 */
@RestController
@Api(tags = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Resource
    private UmsRoleService umsRoleService;
    @Resource
    private UmsAdminService umsAdminService;

    @ApiOperation("注册用户")
    public CommonResult register(@RequestBody UmsAdminParam umsAdminParam){
        UmsAdmin umsAdmin = umsAdminService.addUser(umsAdminParam);
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation("登陆返回token")
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam){

        return umsAdminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
    }

    @ApiOperation("根据用户名获取用户详情信息")
    @RequestMapping(value = "loadByUsername",method = RequestMethod.GET)
    public UserDto loadUserByUsername(@RequestParam(name = "username",value = "用户名",required = true) String username){
        UserDto userDto = umsAdminService.loadUserByUsername(username);
        return userDto;
    }

    @ApiOperation("获取公钥")
    @RequestMapping(value = "getPubKey",method = RequestMethod.GET)
    public Map<String, Object> getPubKey(){
        return umsAdminService.getPubKey();
    }


    @ApiOperation("获取当前登录用户信息")
    @RequestMapping(value = "userInfo",method = RequestMethod.GET)
    public CommonResult userInfo(){
        UmsAdmin umsAdmin = umsAdminService.getCurrentAdmin();

        Map<String,Object> map = new HashMap<>();
        map.put("username",umsAdmin.getUsername());
        map.put("menus",umsRoleService.getMenuList(umsAdmin.getId()));
        map.put("icon",umsAdmin.getIcon());
        List<UmsRole> roleList = umsAdminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roleStrList = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            map.put("role",roleStrList);
        }
        return CommonResult.success(map);
    }
}
