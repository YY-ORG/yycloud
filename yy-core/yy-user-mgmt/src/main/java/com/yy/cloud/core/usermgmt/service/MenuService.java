package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.menu.MenuProfile;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;

public interface MenuService {

    String createMenu(MenuProfile _menuProfile);

    void addRole(String _menuId, String _roleId);

    List<MenuItem> getAllMenuTree();

    RoleDetailsItem getMenuTreeByRoleId(String _roleId);

    List<MenuItem> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles);
    
    
    
    GeneralContentResult<RoleDetailsItem>  getMenuTreeByRoleIdForEdit(String _roleId);
    
    
    

}
