package com.gemii.lizcloud.api.group.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapSourceProfile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * ClassName: LdapMgmtClient <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月16日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */

@FeignClient("usermgmt")
// @FeignClient(url = "http://localhost:9101", name = "usermgmt")
public interface LdapMgmtClient {
	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/test", method = RequestMethod.POST)
	@ApiOperation(value = "Test Ldap Source", notes = "Test Ldap Source")
	public GeneralResult testLdapSource(@RequestBody LdapSourceProfile ldapSource);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap", method = RequestMethod.POST)
	@ApiOperation(value = "Create Ldap Source", notes = "Create Ldap Source")
	public GeneralContentResult<String> createLdapSource(@RequestBody LdapSourceProfile ldapSource);

	@ResponseBody
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/ldap", method = RequestMethod.POST)
	@ApiOperation(value = "Create Ldap Source", notes = "Create Ldap Source")
	public GeneralContentResult<String> createEnterpriseLdapSource(
			@ApiParam(required = true, name = "enterpriseId", value = "Enterprise Id") @PathVariable("enterpriseId") String enterpriseId,
			@RequestBody LdapSourceProfile ldapSource);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Ldap Source Details", notes = "Ldap Source Details")
	public GeneralContentResult<LdapSourceProfile> getLdapSource(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Ldap Source", notes = "Update Ldap Source")
	public GeneralResult updateLdapSource(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@RequestBody LdapSourceProfile ldapSource);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/account", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Ldap Source Account", notes = "Update Ldap Source Account")
	public GeneralResult updateLdapSourceAccount(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@RequestBody LdapSourceProfile ldapSource);

	@ResponseBody
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/ldaps/page/{page}/size/{size}", method = RequestMethod.GET)
	@ApiOperation(value = "Enterprise Ldap Source List", notes = "Enterprise Ldap Source List")
	public GeneralPagingResult<List<LdapSourceProfile>> getEnterpriseLdapSourceList(
			@ApiParam(required = true, name = "enterpriseId", value = "Enterprise Id") @PathVariable("enterpriseId") String enterpriseId,
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldaps/page/{page}/size/{size}", method = RequestMethod.GET)
	@ApiOperation(value = "Ldap Source List", notes = "Ldap Source List")
	public GeneralPagingResult<List<LdapSourceProfile>> getLdapSourceList(
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Ldap Source", notes = "Delete Ldap Source")
	public GeneralResult deleteLdapSource(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/status/{status}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Ldap Source Status", notes = "Delete Ldap Source Status")
	public GeneralResult updateLdapSourceStatus(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@ApiParam(required = true, name = "status", value = "Ldap Source status") @PathVariable("status") Byte status);

	@ResponseBody
	@RequestMapping(value = "/authsec/ldaps/simple", method = RequestMethod.GET)
	@ApiOperation(value = "Simple Ldap Source List", notes = "Simple Ldap Source List")
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLdapSourceList();

	@ResponseBody
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/ldaps/simple", method = RequestMethod.GET)
	@ApiOperation(value = "Enterprise Simple Ldap Source List", notes = "Enterprise Simple Ldap Source List")
	public GeneralContentResult<List<LdapSourceProfile>> getEnterpriseSimpleLdapSourceList(
			@ApiParam(required = true, name = "enterpriseId", value = "Enterprise Id") @PathVariable("enterpriseId") String enterpriseId);

	@ResponseBody
	@RequestMapping(value = "/noauth/ldaps", method = RequestMethod.GET)
	@ApiOperation(value = "Ldap Source Login List", notes = "Ldap Source Login List")
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLoginLdapSourceList(
			@ApiParam(required = true, name = "login", value = "Login Name") @RequestParam("login") String login);

}
