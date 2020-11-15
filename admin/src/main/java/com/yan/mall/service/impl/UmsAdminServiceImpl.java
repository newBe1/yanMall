package com.yan.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yan.mall.common.api.CommonResult;
import com.yan.mall.common.constant.AuthConstant;
import com.yan.mall.common.domain.UserDto;
import com.yan.mall.common.exception.Asserts;
import com.yan.mall.mapper.UmsAdminMapper;
import com.yan.mall.dao.UmsAdminRoleRelationDao;
import com.yan.mall.model.UmsAdmin;
import com.yan.mall.model.UmsAdminExample;
import com.yan.mall.model.UmsRole;
import com.yan.mall.service.AuthService;
import com.yan.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * Time: 15:58
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Resource
    private UmsAdminMapper umsAdminMapper;

    @Resource
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Resource
    private AuthService authService;


    @Override
    public UserDto loadUserByUsername(String username) {
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if (umsAdmin != null) {
            List<UmsRole> roleList = getRoleList(umsAdmin.getId());
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(umsAdmin, userDto);
            if(CollUtil.isNotEmpty(roleList)){
                List<String> roleStrList = roleList.stream().map(item -> item.getId() + "_" + item.getName()).collect(Collectors.toList());
                userDto.setRoles(roleStrList);
            }
            return userDto;
        }
        return null;
    }

    @Override
    public CommonResult login(String username, String password) {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            Asserts.fail("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", "123456");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        return authService.getAccessToken(params);
    }

    @Override
    public Map<String, Object> getPubKey() {
        return authService.getPubKey();
    }

    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if (umsAdminList != null && umsAdminList.size() > 0) {
            return umsAdminList.get(0);
        }
        return null;
    }

    public List<UmsRole> getRoleList(Long adminId) {
        return adminRoleRelationDao.getRoleList(adminId);
    }
}
