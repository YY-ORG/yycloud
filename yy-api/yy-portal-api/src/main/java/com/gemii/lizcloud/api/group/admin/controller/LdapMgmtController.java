package com.gemii.lizcloud.api.group.admin.controller;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemii.lizcloud.api.group.admin.service.LdapMgmtService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapSourceProfile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: LdapMgmtController <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月16日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Controller
@Slf4j
public class LdapMgmtController {

	@Autowired
	private LdapMgmtService ldapMgmtService;

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/test", method = RequestMethod.POST)
	@ApiOperation(value = "Test Ldap Source", notes = "Test Ldap Source")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult testLdapSource(@RequestBody LdapSourceProfile ldapSource) {
		log.debug("testLdapSource");
		GeneralResult result = ldapMgmtService.checkLdapSource(ldapSource);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap", method = RequestMethod.POST)
	@ApiOperation(value = "Create Ldap Source", notes = "Create Ldap Source")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<String> createLdapSource(@RequestBody LdapSourceProfile ldapSource) {
		log.debug("createLdapSource");
		GeneralContentResult<String> result = ldapMgmtService.createLdapSource(ldapSource);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/ldap", method = RequestMethod.POST)
	@ApiOperation(value = "Create Ldap Source", notes = "Create Ldap Source")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<String> createEnterpriseLdapSource(
			@ApiParam(required = true, name = "enterpriseId", value = "Enterprise Id") @PathVariable("enterpriseId") String enterpriseId,
			@RequestBody LdapSourceProfile ldapSource) {
		log.debug("createEnterpriseLdapSource");
		ldapSource.setEnterpriseId(enterpriseId);
		GeneralContentResult<String> result = ldapMgmtService.createLdapSource(ldapSource);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Ldap Source Details", notes = "Ldap Source Details")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<LdapSourceProfile> getLdapSource(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id) {
		log.debug("getLdapSource");
		GeneralContentResult<LdapSourceProfile> result = ldapMgmtService.getLdapSource(id);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Ldap Source", notes = "Update Ldap Source")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult updateLdapSource(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@RequestBody LdapSourceProfile ldapSource) {
		log.debug("updateLdapSource");
		ldapSource.setId(id);
		GeneralResult result = ldapMgmtService.updateLdapSource(ldapSource);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/account", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Ldap Source Account", notes = "Update Ldap Source Account")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult updateLdapSourceAccount(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@RequestBody LdapSourceProfile ldapSource) {
		log.debug("updateLdapSourceAccount");
		ldapSource.setId(id);
		GeneralResult result = ldapMgmtService.updateLdapSourceAccount(ldapSource);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/ldaps/page/{page}/size/{size}", method = RequestMethod.GET)
	@ApiOperation(value = "Enterprise Ldap Source List", notes = "Enterprise Ldap Source List")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<LdapSourceProfile>> getEnterpriseLdapSourceList(
			@ApiParam(required = true, name = "enterpriseId", value = "Enterprise Id") @PathVariable("enterpriseId") String enterpriseId,
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size) {
		log.debug("getEnterpriseLdapSourceList");
		GeneralPagingResult<List<LdapSourceProfile>> result = ldapMgmtService.getEnterpriseLdapSourceList(enterpriseId,
				page, size);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldaps/page/{page}/size/{size}", method = RequestMethod.GET)
	@ApiOperation(value = "Ldap Source List", notes = "Ldap Source List")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<LdapSourceProfile>> getLdapSourceList(
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size) {
		log.debug("getLdapSourceList");
		GeneralPagingResult<List<LdapSourceProfile>> result = ldapMgmtService.getLdapSourceList(page, size);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Ldap Source", notes = "Delete Ldap Source")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult deleteLdapSource(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id) {
		log.debug("deleteLdapSource");
		GeneralResult result = ldapMgmtService.deleteLdapSource(id);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/status/{status}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Ldap Source Status", notes = "Delete Ldap Source Status")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralResult updateLdapSourceStatus(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@ApiParam(required = true, name = "status", value = "Ldap Source status") @PathVariable("status") Byte status) {
		log.debug("updateLdapSourceStatus");
		GeneralResult result = ldapMgmtService.updateLdapSourceStatus(id, status);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/ldaps/simple", method = RequestMethod.GET)
	@ApiOperation(value = "Simple Ldap Source List", notes = "Simple Ldap Source List")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLdapSourceList() {
		log.debug("getSimpleLdapSourceList");
		GeneralContentResult<List<LdapSourceProfile>> result = ldapMgmtService.getSimpleLdapSourceList();
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/ldaps/simple", method = RequestMethod.GET)
	@ApiOperation(value = "Enterprise Simple Ldap Source List", notes = "Enterprise Simple Ldap Source List")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<List<LdapSourceProfile>> getEnterpriseSimpleLdapSourceList(
			@ApiParam(required = true, name = "enterpriseId", value = "Enterprise Id") @PathVariable("enterpriseId") String enterpriseId) {
		log.debug("getEnterpriseSimpleLdapSourceList");
		GeneralContentResult<List<LdapSourceProfile>> result = ldapMgmtService
				.getEnterpriseSimpleLdapSourceList(enterpriseId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/noauth/ldaps", method = RequestMethod.GET)
	@ApiOperation(value = "Ldap Source Login List", notes = "Ldap Source Login List")
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLoginLdapSourceList(
			@ApiParam(required = true, name = "login", value = "Login Name") @RequestParam("login") String login) {
		log.debug("getSimpleLoginLdapSourceList");
		GeneralContentResult<List<LdapSourceProfile>> result = ldapMgmtService.getSimpleLoginLdapSourceList(login);
		return result;
	}

}
