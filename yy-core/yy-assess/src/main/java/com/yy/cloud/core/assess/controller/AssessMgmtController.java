/**
 * Project Name:yy-assess
 * File Name:AssessMgmtController.java
 * Package Name:com.yy.cloud.core.assess.controller
 * Date:Sep 26, 20178:33:53 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.assess.AssessItem;
import com.yy.cloud.core.assess.service.AssessMgmtService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * ClassName:AssessMgmtController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 26, 2017 8:33:53 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Slf4j
@RestController
public class AssessMgmtController {
	@Autowired
	private AssessMgmtService assessService;
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/authsec/assess/{_id}", method = RequestMethod.GET)
	@ApiOperation(value = "依据考题ID，获取考题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<AssessItem> getAssess(@PathVariable(value = "_id", required = true) String _assessId) {
		GeneralContentResult<AssessItem> result = new GeneralContentResult<AssessItem>();
		try {
			result = this.assessService.getAssessItemById(_assessId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	public GeneralContentResult<List<AssessMenuItem>> getAssessListByOrg(){
		GeneralContentResult<List<AssessMenuItem>> result = new GeneralContentResult<List<AssessMenuItem>>();
		try {
			String tempUserId = this.securityService.getCurrentUser().getUserId();
			String tempOrgId = this.securityService.getCurrentUser().getOrganizationId();
			log.info("Is going to retrieve the assess menu list for [{}] -> [{}]", tempOrgId, tempUserId);
			result = this.assessService.getAssessMenu(tempUserId, tempOrgId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}
}