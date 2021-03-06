package com.gemii.lizcloud.api.group.admin.controller;

import com.gemii.lizcloud.api.group.admin.service.RoleService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationProfile;
import com.gemii.lizcloud.common.data.otd.organization.OrganizationItem;
import com.gemii.lizcloud.common.data.otd.role.RoleItem;
import com.gemii.lizcloud.common.data.otd.user.UserItem;
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
public class ADMRoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/authsec/adm/roles/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-角色管理，获取所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<RoleItem>> findRoles(
            @RequestParam(value = "status", required = false) Byte _status,
            @PathVariable("page") Integer _page,
            @PathVariable("size") Integer _size) {
        return roleService.findRoles(_page, _size);
    }

}
