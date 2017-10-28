package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.api.admin.service.RoleService;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/authsec/adm/roles/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-角色管理，获取所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<RoleItem>> findRoles(
            @RequestParam(value = "status", required = false) Byte _status,
            @PathVariable("page") Integer _page,
            @PathVariable("size") Integer _size) {
        return roleService.findRoles(_page, _size);
    }
    
    
    
    /**
     * 角色分配权限
     * @param _userProfile
     * @return
     */
    @RequestMapping(value = "/authsec/adm/role/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "角色管理--分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult roleAndMenuManage(
            @RequestBody RoleDetailsItem roleDetailsItem) {
 	   log.debug("The value of roleDetailsItem is \' "+roleDetailsItem+"\'");
        return  roleService.roleAndMenuManage(roleDetailsItem);
    }
    
    
    
   


}
