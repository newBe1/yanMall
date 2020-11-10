package com.yan.mall.service.impl;

import com.yan.mall.common.domain.UserDto;
import com.yan.mall.mapper.UmsAdminMapper;
import com.yan.mall.dao.UmsAdminRoleRelationDao;
import com.yan.mall.model.UmsAdmin;
import com.yan.mall.model.UmsAdminExample;
import com.yan.mall.model.UmsRole;
import com.yan.mall.service.UmsAdminService;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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



    @Override
    public UserDto loadUserByUsername(String username) {
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if(umsAdmin != null){
            List<UmsRole> roleList = getRoleList(umsAdmin.getId());
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(umsAdmin,userDto);
        }


        return null;
    }

    public UmsAdmin getAdminByUsername(String username){
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if(umsAdminList != null && umsAdminList.size() > 0){
            return umsAdminList.get(0);
        }
        return null;
    }

    public List<UmsRole> getRoleList(Long adminId){
        return adminRoleRelationDao.getRoleList(adminId);
    }
}
