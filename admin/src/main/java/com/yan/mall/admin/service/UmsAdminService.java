package com.yan.mall.admin.service;

import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.admin.domain.UmsAdminParam;
import com.yan.mall.model.UmsAdmin;
import com.yan.mall.model.UmsRole;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:57
 */
public interface UmsAdminService {

    UserDto loadUserByUsername(String username);

    /**
     * 登陆功能
     * @param username 账号
     * @param password 密码
     * @return 调用认证中心获取token接口（密码模式认证）返回token
     */
    CommonResult login(String username, String password);

    Map<String, Object> getPubKey();

    UmsAdmin getUserById(Long id);

    UmsAdmin addUser(UmsAdminParam umsAdminParam);

    UmsAdmin getCurrentAdmin();

    List<UmsRole> getRoleList(Long id);
}
