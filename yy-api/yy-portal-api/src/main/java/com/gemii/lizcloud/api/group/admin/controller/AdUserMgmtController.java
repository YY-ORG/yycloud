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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gemii.lizcloud.api.group.admin.service.AdUserMgmtService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.AdUserProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: AdUserMgmtController <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月21日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Controller
@Slf4j
public class AdUserMgmtController {

	@Autowired
	private AdUserMgmtService adUserMgmtService;

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/adusers/page/{page}/size/{size}", method = RequestMethod.POST)
	@ApiOperation(value = "Ldap User List", notes = "Ldap User List")
	public GeneralPagingResult<List<AdUserProfile>> getLdapUserList(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size,
			@RequestBody LdapUserCriteriaQuery criteriaQuery) {
		log.debug("getLdapUserList");
		GeneralPagingResult<List<AdUserProfile>> result = adUserMgmtService.getLdapUserList(id, page, size,
				criteriaQuery);
		return result;
	}

	/**
	 * 创建AD认证类型用户
	 * @param profile
	 * @return
     */
	@ResponseBody
	@RequestMapping(value = "/authsec/aduser", method = RequestMethod.POST)
	@ApiOperation(value = "Create Ad User", notes = "Creat Ad User")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
	})
	public GeneralContentResult<String> createAdUser(@RequestBody AdUserProfile profile) {
		log.debug("createAdUser");
		GeneralContentResult<String> result = adUserMgmtService.createAdUser(profile);
		return result;
	}

}
