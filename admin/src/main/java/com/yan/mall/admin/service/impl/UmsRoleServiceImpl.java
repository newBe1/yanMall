package com.yan.mall.admin.service.impl;

import com.yan.mall.admin.dao.UmsRoleDao;
import com.yan.mall.model.UmsMenu;
import com.yan.mall.admin.service.UmsRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:59
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Resource
    private UmsRoleDao roleDao;

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }
}
