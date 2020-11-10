package com.yan.mall.service;

import com.yan.mall.common.domain.UserDto;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:57
 */
public interface UmsAdminService {

    UserDto loadUserByUsername(String username);
}
