package com.gemii.lizcloud.api.group.admin.controller;

import com.gemii.lizcloud.api.group.admin.service.MenuService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.RoleProfile;
import com.gemii.lizcloud.common.data.otd.menu.MenuItem;
import com.gemii.lizcloud.common.data.otd.role.RoleDetailsItem;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MPPMenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/authsec/mpp/menu/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "获得所有菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<MenuItem>> getMenuTree() {
        return menuService.getMenuTree();
    }

    @RequestMapping(value = "/authsec/mpp/menu/tree/{role_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据一个角色获得菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(
            @PathVariable("role_id") String _roleId) {
        return menuService.getMenuTreeByRoleId(_roleId);
    }

    @RequestMapping(value = "/authsec/mpp/tree/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "根据多个角色获得菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<MenuItem>> getMenuTreeByRoleIds(
            @RequestBody List<RoleProfile> _roleProfiles) {
        return menuService.getMenuTreeByRoleIds(_roleProfiles);
    }

}
