package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.api.admin.service.MenuService;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

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
    @ApiOperation(value = "根据一个角色获得菜单--view")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(
            @PathVariable("role_id") String _roleId) {
        return menuService.getMenuTreeByRoleId(_roleId);
    }
    
    
    @RequestMapping(value = "/authsec/mpp/menu/{role_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "角色管理--根据一个角色获得已经选择和未选择的菜单--edit")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleIdForEdit(
            @PathVariable("role_id") String _roleId) {
        return menuService.getMenuTreeByRoleIdForEdit(_roleId);
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
