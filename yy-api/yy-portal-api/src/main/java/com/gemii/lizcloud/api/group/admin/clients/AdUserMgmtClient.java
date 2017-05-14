package com.gemii.lizcloud.api.group.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.AdLoginReq;
import com.gemii.lizcloud.common.data.dto.accountcenter.AdUserProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * ClassName: AdUserMgmtClient <br/>
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
public interface AdUserMgmtClient {
	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/adusers/page/{page}/size/{size}", method = RequestMethod.POST)
	@ApiOperation(value = "Ldap User List", notes = "Ldap User List")
	public GeneralPagingResult<List<AdUserProfile>> getLdapUserList(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size,
			@RequestBody LdapUserCriteriaQuery criteriaQuery);

	@ResponseBody
	@RequestMapping(value = "/authsec/aduser", method = RequestMethod.POST)
	@ApiOperation(value = "Create Ad User", notes = "Create Ad User")
	public GeneralContentResult<String> createAdUser(@RequestBody AdUserProfile profile);

	@ResponseBody
	@RequestMapping(value = "/authsec/aduser/login", method = RequestMethod.POST)
	@ApiOperation(value = "Login Ad User", notes = "Login Ad User")
	public GeneralContentResult<String> loginAdUser(@RequestBody AdLoginReq loginReq);
}
