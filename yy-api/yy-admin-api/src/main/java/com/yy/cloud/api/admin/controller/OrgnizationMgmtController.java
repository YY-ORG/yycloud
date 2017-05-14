package com.yy.cloud.api.admin.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationProfile;
import com.yy.cloud.common.data.otd.organization.OrganizationItem;

/**
 * Created by chenluo on 10/24/2016.
 */
@RestController
public class OrgnizationMgmtController {

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/organization", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，创建机构（未完成）")
    public GeneralContentResult<OrganizationProfile> createOrganization(OrganizationProfile organizationProfile){
        return null;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/organizations/{status}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，获取所有机构（未完成）")
    public GeneralContentResult<OrganizationItem> findOrganizations(@ApiParam(value = "传数字，不要传成字符类型了") @PathVariable("status") Byte status, PageParameter pageParameter){
        return null;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/organization/{orgId}/status/{status}", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，更新企业状态 1 启用，2 禁用，4删除,注意状态不要传成字符类型了（未完成）")
    public GeneralContentResult<OrganizationItem> updateOrganizations(@PathVariable(value = "orgId") String orgId,@ApiParam(value = "传数字，不要传成字符类型了") @PathVariable("status") Byte status){
        return null;
    }

    // 获取所有云平台this.platformService.getAllPlatformsByPage(_page, _size, _status);

}
