package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;

public interface MenuService {

    GeneralContentResult<List<MenuItem>> getMenuTree();

    GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(String _roleId);

    GeneralContentResult<List<MenuItem>> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles);
    
    public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleIdForEdit( String _roleId);

}
