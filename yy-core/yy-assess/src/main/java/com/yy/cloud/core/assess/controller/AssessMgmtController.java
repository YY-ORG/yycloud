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
import com.yy.cloud.common.data.assess.AssessPaperItem;
import com.yy.cloud.common.data.dto.assess.AssessProfileReq;
import com.yy.cloud.common.data.dto.assess.AssessWithIDProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateItemProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateItemWithIDProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateWithIDProfileReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessItem;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplate;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplateItem;
import com.yy.cloud.common.service.SecurityService;
import io.swagger.annotations.ApiParam;
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

	@RequestMapping(value = "/authsec/assesspaperlist/orgnization", method = RequestMethod.GET)
	@ApiOperation(value = "依据登录用户所属部门/组织机构来检索该部门下的考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<AssessPaperItem>> getAssessPaperByOrg(){
		GeneralContentResult<List<AssessPaperItem>> result = new GeneralContentResult<List<AssessPaperItem>>();
		try {
			String tempUserId = this.securityService.getCurrentUser().getUserId();
			String tempOrgId = this.securityService.getCurrentUser().getOrganizationId();
			log.info("Is going to retrieve the assess menu list for [{}] -> [{}]", tempOrgId, tempUserId);
			result = this.assessService.getAssessPaperList(tempUserId, tempOrgId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper/{_id}/assesslist", method = RequestMethod.GET)
	@ApiOperation(value = "依据考卷ID来检索该考卷下的试题列表")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<AssessMenuItem>> getAssessByAssessPaperId(@ApiParam(value = "Assess Profile")
																				   @RequestBody String _assessPaperId){
		GeneralContentResult<List<AssessMenuItem>> result = new GeneralContentResult<List<AssessMenuItem>>();
		try {
			log.info("Is going to retrieve the assess menu list for Assess Paper [{}]", _assessPaperId);
			result = this.assessService.getAssessMenuByAssessPaperId(_assessPaperId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}


	@RequestMapping(value = "/authsec/assesslist/orgnization", method = RequestMethod.GET)
	@ApiOperation(value = "依据登录用户所属部门/组织机构来检索该部门下的考题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
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

	@RequestMapping(value = "/authsec/assess", method = RequestMethod.POST)
	@ApiOperation(value = "单独创建考题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<SimpleAssessItem> createAssess( @ApiParam(value = "创建题目") AssessProfileReq _profile) {
		GeneralContentResult<SimpleAssessItem> result = new GeneralContentResult<>();
		try {
			result = this.assessService.createAssess(_profile);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assess", method = RequestMethod.PUT)
	@ApiOperation(value = "更新考题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<SimpleAssessItem> updateAssess( @ApiParam(value = "更新题目") AssessWithIDProfileReq _profile) {
		GeneralContentResult<SimpleAssessItem> result = new GeneralContentResult<>();
		try {
			result = this.assessService.updateAssess(_profile);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assess/template/templateitem", method = RequestMethod.POST)
	@ApiOperation(value = "创建考题的元素项")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<SimpleTemplateItem> createAssessTemplateItem(@ApiParam(value = "创建题目元素项") TemplateItemProfileReq _profile) {
		GeneralContentResult<SimpleTemplateItem> result = new GeneralContentResult<>();
		try {
			result = this.assessService.createAssessTemplateItem(_profile);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}


    @RequestMapping(value = "/authsec/assess/template/templateitem", method = RequestMethod.PUT)
    @ApiOperation(value = "更新考题的元素项")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<SimpleTemplateItem> updateAssessTemplateItem(@ApiParam(value = "更新题目元素项") TemplateItemWithIDProfileReq _profile) {
        GeneralContentResult<SimpleTemplateItem> result = new GeneralContentResult<>();
        try {
            result = this.assessService.updateAssessTemplateItem(_profile);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

	@RequestMapping(value = "/authsec/assess/template", method = RequestMethod.POST)
	@ApiOperation(value = "创建考题的模板")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<SimpleTemplate> createAssessTemplate(@ApiParam(value = "创建题目模板") TemplateProfileReq _profile) {
		GeneralContentResult<SimpleTemplate> result = new GeneralContentResult<>();
		try {
			result = this.assessService.createAssessTemplate(_profile);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

    @RequestMapping(value = "/authsec/assess/template", method = RequestMethod.PUT)
    @ApiOperation(value = "更新考题的模板")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<SimpleTemplate> updateAssessTemplate(@ApiParam(value = "更新题目模板") TemplateWithIDProfileReq _profile) {
        GeneralContentResult<SimpleTemplate> result = new GeneralContentResult<>();
        try {
            result = this.assessService.updateAssessTemplate(_profile);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
