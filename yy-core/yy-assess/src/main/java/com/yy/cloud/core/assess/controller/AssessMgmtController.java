/**
 * Project Name:yy-assess
 * File Name:AssessMgmtController.java
 * Package Name:com.yy.cloud.core.assess.controller
 * Date:Sep 26, 20178:33:53 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.assess.AssessGroupItem;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.assess.AssessPaperItem;
import com.yy.cloud.common.data.dto.assess.*;
import com.yy.cloud.common.data.dto.metadata.TemplateItemProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateItemWithIDProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateProfileReq;
import com.yy.cloud.common.data.dto.metadata.TemplateWithIDProfileReq;
import com.yy.cloud.common.data.otd.assess.AssessPeriodItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperItem;
import com.yy.cloud.common.data.otd.metadata.ComplexTemplateItem;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplate;
import com.yy.cloud.common.data.otd.metadata.SimpleTemplateItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.data.domain.IPerAssessPaperAnnual;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
			String tempOrgId = this.securityService.getCurrentUser().getDeptId();
			Byte tempTitle = this.securityService.getCurrentUser().getProfessionalTitle();
			log.info("Is going to retrieve the assess menu list for [{}] -> [{}]", tempOrgId, tempUserId);
			result = this.assessService.getAssessPaperList(tempUserId, tempOrgId, tempTitle);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper/{_id}/assesslist", method = RequestMethod.GET)
	@ApiOperation(value = "依据考卷ID来检索该考卷下的某个组或全部试题列表")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<AssessGroupItem>> getAssessByAssessPaperId(@ApiParam(value = "考卷ID")
																				   @PathVariable(value = "_id") String _assessPaperId,
																				@ApiParam(value = "考卷组的ID")
																			   @RequestParam(value = "_groupId", required = false) String _groupId){
		GeneralContentResult<List<AssessGroupItem>> result = new GeneralContentResult<>();
		try {
			String tempUserId = this.securityService.getCurrentUser().getUserId();
			log.info("Is going to retrieve the assess menu list for Assess Paper [{}] with Group [{}].", _assessPaperId, _groupId);
			if(_groupId == null)
				result = this.assessService.getAssessMenuByAssessPaperId(tempUserId, _assessPaperId);
			else
				result = this.assessService.getAssessMenuByAssessPaperIdAndGroup(tempUserId, _assessPaperId, _groupId);

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
			String tempOrgId = this.securityService.getCurrentUser().getDeptId();
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
	public GeneralContentResult<SimpleAssessItem> createAssess( @ApiParam(value = "创建题目") @RequestBody AssessProfileReq _profile) {
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
	public GeneralContentResult<SimpleAssessItem> updateAssess( @ApiParam(value = "更新题目") @RequestBody AssessWithIDProfileReq _profile) {
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

	@RequestMapping(value = "/authsec/assess/{_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除某个考题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralResult deleteAssessByID(@ApiParam(value = "更新题目模板") @PathVariable(value = "_id") String _assessId) {
		GeneralResult result = new GeneralResult();
		try {
			log.info("Going to delete assess [{}].", _assessId);
			result = this.assessService.deleteAssess(_assessId);
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
	public GeneralContentResult<SimpleTemplate> createAssessTemplate(@ApiParam(value = "创建题目模板") @RequestBody TemplateProfileReq _profile) {
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
	public GeneralContentResult<SimpleTemplate> updateAssessTemplate(@ApiParam(value = "更新题目模板") @RequestBody TemplateWithIDProfileReq _profile) {
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

	@RequestMapping(value = "/authsec/assess/template/{_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除某个考题的某个模板")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralResult deleteAssessTemplateByID(@ApiParam(value = "删除题目模板") @PathVariable(value = "_id") String _templateId) {
		GeneralResult result = new GeneralResult();
		try {
			log.info("Going to delete assess's template [{}].", _templateId);
			result = this.assessService.deleteAssessTemplate(_templateId);
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
	public GeneralContentResult<SimpleTemplateItem> createAssessTemplateItem(@ApiParam(value = "创建题目元素项") @RequestBody TemplateItemProfileReq _profile) {
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
    public GeneralContentResult<SimpleTemplateItem> updateAssessTemplateItem(@ApiParam(value = "更新题目元素项") @RequestBody TemplateItemWithIDProfileReq _profile) {
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

	@RequestMapping(value = "/authsec/assess/template/templateitem/{_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除某个模板元素")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralResult deleteAssessTemplateItemByID(@ApiParam(value = "删除题目模板元素") @PathVariable(value = "_id") String _templateItemId) {
		GeneralResult result = new GeneralResult();
		try {
			log.info("Going to delete assess's template's Item [{}].", _templateItemId);
			result = this.assessService.deleteAssessTemplateItem(_templateItemId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper", method = RequestMethod.POST)
	@ApiOperation(value = "创建考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<SimpleAssessPaperItem> createAssessPaper(@ApiParam(value = "创建考卷") @RequestBody AssessPaperProfileReq _profile) {
		GeneralContentResult<SimpleAssessPaperItem> result = new GeneralContentResult<>();
		try {
			result = this.assessService.createAssessPaper(_profile);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (YYException ye) {
			log.error("YYException occured: {}", ye.getCode());
			result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ye.getCode()));
			result.setResultCode(ye.getCode());
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper/copy", method = RequestMethod.GET)
	@ApiOperation(value = "复制考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralResult copyAssessPaper(@ApiParam(value = "源考卷") @RequestParam(name = "_sourceId") String _sourceId,
																	   @ApiParam(value = "目标年份") @RequestParam(name = "_destAnnual", required = false) Integer _destAnnual) {
		GeneralResult result = new GeneralResult();
		try {
			result = this.assessService.copyAssessPaper(_sourceId, _destAnnual, this.securityService.getCurrentUser().getUserId());

			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (YYException ye) {
			log.error("YYException occured: {}", ye.getCode());
			result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ye.getCode()));
			result.setResultCode(ye.getCode());
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESSPAPER_CREATE_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper", method = RequestMethod.PUT)
	@ApiOperation(value = "更新考题考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<SimpleAssessPaperItem> updateAssessPaperItem(@ApiParam(value = "更新考卷") @RequestBody AssessPaperWithIDProfileReq _profile) {
		GeneralContentResult<SimpleAssessPaperItem> result = new GeneralContentResult<>();
		try {
			log.info("Going to update assess paper [{}].", _profile.getId());
			result = this.assessService.updateAssessPaper(_profile);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (YYException ye) {
			log.error("YYException occured: {}", ye.getCode());
			result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ye.getCode()));
			result.setResultCode(ye.getCode());
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper/{_id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除某个考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralResult deleteAssessPaperByID(@ApiParam(value = "删除某个考卷") @PathVariable(value = "_id") String _assessPaperId) {
		GeneralResult result = new GeneralResult();
		try {
			log.info("Going to delete assess paper [{}].", _assessPaperId);
			result = this.assessService.deleteAssessPaper(_assessPaperId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesses/assesslist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索题库中的试题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleAssessItem>> getAssessList(@PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleAssessItem>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load assess by page [{}].", _page);
			result = this.assessService.getAssessList(_page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}


	@RequestMapping(value = "/authsec/assesspaper/{_id}/assesslistpage", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索某个考卷的试题")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleAssessItem>> getAssessListByAssessPaper(@ApiParam(value = "考卷的ID") @PathVariable(value = "_id") String _assessPaperId,
																				  @ApiParam(value = "考卷组的ID")
																					  @RequestParam(value = "_groupId", required = false) String _groupId,
																				  @PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleAssessItem>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load assess list by assess paper [{}] and page [{}].", _assessPaperId, _page);
			if(_groupId == null)
				result = this.assessService.getAssessListByAssessPaper(_assessPaperId, _page);
			else
				result = this.assessService.getAssessListByAssessPaper(_assessPaperId, _groupId, _page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper/assesspaperlist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索所有的考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessAssessPaperList(@PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleAssessPaperItem>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load assess paper list by page [{}].", _page);
			result = this.assessService.getAssessPaperList(_page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assesspaper/allassesspaperlist", method = RequestMethod.GET)
	@ApiOperation(value = "检索所有的考卷")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<SimpleAssessPaperItem>> getAllAssessAssessPaperList(){
		GeneralContentResult<List<SimpleAssessPaperItem>> result = new GeneralContentResult<>();
		try {
			log.info("Going to load assess paper list.");
			result = this.assessService.getAllAssessPaperList();
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/org/assesspaper/assesspaperlist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索当前登录用户所在部门的考卷列表")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessAssessPaperListByOrg(
			@ApiParam(value = "职称")
			@RequestParam(value = "_title", required = false) Byte _title,
			@PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleAssessPaperItem>> result = new GeneralPagingResult<>();
		try {
			String tempOrgId = this.securityService.getCurrentUser().getDeptId();
			log.info("Going to load assess paper list by org [{}] page [{}].", tempOrgId, _page);
			if(_title == null)
				result = this.assessService.getAssessPaperListByOrg(tempOrgId, _page);
			else
				result = this.assessService.getAssessPaperListByOrg(tempOrgId, _title, _page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/org/{_orgId}/assesspaper/assesspaperlist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索某个部门的考卷列表")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleAssessPaperItem>> getAssessAssessPaperListByOrg(@ApiParam(value = "部门的ID") @PathVariable(value = "_orgId") String _orgId,
																						  @ApiParam(value = "职称", required = false)
																						  @RequestParam(value = "_title") Byte _title,
																						  @PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleAssessPaperItem>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load assess paper list by org [{}] page [{}].", _orgId, _page);
			if(_title == null)
				result = this.assessService.getAssessPaperListByOrg(_orgId, _page);
			else
				result = this.assessService.getAssessPaperListByOrg(_orgId, _title, _page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}


	@RequestMapping(value = "/authsec/template/templatelist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索所有的某个类型的考题模板")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleTemplate>> getAssessTemplateListByPage(@RequestParam(value = "_type", required = false) Byte _type,
																				 @PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleTemplate>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load all of the assess's template, type=[{}] Page=[{}].", _type, _page);
			if(_type == null)
				result = this.assessService.getAssessTemplateList(_page);
			else
				result = this.assessService.getAssessTemplateList(_type, _page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}


	@RequestMapping(value = "/authsec/templates", method = RequestMethod.GET)
	@ApiOperation(value = "检索所有的某个类型的考题模板")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<SimpleTemplate>> getAssessTemplateList(@RequestParam(value = "_type", required = true) Byte _type){
		GeneralContentResult<List<SimpleTemplate>> result = new GeneralContentResult<>();
		try {
			log.info("Going to load all of the assess's template, type=[{}].", _type);
			result = this.assessService.getTemplateListByType(_type);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}


	@RequestMapping(value = "/authsec/assess/{_id}/template", method = RequestMethod.GET)
	@ApiOperation(value = "获取某个试题的模板")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<SimpleTemplate>> getAssessTemplate(@ApiParam(value = "试题的ID") @PathVariable(value = "_id") String _id) {
		GeneralContentResult<List<SimpleTemplate>> result = new GeneralContentResult<>();
		try {
			log.info("Going to get assess [{}]'s template.", _id);
			result = this.assessService.getAssessTemplateByAssess(_id);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/templateitem/templateitemlist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索所有的考题模板元素")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<SimpleTemplateItem>> getAssessTemplateItemList(@PageableDefault(sort = { "code" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<SimpleTemplateItem>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load all of the assess's templateItem.Page=[{}]", _page);
			result = this.assessService.getAssessTemplateItemList(_page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/template/{_id}/templateitemlist", method = RequestMethod.GET)
	@ApiOperation(value = "获取某个试题模板的元素列表")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<ComplexTemplateItem>> getAssessTemplateItemByTemplate(@ApiParam(value = "试题模板的ID") @PathVariable(value = "_id") String _id) {
		GeneralContentResult<List<ComplexTemplateItem>> result = new GeneralContentResult<>();
		try {
			log.info("Going to get template [{}]'s templateItem.", _id);
			result = this.assessService.getAssessTemplateItemByTemplate(_id);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESS_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assessespaper/periodlist", method = RequestMethod.GET)
	@ApiOperation(value = "分页检索题库中的试题的有效期设置")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralPagingResult<List<AssessPeriodItem>> getAssessPaperPeriodList(@PageableDefault(sort = { "name" }, direction = Sort.Direction.ASC) Pageable _page){
		GeneralPagingResult<List<AssessPeriodItem>> result = new GeneralPagingResult<>();
		try {
			log.info("Going to load assess period by page [{}].", _page);
			result = this.assessService.getAssessPeriodPageList(_page);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESSPAPER_PERIOD_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assessespaper/perioditems", method = RequestMethod.POST)
	@ApiOperation(value = "批量更新试题的有效期设置")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralResult updateAssessPaperPeriod(@ApiParam(value = "更新考卷") @RequestBody List<AssessPeriodReq> _profileList){
		GeneralResult result = new GeneralResult();
		try {
			String tempUserId = this.securityService.getCurrentUser().getUserId();
			log.info("Going to update assess period [{}].", _profileList.size());
			result = this.assessService.updateAssessPeriodList(_profileList, tempUserId);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESSPAPER_PERIOD_GET_FAILED);
		}
		return result;
	}

	@RequestMapping(value = "/authsec/assessespaper/annuallist", method = RequestMethod.GET)
	@ApiOperation(value = "检索题库中的试题的年度列表")
	@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
			value = "Token", defaultValue = "bearer ")
	public GeneralContentResult<List<IPerAssessPaperAnnual>> getAssessPaperAnnualList(){
		GeneralContentResult<List<IPerAssessPaperAnnual>> result = new GeneralContentResult<>();
		try {
			log.info("Going to load assess papers' annual list.");
			result = this.assessService.getAssessPaperAnnualList();
		} catch (Exception e) {
			log.error("Unexpected Error occured", e);
			result.setDetailDescription("Unexpected Error occured...");
			result.setResultCode(ResultCode.ASSESSPAPER_PERIOD_GET_FAILED);
		}
		return result;
	}

}
