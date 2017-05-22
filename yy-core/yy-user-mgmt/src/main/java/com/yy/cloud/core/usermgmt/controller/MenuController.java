package com.yy.cloud.core.usermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.menu.MenuProfile;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.core.usermgmt.service.MenuService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/authsec/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "创建菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> createMenu(
            @RequestBody MenuProfile _menuProfile) {
        GeneralContentResult<String> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        String menuId = menuService.createMenu(_menuProfile);
        result.setResultContent(menuId);
        return result;
    }

    @RequestMapping(value = "/authsec/menu/{menu_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "给菜单设置角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult addRoleToMenu(
            @PathVariable("menu_id") String _menuId,
            @RequestParam("role_id") String _roleId) {
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        menuService.addRole(_menuId, _roleId);
        return result;
    }

    @RequestMapping(value = "/authsec/menu/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<MenuItem>> getMenuTree() {
        GeneralContentResult<List<MenuItem>> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        List<MenuItem> menuItems = menuService.getAllMenuTree();
        result.setResultContent(menuItems);
        return result;
    }

    @RequestMapping(value = "/authsec/menu/tree/{role_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(
            @PathVariable("role_id") String _roleId) {
        GeneralContentResult<RoleDetailsItem> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        RoleDetailsItem roleDetailsItem = menuService.getMenuTreeByRoleId(_roleId);
        result.setResultContent(roleDetailsItem);
        return result;
    }

    @RequestMapping(value = "/authsec/menu/tree/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<MenuItem>> getMenuTreeByRoleIds(
            @RequestBody List<RoleProfile> _roleProfiles) {
        GeneralContentResult<List<MenuItem>> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        List<MenuItem> menuItems = menuService.getMenuTreeByRoleIds(_roleProfiles);
        result.setResultContent(menuItems);
        return result;
    }

}
