package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.EnterpriseMgmtService;
import com.yy.cloud.api.admin.service.ResourceMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseQuotaProfile;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaDetailResp;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 11/1/2016.
 */
@RestController
@Slf4j
public class ResourceMgmtController {

    @Autowired
    private EnterpriseMgmtService enterpriseMgmtService;

    @Autowired
    private ResourceMgmtService resourceMgmtService;

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/resouces/quotas", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新某个企业的资源配额（已完成）", notes = "Update resource quota list for enterprise.")
    public GeneralResult updateResourceQuota(@ApiParam(name = "_enterpriseId", value = "Enterprise's ID",
            required = true) @PathVariable String _enterpriseId, @RequestBody EnterpriseQuotaProfile _profile) {
        log.debug("Going to update the resources' quotas for Enterprise {}.", _enterpriseId);
        _profile.setEnterpriseId(_enterpriseId);
        GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpriseQuota(_enterpriseId, _profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/resouces/quotas/page/{_page}/size/{_size}",
            method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取某个企业信息附带资源配额及使用情况（已完成）", notes = "Get resource quota list for enterprise.")
    public GeneralPagingResult<List<EnterpriseQuotaItem>> getResourceQuotaForEnterprise(
            @ApiParam(name = "_enterpriseId", value = "Enterprise's ID",
                    required = true) @PathVariable String _enterpriseId,
            @ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
            @ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
        log.debug("Going to get the Enterprise {}'s resource quota.");

        GeneralPagingResult<List<EnterpriseQuotaItem>> tempResult = this.enterpriseMgmtService
                .getEnterpriseResourceQuotaByEnterpriseId(_enterpriseId, _page, _size);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprises/resouces/quotas/page/{_page}/size/{_size}",
            method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取所有企业信息附带资源配额及使用情况（已完成）", notes = "Get resource quota list for enterprise.")
    public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpriseResourceQuota(
            @ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
            @ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
        log.debug("Going to get the Enterprise {}'s resource quota.");

        GeneralPagingResult<List<EnterpriseQuotaItem>> tempResult = this.enterpriseMgmtService
                .getEnterpriseResourceQuota(_page, _size);
        return tempResult;
    }

    /**
     * 更新企业配额的使用量
     * @param resourceQuotaUsageDetailProfiles
     * @return
     */
    @RequestMapping(value = "/authsec/enterprise/resource/quota/caculate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "更新企业配额的已使用量(已完成）")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    public GeneralContentResult<List<String>> enterpriseUsageQuotaCaculate(@RequestBody List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"更新企业已使用配额 {}",resourceQuotaUsageDetailProfiles);
        return enterpriseMgmtService.enterpriseUsageQuotaCaculate(resourceQuotaUsageDetailProfiles);
    }

    /**
     * 获取企业配额详情
     * @param enterpriseId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/{enterpriseId}/resource/quota/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "企业管理-查看企业,获取企业配额详情(未完成）")
    public GeneralContentResult<List<EnterpriseQuotaDetailResp>> getEnterpriseQuotaDetail(@ApiParam("企业ID") @PathVariable("enterpriseId") String enterpriseId){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取企业配额详情，enterpriseId= {}",enterpriseId);
        return enterpriseMgmtService.getEnterpriseQuotaDetail(enterpriseId);
    }

    /**
     * 获取所有企业配额详情
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/resource/quota/detail/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "企业管理-查看企业,获取所有企业配额详情(未完成)")
    public GeneralContentResult<List<EnterpriseQuotaDetailResp>> getAllEnterpriseQuotaDetail(){
        return resourceMgmtService.getAllEnterpriseQuotaDetail();
    }

    /**
     * 校验并更新企业配额的使用量
     * @param resourceQuotaUsageDetailProfiles
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/enterprise/resouce/quota/validationupdate", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "校验并更新企业的配额使用量（已完成）", notes = "Validate and update resource quota for enterprise.")
     public GeneralContentResult<ResourceQuotaUsageDetailProfile> validateAndUpdateEnterpriseUsedQuota(@RequestBody List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"校验并更新企业已使用配额 {}",resourceQuotaUsageDetailProfiles);
        return resourceMgmtService.validateAndUpdateEnterpriseUsedQuota(resourceQuotaUsageDetailProfiles);
    }

}
