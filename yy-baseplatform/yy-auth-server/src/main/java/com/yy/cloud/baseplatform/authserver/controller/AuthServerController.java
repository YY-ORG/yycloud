/**
 * Project Name:liz-auth-server
 * File Name:AuthServerController.java
 * Package Name:com.gemii.lizcloud.baseplatform.authserver.controller
 * Date:Jun 7, 20174:12:38 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.baseplatform.authserver.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:AuthServerController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Jun 7, 2017 4:12:38 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@RestController
@Slf4j
public class AuthServerController {
	@Autowired
	private SecurityClient securityClient;

	@RequestMapping("/user")
	public Principal user(Principal _user) {
		log.info("The login user is: {}", _user);
		if (_user == null) {
			return _user;
		}
		String loginName = _user.getName();
		GeneralContentResult<UserDetailsItem> tempResult = this.securityClient.loadUserByUsernameOrId(loginName);
		if (!tempResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)) {
			log.info("Could not get the current user {} 's detail info currently....", loginName);
			return _user;
		}
		return tempResult.getResultContent();
	}
}
