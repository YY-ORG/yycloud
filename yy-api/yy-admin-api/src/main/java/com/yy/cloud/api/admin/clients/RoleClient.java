package com.yy.cloud.api.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@FeignClient("usermgmt")
public interface RoleClient {

    @RequestMapping(value = "/authsec/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<RoleItem>> findRoles(
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size);
    
    
    
    @RequestMapping(value = "/authsec/adm/role/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "角色管理--分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult roleAndMenuManage(
            @RequestBody RoleDetailsItem roleDetailsItem);

}
