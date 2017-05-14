package com.yy.cloud.core.usermgmt.controller;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.*;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.enterpise.EnterpriseUsedQuota;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseGeneralItem;
import com.yy.cloud.common.data.usermgmt.TenantModel;
import com.yy.cloud.core.usermgmt.data.domain.FoxTenant;
import com.yy.cloud.core.usermgmt.service.TenantService;
import com.yy.cloud.core.usermgmt.utils.BeanCopyHelper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guof on 2016/9/27.
 */
@RestController
@Slf4j
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/tenant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<EnterpriseGeneralItem> createEnterprise(@RequestBody EnterpriseProfile _profile) {
        GeneralPagingResult<EnterpriseGeneralItem> response = new GeneralPagingResult<>();

        FoxTenant savedTenant = tenantService.createTenant(_profile);
        EnterpriseGeneralItem generalItem = BeanCopyHelper.copyFoxTenant2EnterpriseGeneralItem(savedTenant);

        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        response.setResultContent(generalItem);
        return response;
    }

    @RequestMapping(value = "/tenants", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<TenantModel> modifyTenant(@RequestBody TenantModel tenantModel) {
        GeneralPagingResult<TenantModel> response = new GeneralPagingResult<>();

        FoxTenant savedTenant = tenantService.updateTenant(tenantModel);

        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        response.setResultContent(tenantModel);
        return response;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tenant/{_tenantId}/status/{_status}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<GeneralContent> updateEnterpriseStatus(@PathVariable("_tenantId") String _tenantId,
                                                                      @PathVariable("_status") Byte _status) {
        GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();
        tenantService.updateStatus(_tenantId, _status);
        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        return response;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tenants/status/{_status}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<GeneralContent> updateEnterpriseStatusByBatch(@RequestBody List<EnterpriseProfile> _list,
                                                                             @PathVariable("_status") Byte _status) {
        GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();
        tenantService.updateStatusByBatch(_list, _status);
        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        return response;
    }

    @RequestMapping(value = "/tenants/simple", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<EnterpriseGeneralItem>> listSimpleTenants() {
        GeneralPagingResult<List<EnterpriseGeneralItem>> response = new GeneralPagingResult<>();

        List<FoxTenant> foxTenants = tenantService.findAll();
        List<EnterpriseGeneralItem> generalItems = new ArrayList<>();

        foxTenants.forEach(foxTenant -> {
            EnterpriseGeneralItem generalItem = BeanCopyHelper.copyFoxTenant2EnterpriseGeneralItem(foxTenant);
            generalItem.setAuthMode(new Byte("0"));
            generalItems.add(generalItem);
        });

        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        response.setResultContent(generalItems);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tenants/paging")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<EnterpriseGeneralItem>> getEnterpriseGeneralItemList(@RequestBody PageParameter _request) {
        GeneralPagingResult<List<EnterpriseGeneralItem>> response = new GeneralPagingResult<>();

        Page<FoxTenant> foxTenants = tenantService.findAllByPaging(_request.getCurrentPage(), _request.getSize());
        List<EnterpriseGeneralItem> generalItems = new ArrayList<>();

        foxTenants.forEach(foxTenant -> {
            EnterpriseGeneralItem generalItem = BeanCopyHelper.copyFoxTenant2EnterpriseGeneralItem(foxTenant);
            generalItem.setAuthMode(foxTenant.getAuthmode());
            generalItems.add(generalItem);
        });

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_request.getCurrentPage());
        pageInfo.setPageSize(_request.getSize());
        pageInfo.setTotalPage(foxTenants.getTotalPages());
        pageInfo.setTotalRecords(new Long(foxTenants.getTotalElements()).intValue());

        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        response.setResultContent(generalItems);
        response.setPageInfo(pageInfo);
        return response;
    }

    @RequestMapping(value = "/tenants/{tenant_id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<TenantModel> findTenantById(@PathVariable("tenant_id") String tenantId) {
        GeneralPagingResult<TenantModel> response = new GeneralPagingResult<>();

        FoxTenant foxTenant = tenantService.findById(tenantId);

        TenantModel tenantModel = new TenantModel();
        tenantModel.setId(foxTenant.getId());
        tenantModel.setName(foxTenant.getName());
        tenantModel.setPhone(foxTenant.getPhone());
        tenantModel.setEmail(foxTenant.getEmail());
        tenantModel.setAdmin(foxTenant.getContactor());
        tenantModel.setDescription(foxTenant.getDescription());

        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        response.setResultContent(tenantModel);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tenants/{tenant_id}/general")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<EnterpriseGeneralItem> getEnterpriseInfo(@PathVariable("tenant_id") String _enterpriseId) {
        GeneralContentResult<EnterpriseGeneralItem> response = new GeneralContentResult<>();

        FoxTenant foxTenant = tenantService.findById(_enterpriseId);
        EnterpriseGeneralItem enterpriseGeneralItem = new EnterpriseGeneralItem();
        enterpriseGeneralItem.setId(_enterpriseId);
        enterpriseGeneralItem.setCode(foxTenant.getCode());
        enterpriseGeneralItem.setName(foxTenant.getName());
        enterpriseGeneralItem.setStatus(foxTenant.getStatus());
        enterpriseGeneralItem.setDescription(foxTenant.getDescription());
        enterpriseGeneralItem.setLoginName(foxTenant.getContactor());
        enterpriseGeneralItem.setAuthMode(foxTenant.getAuthmode());

        response.setResultCode(ResultCode.OPERATION_SUCCESS);
        response.setResultContent(enterpriseGeneralItem);
        return response;
    }
    
    @RequestMapping(value="/tenants/{platform_id}", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<EnterpriseProfile>> getEnterprisesByPlatformId(@PathVariable("platform_id") String _platformId){
    	GeneralContentResult<List<EnterpriseProfile>> response = new GeneralContentResult<>();
    	
    	List<EnterpriseProfile> enterpriseProfiles = tenantService.getEnterprisesByPlatformId(_platformId);
    	
    	response.setResultCode(ResultCode.OPERATION_SUCCESS);
    	response.setResultContent(enterpriseProfiles);
    	return response;
    }

    /**
     * 根据企业ID查询企业下所有部门的资源使用额度
     * @param tenantId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tenant/{tenant_id}/organization/quota")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<EnterpriseUsedQuota> getAllOrganUsedQuotaByTenantId(@PathVariable("tenant_id") String tenantId){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据企业ID查询企业下所有部门的资源使用额度，tenantId:{}", tenantId);
        return tenantService.getAllOrganUsedQuotaByTenantId(tenantId);
    }


}
