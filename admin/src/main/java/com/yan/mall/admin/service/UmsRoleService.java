package com.yan.mall.admin.service;

import com.yan.mall.model.UmsMenu;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Ryan
 * Date: 2020-11-09
 * Time: 15:58
 */
public interface UmsRoleService {
    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuList(Long adminId);

}
