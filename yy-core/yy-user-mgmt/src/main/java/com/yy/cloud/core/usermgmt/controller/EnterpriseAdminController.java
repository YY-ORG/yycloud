/**
 * Project Name:liz-sysbase
 * File Name:SysBaseRestController.java
 * Package Name:com.yy.cloud.core.sysbase.controller
 * Date:Jul 11, 20164:13:02 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.core.usermgmt.controller;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.accountcenter.LdapSourceProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;
import com.yy.cloud.core.usermgmt.data.domain.FoxTenant;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;
import com.yy.cloud.core.usermgmt.exception.UserExistException;
import com.yy.cloud.core.usermgmt.service.AdUserMgmtService;
import com.yy.cloud.core.usermgmt.service.EnterpriseAdminService;
import com.yy.cloud.core.usermgmt.service.LdapMgmtService;
import com.yy.cloud.core.usermgmt.service.TenantService;
import com.yy.cloud.core.usermgmt.utils.BeanCopyHelper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:SysBaseRestController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Jul 11, 2016 4:13:02 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class EnterpriseAdminController {

	@Autowired
	private EnterpriseAdminService enterpriseAdminService;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private AdUserMgmtService adUserMgmtService;

	@Autowired
	private LdapMgmtService ldapMgmtService;

	/**
	 * 创建企业管理员
	 * 
	 * @param adminProfile
	 * @return
	 */
	@RequestMapping(value = "/tenants/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<EnterpriseAdminItem> createEnterpriseAdmin(
			@RequestBody EnterpriseAdminProfile adminProfile) {
		GeneralPagingResult<EnterpriseAdminItem> response = new GeneralPagingResult<>();
		if (null != adminProfile.getAuthMode()
				&& adminProfile.getAuthMode().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
			ExtLdapUser ldapUser = adUserMgmtService.getAdUser(adminProfile.getLoginName(), adminProfile.getLdapId());
			if (ldapUser != null) {
				response.setResultCode(ResultCode.ADUSERMGMT_ADUSER_DUPLICATED_EXCEPTION);
				response.setDetailDescription("User existed.");
				return response;
			}
		}
		FoxUser foxUser = enterpriseAdminService.createUser(adminProfile);
		EnterpriseAdminItem adminItem = BeanCopyHelper.copyFoxUser2AdminItem(foxUser);
		buildEnterpriseAdminItem4AD(adminItem);
		FoxTenant foxTenant = tenantService.findById(foxUser.getTenantId());
		if (null != foxTenant) {
			// adminItem.setEnterpriseName(foxTenant.getName());
		}

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		response.setResultContent(adminItem);
		return response;
	}

	@RequestMapping(value = "/tenants/{_enterpriseId}/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<EnterpriseAdminItem> createEnterpriseAdmin2(
			@ApiParam @PathVariable("_enterpriseId") String _enterpriseId,
			@RequestBody EnterpriseAdminProfile adminProfile) {
		log.debug("createEnterpriseAdmin2");
		GeneralPagingResult<EnterpriseAdminItem> response = new GeneralPagingResult<>();
		if (null != adminProfile.getAuthMode()
				&& adminProfile.getAuthMode().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
			log.debug("createEnterpriseAdmin2 check ad duplicate");
			ExtLdapUser ldapUser = adUserMgmtService.getAdUser(adminProfile.getLoginName(), adminProfile.getLdapId());
			if (ldapUser != null) {
				response.setResultCode(ResultCode.ADUSERMGMT_ADUSER_DUPLICATED_EXCEPTION);
				response.setDetailDescription("User existed.");
				return response;
			}
		}
		FoxUser foxUser;
		try {
			foxUser = enterpriseAdminService.createUser(adminProfile);
		} catch (UserExistException e) {
			response.setResultCode(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION);
			response.setDetailDescription(String.format("用户名 %s 已存在.", adminProfile.getLoginName()));
			return response;
		}
		EnterpriseAdminItem adminItem = BeanCopyHelper.copyFoxUser2AdminItem(foxUser);
		adminItem.setId(foxUser.getId());
		buildEnterpriseAdminItem4AD(adminItem);
		FoxTenant foxTenant = tenantService.findById(foxUser.getTenantId());
		if (null != foxTenant) {
			// adminItem.setEnterpriseName(foxTenant.getName());
		}

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		response.setResultContent(adminItem);
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/tenant/admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<GeneralContent> updateEnterpriseAdmin(@RequestBody EnterpriseAdminProfile profile) {
		GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();
		enterpriseAdminService.updateUser(profile);

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		return response;
	}

	@RequestMapping(value = "/tenants/admins/{_tenantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<EnterpriseAdminItem> getEnterpriseById(@PathVariable("_tenantId") String id) {

		// TODO 根据用户ID查询企业管理员详细信息
		GeneralPagingResult<EnterpriseAdminItem> response = new GeneralPagingResult<>();

		FoxUser foxUser = enterpriseAdminService.findUserById(id);
		EnterpriseAdminItem adminItem = BeanCopyHelper.copyFoxUser2AdminItem(foxUser);
		buildEnterpriseAdminItem4AD(adminItem);
		FoxTenant foxTenant = tenantService.findById(foxUser.getTenantId());

		if (null != foxTenant) {
			// adminItem.setEnterpriseName(foxTenant.getName());
		}

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		response.setResultContent(adminItem);
		return response;
	}

	@RequestMapping(value = "/tenants/admins/{page}/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminList(@PathVariable("page") Integer page,
			@PathVariable("size") Integer size) {
		GeneralPagingResult<List<EnterpriseAdminItem>> response = new GeneralPagingResult<>();

		List<EnterpriseAdminItem> adminItems = new ArrayList<>();

		Page<FoxUser> foxUsers = enterpriseAdminService.listAllUsers(page, size);

		foxUsers.forEach(foxUser -> {
			EnterpriseAdminItem adminItem = BeanCopyHelper.copyFoxUser2AdminItem(foxUser);
			FoxTenant foxTenant = tenantService.findById(foxUser.getTenantId());
			if (null != foxTenant) {
				// adminItem.setEnterpriseName(foxTenant.getName());
			}
			buildEnterpriseAdminItem4AD(adminItem);
			adminItems.add(adminItem);
		});

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setPageSize(size);
		pageInfo.setTotalPage(foxUsers.getTotalPages());
		pageInfo.setTotalRecords(new Long(foxUsers.getTotalElements()).intValue());

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		response.setResultContent(adminItems);
		response.setPageInfo(pageInfo);
		return response;
	}

	@RequestMapping(value = "/tenant/{_tenantId}/admins/{page}/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminListByEnt(
			@PathVariable("_tenantId") String tenantId, @PathVariable("page") Integer page,
			@PathVariable("size") Integer size) {
		GeneralPagingResult<List<EnterpriseAdminItem>> response = new GeneralPagingResult<>();

		List<EnterpriseAdminItem> adminItems = new ArrayList<>();

		Page<FoxUser> foxUsers = enterpriseAdminService.listUsersByTenantId(page, size, tenantId);

		foxUsers.forEach(foxUser -> {
			EnterpriseAdminItem adminItem = BeanCopyHelper.copyFoxUser2AdminItem(foxUser);
			FoxTenant foxTenant = tenantService.findById(foxUser.getTenantId());
			if (null != foxTenant) {
				// adminItem.setEnterpriseName(foxTenant.getName());
			}
			buildEnterpriseAdminItem4AD(adminItem);
			adminItems.add(adminItem);
		});

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setPageSize(size);
		pageInfo.setTotalPage(foxUsers.getTotalPages());
		pageInfo.setTotalRecords(new Long(foxUsers.getTotalElements()).intValue());

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		response.setResultContent(adminItems);
		response.setPageInfo(pageInfo);
		return response;
	}

	@RequestMapping(value = "/tenant/admin/{_adminId}", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	GeneralPagingResult<GeneralContent> deleteEnterpriseAdmin(@PathVariable("_adminId") String id) {
		GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();

		enterpriseAdminService.deleteUser(id);

		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		return response;
	}

	@RequestMapping(value = "/tenant/batch", method = RequestMethod.DELETE)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdminByBatch(
			@RequestBody List<EnterpriseAdminProfile> adminProfiles) {
		GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();
		enterpriseAdminService.deleteUsersBatch(adminProfiles);
		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		return response;
	}

	@RequestMapping(value = "/tenant/admin/{_adminId}/status/{_status}", method = RequestMethod.PUT)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatus(@PathVariable("_adminId") String id,
			@PathVariable("_status") Byte status) {
		GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();
		enterpriseAdminService.updateUserStatus(id, status);
		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/tenants/admins/status/{_status}")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatusByBatch(
			@RequestBody List<EnterpriseAdminProfile> adminProfiles, @PathVariable("_status") Byte status) {
		GeneralPagingResult<GeneralContent> response = new GeneralPagingResult<>();
		enterpriseAdminService.updateUsersStatusBatch(adminProfiles, status);
		response.setResultCode(ResultCode.OPERATION_SUCCESS);
		return response;
	}

	private void buildEnterpriseAdminItem4AD(EnterpriseAdminItem adminItem) {
		ExtLdapUser ldapUser = adUserMgmtService.findByUserId(adminItem.getId());
		if (ldapUser != null) {
			adminItem.setLoginName(ldapUser.getLogin());
			adminItem.setLdapId(ldapUser.getId());
			GeneralContentResult<LdapSourceProfile> ldap = ldapMgmtService.getLdapSource(ldapUser.getLdapSourceId());
			if (ldap != null) {
				adminItem.setLdapName(ldap.getResultContent().getName());
			}
		}
	}

	// @RequestMapping(value = "/noauthsec/account/{_userName}", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public GeneralResult<CurrentUser> getUserDetailInfo(@PathVariable String
	// _userName) {
	// log.info("Going to retrieve user {} 's detail info...", _userName);
	// GeneralResult<CurrentUser> tempResult = new GeneralResult<CurrentUser>();
	//
	// try {
	// CurrentUser tempUser =
	// enterpriseAdminService.getUserDetailInfo(_userName);
	// tempResult.setResultContent(tempUser);
	// tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
	// return tempResult;
	// } catch (RuntimeException re) {
	// log.error("Exception occured while retrieving the user detail info: {}",
	// re);
	// tempResult.setResultCode(ResultCode.USERMGMT_RETRIEVE_FAILED);
	// tempResult.setDetailDescription(re.getMessage());
	// }
	//
	// return tempResult;
	// }
}
