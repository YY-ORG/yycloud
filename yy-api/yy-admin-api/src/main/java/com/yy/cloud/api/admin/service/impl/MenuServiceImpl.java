package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.MenuClient;
import com.yy.cloud.api.admin.service.MenuService;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuClient menuClient;

    @Override
    public GeneralContentResult<List<MenuItem>> getMenuTree() {
        return menuClient.getMenuTree();
    }

    @Override
    public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(String _roleId) {
        return menuClient.getMenuTreeByRoleId(_roleId);
    }

    @Override
    public GeneralContentResult<List<MenuItem>> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles) {
        return menuClient.getMenuTreeByRoleIds(_roleProfiles);
    }

	@Override
	public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleIdForEdit(String _roleId) {
		return menuClient.getMenuTreeByRoleIdForEdit(_roleId);
	}

}
