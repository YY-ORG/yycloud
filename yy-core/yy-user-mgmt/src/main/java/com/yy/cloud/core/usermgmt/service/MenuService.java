package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import com.yy.cloud.common.data.dto.accountcenter.RoleProfile;
import com.yy.cloud.common.data.dto.menu.MenuProfile;
import com.yy.cloud.common.data.otd.menu.MenuItem;
import com.yy.cloud.common.data.otd.role.RoleDetailsItem;

/**
 * Created by guof on 2016/11/15.
 */
public interface MenuService {

    String createMenu(MenuProfile _menuProfile);

    void addRole(String _menuId, String _roleId);

    List<MenuItem> getAllMenuTree();

    RoleDetailsItem getMenuTreeByRoleId(String _roleId);

    List<MenuItem> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles);

}
