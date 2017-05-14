package com.gemii.lizcloud.api.group.admin.service.impl;

import com.gemii.lizcloud.api.group.admin.clients.MenuClient;
import com.gemii.lizcloud.api.group.admin.service.MenuService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.RoleProfile;
import com.gemii.lizcloud.common.data.otd.menu.MenuItem;
import com.gemii.lizcloud.common.data.otd.role.RoleDetailsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
