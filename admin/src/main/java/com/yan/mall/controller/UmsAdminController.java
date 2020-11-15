package com.yan.mall.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.api.IErrorCode;
import com.yan.mall.common.api.ResultCode;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.common.exception.Asserts;
import com.yan.mall.dto.UmsAdminLoginParam;
import com.yan.mall.model.UmsAdmin;
import com.yan.mall.service.UmsAdminService;
import com.yan.mall.service.UmsRoleService;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
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

    @Autowired
    private HttpServletRequest request;

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


    @ApiOperation("获取当前登录用户信息")
    @RequestMapping(value = "getCurrentUser",method = RequestMethod.GET)
    public CommonResult getCurrentUser(){

        String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        if(StrUtil.isEmpty(token)){
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }

        try {
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();

            UserDto userDto = JSONUtil.toBean(userStr,UserDto.class);
            UmsAdmin umsAdmin = umsAdminService.getUserById(userDto.getId());
            if(umsAdmin != null){
                return CommonResult.success(umsAdmin);
            }else {
                return CommonResult.failed(ResultCode.NON_EXISTENT);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
