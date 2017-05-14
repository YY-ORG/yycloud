package com.yy.cloud.core.usermgmt.controller;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.*;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationProfile;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.yy.cloud.common.data.dto.usermgmt.OrganizationResourceQuotaUsageProfile;
import com.yy.cloud.common.data.otd.organization.OrganizationItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.user.OrganizationResourceItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationQuotaResp;
import com.yy.cloud.core.usermgmt.service.OrgnizationService;

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
public class OrgnizationController {

    @Autowired
    private OrgnizationService orgnizationService;

    @RequestMapping(value = "/authsec/organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，创建")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> createOrganization(
            @RequestBody OrganizationProfile _organizationProfile) {
        log.debug("用户中心-组织管理，创建组织：{}", _organizationProfile);
        GeneralContentResult<String> result = orgnizationService.createOrgnization(_organizationProfile);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    @ApiOperation(value = "用户中心-组织管理，修改")
    public GeneralResult modifyOrganization(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationProfile _organizationProfile) {
        GeneralResult result = orgnizationService.updateOrgnization(_organizationId, _organizationProfile);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户中心-组织管理，删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult deleteOrganization(
            @PathVariable("organization_id") String _organizationId) {
        GeneralResult result = orgnizationService.deleteOrgnization(_organizationId);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/{organization_id}/status", method = RequestMethod.PUT)
    @ApiOperation(value = "用户中心-组织管理，状态更新")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult updateOrganizationStatus(
            @PathVariable("organization_id") String _organizationId,
            @RequestParam(value = "status") Byte _status) {
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        orgnizationService.updateOrgnizationStatus(_organizationId, _status);
        return result;
    }

    @RequestMapping(value = "/authsec/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，获取所有机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<OrganizationItem>> findOrganizations(
            @RequestParam(value = "status", required = false) Byte _status,
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size) {
        GeneralPagingResult<List<OrganizationItem>> result = new GeneralPagingResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_page);
        pageInfo.setPageSize(_size);

        List<OrganizationItem> organizationItems = orgnizationService.listOrganizationsByPage(pageInfo, _status);

        result.setResultContent(organizationItems);
        result.setPageInfo(pageInfo);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，获取单个机构")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<OrganizationItem> findOrganizationById(
            @PathVariable("organization_id") String _organizationId) {
        GeneralContentResult<OrganizationItem> result = new GeneralContentResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        OrganizationItem organizationItem = orgnizationService.findOrganizationItemById(_organizationId);

        result.setResultContent(organizationItem);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/platforms/nonorganization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，获得无机构管理的平台")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<PlatformItem>> getNonOrganizationPlatforms() {
        GeneralContentResult<List<PlatformItem>> result = new GeneralContentResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        List<PlatformItem> platformItems = orgnizationService.findNonOrganizationPlatforms();
        result.setResultContent(platformItems);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/enterprise/{enterprise_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，根据企业获得组织")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<OrganizationItem>> getOrganizationsByEnterpriseId(
            @PathVariable("enterprise_id") String enterpriseId) {
        GeneralContentResult<List<OrganizationItem>> result = new GeneralContentResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        List<OrganizationItem> organizationItems = orgnizationService.findOrganizationsByTenantId(enterpriseId);
        result.setResultContent(organizationItems);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/resource/{organization_id}", method = RequestMethod.POST)
    @ApiOperation(value = "用户中心-组织管理，首次设置资源配额(已过期，已合并到创建组织)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult createOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationResourceProfile _organizationResourceProfile) {
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        // orgnizationService.createResourceQuota(_organizationId, _organizationResourceProfile);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/resource/{organization_id}", method = RequestMethod.PUT)
    @ApiOperation(value = "用户中心-组织管理，更新资源配额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult updateOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationResourceProfile _organizationResourceProfile) {
        GeneralResult result = orgnizationService.updateResourceQuota(_organizationId, _organizationResourceProfile);
        return result;
    }

    @RequestMapping(value = "/authsec/organization/resource/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-组织管理，获得组织的资源配额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<OrganizationResourceItem> getOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId) {
        GeneralContentResult<OrganizationResourceItem> result = new GeneralContentResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        OrganizationResourceItem organizationResourceItem = orgnizationService.findResourceQuotaByOrganizationId(_organizationId);
        result.setResultContent(organizationResourceItem);
        return result;
    }

    /**
     * 修改部门配额
     * @param _organizationId
     * @param organizationResourceQuotaUsageProfile
     * @return
     */
    @RequestMapping(value = "/noauth/organization/{organization_id}/usedresource/validation", method = RequestMethod.POST)
    @ApiOperation(value = "用户中心-组织管理，校验部门已使用资源配额(已完成)")
    public GeneralContentResult<OrganizationQuotaResp> validateOrganizationUsedResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfile) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "进入校验部门已使用额度Controller");
        GeneralContentResult<OrganizationQuotaResp> result = orgnizationService.validateOrganizationUsedResourceQuota(_organizationId, organizationResourceQuotaUsageProfile);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "校验完成：{}", result);
        return result;
    }

    /**
     * 校验部门配额是否完成
     * @param _organizationId
     * @param organizationResourceQuotaUsageProfile
     * @return
     */
    @RequestMapping(value = "/noauth/organization/{organization_id}/usedresource", method = RequestMethod.POST)
    @ApiOperation(value = "用户中心-组织管理，更新部门已使用资源配额(已完成)")
    public GeneralContentResult<OrganizationQuotaResp> updateOrganizationUsedResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfile) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "进入修改部门已使用额度Controller");
        GeneralContentResult<OrganizationQuotaResp> result = orgnizationService.updateOrganizationUsedResourceQuota(_organizationId, organizationResourceQuotaUsageProfile);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "修改完成：{}", result);
        return result;
    }

}
