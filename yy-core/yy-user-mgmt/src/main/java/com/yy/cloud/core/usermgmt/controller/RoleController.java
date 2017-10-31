package com.yy.cloud.core.usermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.core.usermgmt.service.MenuService;
import com.yy.cloud.core.usermgmt.service.RoleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    
    @Autowired
    private MenuService menuService;
    
    

    @RequestMapping(value = "/authsec/role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-角色管理，创建")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> createRole(
            @RequestBody RoleProfile _roleProfile) {
        GeneralContentResult<String> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        String roleId = roleService.createRole(_roleProfile);
        result.setResultContent(roleId);
        return result;
    }

    @RequestMapping(value = "/authsec/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-角色管理，获取所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<RoleItem>> findRoles(
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size) {
        GeneralPagingResult<List<RoleItem>> result = new GeneralPagingResult<List<RoleItem>>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_page);
        pageInfo.setPageSize(_size);

        List<RoleItem> roleItems = roleService.listRolesByPage(pageInfo);
        if(roleItems!=null){
        	for(RoleItem roleItem :roleItems){
        		RoleDetailsItem menuitem= menuService.getMenuTreeByRoleId(roleItem.getId());
        		menuitem.setRoleId(roleItem.getId());
        		menuitem.setRoleName(roleItem.getRoleName());
        		roleItem.setRoleDetailsItem(menuitem);
        	}
        }
        result.setResultContent(roleItems);
        result.setPageInfo(pageInfo);
        return result;
    }

    @RequestMapping(value = "/noauth/tenant/{tenant_id}/role/user", method = RequestMethod.POST)
    @ResponseBody
    public GeneralContentResult<List<UserItem>> getMppUserListByRole(@PathVariable("tenant_id") String tenant_id, @RequestBody List<String> roleNames){
        return roleService.findMppUserByRoleList(tenant_id, roleNames);
    }

    @RequestMapping(value = "/noauth/tenant/role/user", method = RequestMethod.POST)
    @ResponseBody
    public GeneralContentResult<List<UserItem>> getAdmUserListByRole(@RequestBody List<String> roleNames){
        return roleService.findAdmUserByRoleList(roleNames);
    }
    
    
    @RequestMapping(value = "/authsec/adm/role/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "角色管理--分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult roleAndMenuManage(
            @RequestBody RoleDetailsItem roleDetailsItem){
    	return roleService.roleAndMenuManage(roleDetailsItem);
    };
}
