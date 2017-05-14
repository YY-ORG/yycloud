package com.yy.cloud.core.usermgmt.controller;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.accountcenter.AdLoginReq;
import com.yy.cloud.common.data.dto.accountcenter.AdUserProfile;
import com.yy.cloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;
import com.yy.cloud.core.usermgmt.service.AdUserMgmtService;
import com.yy.cloud.core.usermgmt.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: AdUserMgmtController <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月4日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Controller
@Slf4j
@CrossOrigin
public class AdUserMgmtController {

	@Autowired
	private AdUserMgmtService adUserMgmtService;

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/authsec/ldap/{id}/adusers/page/{page}/size/{size}", method = RequestMethod.POST)
	@ApiOperation(value = "Ldap User List", notes = "Ldap User List")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<AdUserProfile>> getLdapUserList(
			@ApiParam(required = true, name = "id", value = "Ldap Source Id") @PathVariable("id") String id,
			@ApiParam(required = false, name = "page", value = "Ldap Source list page index") @PathVariable(value = "page") Integer page,
			@ApiParam(required = false, name = "size", value = "Ldap Source list page size") @PathVariable(value = "size") Integer size,
			@RequestBody LdapUserCriteriaQuery criteriaQuery) {
		log.debug("getLdapUserList ");
		return adUserMgmtService.findAdUserProfile(id, criteriaQuery, null);
	}

	@ResponseBody
	@RequestMapping(value = "/authsec/aduser/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Ad User Details", notes = "Ad User Details")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<AdUserProfile> getAdUser(
			@ApiParam(required = true, name = "id", value = "User Id") @PathVariable("id") String id) {
		log.debug("getAdUser ");
		GeneralContentResult<AdUserProfile> result = new GeneralContentResult<AdUserProfile>();
		result.setResultCode(ResultCode.OPERATION_SUCCESS);
		result.setResultContent(new AdUserProfile());
		return result;
	}

	/**
	 * 创建AD认证类型用户
	 * 
	 * @param profile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authsec/aduser", method = RequestMethod.POST)
	@ApiOperation(value = "创建AD用户", notes = "创建AD用户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralContentResult<String> createAdUser(@RequestBody AdUserProfile profile) {
		log.debug("createAdUser ");
		GeneralContentResult<String> result = null;
		ExtLdapUser ldapUser = adUserMgmtService.getAdUser(profile.getLoginName(), profile.getLdapId());
		if (ldapUser != null) {
			result = new GeneralContentResult<String>();
			result.setResultCode(ResultCode.ADUSERMGMT_ADUSER_DUPLICATED_EXCEPTION);
			result.setDetailDescription("User existed.");
		} else {
			profile.setAuthMode(AdUserMgmtConstants.USER_TYPE_AD);
			String userId = userService.createUser(profile);
			if (userId != null) {
				result = adUserMgmtService.createAdUser(profile, userId);
			} else {
				result = new GeneralContentResult<String>();
				result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
				result.setDetailDescription("USER CREATE FAILED");
			}
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/noauth/aduser/login", method = RequestMethod.POST)
	@ApiOperation(value = "Login Ad User", notes = "Login Ad User")
	public GeneralContentResult<String> loginAdUser(@RequestBody AdLoginReq loginReq) {
		log.debug("loginAdUser ");
		GeneralContentResult<String> result = adUserMgmtService.loginAdUser(loginReq);
		return result;
	}

}
