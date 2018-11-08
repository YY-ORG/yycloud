package com.yy.cloud.core.finance.controller;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.assess.AssessCategoryItem;
import com.yy.cloud.common.data.dto.assess.AssessCategoryReq;
import com.yy.cloud.common.data.dto.assess.AssessCategoryWithIDReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessCategoryItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.finance.service.AssessCategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 6:09 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@RestController
public class SubjectController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AssessCategoryService assessCategoryService;

    @RequestMapping(value = "/authsec/finance/subject", method = RequestMethod.POST)
    @ApiOperation(value = "创建财务科目")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<AssessCategoryItem> createAssessCategory(@ApiParam(value = "考题分组属性") @RequestBody AssessCategoryReq _profile) {
        GeneralContentResult<AssessCategoryItem> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to create Assess Category [{}].", tempUserId);
            result = this.assessCategoryService.createAssessCategory(tempUserId, _profile);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assess/category", method = RequestMethod.PUT)
    @ApiOperation(value = "更新考题分组")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<AssessCategoryItem> updateAssessCategory(@ApiParam(value = "考题分组属性") @RequestBody AssessCategoryWithIDReq _profile) {
        GeneralContentResult<AssessCategoryItem> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to update Assess Category [{}].", tempUserId);
            result = this.assessCategoryService.updateAssessCategory(tempUserId, _profile);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assess/category/{_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除考题分组")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult deleteAssessCategory(@ApiParam(value = "考题分组的ID") @PathVariable(value = "_id") String _id) {
        GeneralResult result = new GeneralResult();
        try {
            log.info("Going to delete Assess Category [{}].", _id);
            result = this.assessCategoryService.deleteAssessCategory(_id);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assess/category/categorylist", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取所有考题分组")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<SimpleAssessCategoryItem>> getCategoryItemListByPage(Pageable _page){
        GeneralPagingResult<List<SimpleAssessCategoryItem>> result = new GeneralPagingResult<>();
        try {
            log.info("Going to load all of the assess's category.Page=[{}]", _page);
            result = this.assessCategoryService.getAssessCategoryListByPage(_page);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_id}/category", method = RequestMethod.POST)
    @ApiOperation(value = "为考卷增加考题分组")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult addCategoryToAssessPaper(@ApiParam(value = "考卷的ID") @PathVariable(value = "_id") String _id,
                                                  @ApiParam(value = "考题分组的ID List") @RequestBody List<String> _categoryList){
        GeneralResult result = new GeneralResult();
        try {
            log.info("Going to add [{}]'s category.Page=[{}]", _id);
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            result = this.assessCategoryService.addCategoryToAssessPaper(tempUserId, _id, _categoryList);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_id}/category/categorylist", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个考卷的考题分组")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<SimpleAssessCategoryItem>> getCategoryListByAssessPaper(@ApiParam(value = "考卷的ID") @PathVariable(value = "_id") String _id) {
        GeneralContentResult<List<SimpleAssessCategoryItem>> result = new GeneralContentResult<>();
        try {
            log.info("Going to load Assess paper [{}]'s Category.", _id);
            result = this.assessCategoryService.getCategoryListByAssessPaper(_id);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
