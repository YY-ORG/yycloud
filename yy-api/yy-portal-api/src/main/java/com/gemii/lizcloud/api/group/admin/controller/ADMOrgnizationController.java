package com.gemii.lizcloud.api.group.admin.controller;

import com.gemii.lizcloud.api.group.admin.service.OrgnizationService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.gemii.lizcloud.common.data.otd.organization.OrganizationItem;
import com.gemii.lizcloud.common.data.otd.platformmgmt.PlatformItem;
import com.gemii.lizcloud.common.data.otd.user.OrganizationResourceItem;
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
public class ADMOrgnizationController {

    @Autowired
    private OrgnizationService orgnizationService;

    @RequestMapping(value = "/authsec/adm/organizations/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，获取所有机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<OrganizationItem>> findOrganizations(
            @RequestParam(value = "status", required = false) Byte _status,
            @PathVariable("page") Integer _page,
            @PathVariable("size") Integer _size) {

//        System.out.println("-----------" + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        return orgnizationService.findOrganizations(_status, _page, _size);
    }

    @RequestMapping(value = "/authsec/adm/organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-组织管理，创建")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> createOrganization(
            @RequestBody OrganizationProfile _organizationProfile) {
        return orgnizationService.createOrgnization(_organizationProfile);
    }

    @RequestMapping(value = "/authsec/adm/organization/{organization_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-组织管理，修改")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult modifyOrganization(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationProfile _organizationProfile) {
        return orgnizationService.modifyOrganization(_organizationId, _organizationProfile);
    }

    @RequestMapping(value = "/authsec/adm/organization/{organization_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "ADM用户中心-组织管理，删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult deleteOrganization(
            @PathVariable("organization_id") String _organizationId) {
        return orgnizationService.deleteOrganization(_organizationId);
    }

    @RequestMapping(value = "/authsec/adm/organization/{organization_id}/enable", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-组织管理，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult enableOrganization(
            @PathVariable("organization_id") String _organizationId) {
        return orgnizationService.enableOrgnization(_organizationId);
    }

    @RequestMapping(value = "/authsec/adm/organization/{organization_id}/disable", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-组织管理，禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult disableOrganization(
            @PathVariable("organization_id") String _organizationId) {
        return orgnizationService.disableOrgnization(_organizationId);
    }

    @RequestMapping(value = "/authsec/adm/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-组织管理，获取单个机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<OrganizationItem> findOrganizationById(
            @PathVariable("organization_id") String _organizationId) {
        return orgnizationService.findOrganizationById(_organizationId);
    }

    @RequestMapping(value = "/authsec/adm/organization/platforms/nonorganization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-组织管理，获取无机构管理平台")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<PlatformItem>> findNonOrganizationPlatforms() {
        return orgnizationService.getNonOrganizationPlatforms();
    }

    @RequestMapping(value = "/authsec/adm/organization/enterprise/{enterprise_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-组织管理，根据企业获得组织")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<OrganizationItem>> getOrganizationsByEnterpriseId(
            @PathVariable("enterprise_id") String _enterpriseId) {
        return orgnizationService.getOrganizationsByEnterpriseId(_enterpriseId);
    }

    @RequestMapping(value = "/authsec/adm/organization/resource/{organization_id}", method = RequestMethod.POST)
    @ApiOperation(value = "ADM用户中心-组织管理，首次创建资源配额")
    public GeneralResult createOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationResourceProfile _organizationResourceProfile) {
        return orgnizationService.createOrganizationResourceQuota(_organizationId, _organizationResourceProfile);
    }

    @RequestMapping(value = "/authsec/adm/organization/resource/{organization_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-组织管理，更新资源配额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult updateOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationResourceProfile _organizationResourceProfile) {
        return orgnizationService.updateOrganizationResourceQuota(_organizationId, _organizationResourceProfile);
    }

    @RequestMapping(value = "/authsec/adm/organization/resource/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-组织管理，获得组织的资源配额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<OrganizationResourceItem> getOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId) {
        return orgnizationService.getOrganizationResourceQuota(_organizationId);
    }

}
