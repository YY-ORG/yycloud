package com.yy.cloud.core.finance.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.constant.SecurityConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.ApAcScoringReq;
import com.yy.cloud.common.data.dto.assess.ApAssessScoringReq;
import com.yy.cloud.common.data.dto.assess.AssessAnswerScoringReq;
import com.yy.cloud.common.data.otd.assess.*;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.finance.service.MarkedScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     1/3/18 3:12 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@RestController
@Api("评分和复核相关API")
public class MarkScoreController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MarkedScoreService markedScoreService;

    @RequestMapping(value = "/authsec/assesspaper/assessanswer/unmarklist", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户所在部门的未评分的试卷列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getAssessPaperAnswerItemList(
            @PageableDefault(sort = {"createDate"}, direction = Sort.Direction.ASC) Pageable _page
    ){
        GeneralPagingResult<List<AssessPaperExamineeMapItem>> result = new GeneralPagingResult<>();
        try {
            String tempOrgId = this.securityService.getCurrentUser().getDeptId();
            log.info("Going to retrieve the [{}]'s un-marked assess paper list.", tempOrgId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_MARK_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            log.info("Going to get [{}] un-marked answer paper list.", tempOrgId);
            result = this.markedScoreService.getUnMarkedAssessPaperListByOrg(tempOrgId, _page);
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


    @RequestMapping(value = "/authsec/assesspaper/assessanswer/unauditlist", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户所在部门的未复核的试卷列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnAuditAssessPaperAnswerItemList(
            @PageableDefault(sort = {"createDate"}, direction = Sort.Direction.ASC) Pageable _page
    ){
        GeneralPagingResult<List<AssessPaperExamineeMapItem>> result = new GeneralPagingResult<>();
        try {
            String tempOrgId = this.securityService.getCurrentUser().getDeptId();
            log.info("Going to retrieve the [{}]'s un-audited assess paper list.", tempOrgId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_AUDIT_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            result = this.markedScoreService.getUnAuditedAssessPaperListByOrg(tempOrgId, _page);
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


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/{_assessId}/markassessanswer", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个用户某个题的答案及答案的评分详情")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<MarkedAssessAnswer> getUnAuditAssessPaperAnswerItemList(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                        @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                                        @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId){
        GeneralContentResult<MarkedAssessAnswer> result = new GeneralContentResult<>();
        try {
            String tempUserId = _userId;
            if(_userId == null)
                tempUserId = this.securityService.getCurrentUser().getUserId();
            String tempOrgId = this.securityService.getCurrentUser().getDeptId();
            log.info("Going to retrieve the [{}]'s un-marked assess answer.", tempUserId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_MARK_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            result = this.markedScoreService.getUnMarkedAssessAnswer(tempUserId, _assessPaperId, _assessId);
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


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/{_assessId}/markassessanswer", method = RequestMethod.POST)
    @ApiOperation(value = "给某个用户某个题的答案初评分")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<MarkedAssessAnswer> markSocoreForAssessPaperAnswerItem(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                        @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                                        @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId,
                                                                                       @ApiParam(value = "评分详情") @RequestBody AssessAnswerScoringReq _req){
        GeneralContentResult<MarkedAssessAnswer> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            if(_userId == null)
                _userId = tempUserId;
            log.info("Going to scoring the [{}]'s [{}]-[{}] un-marked assess answer.", _userId, _assessPaperId, _assessId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_MARK_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            result = this.markedScoreService.markScoreAssessAnswer(tempUserId, _assessPaperId, _assessId, _req);
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

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/{_assessId}/auditassessanswer", method = RequestMethod.POST)
    @ApiOperation(value = "给某个用户某个题的答案复核评分")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<MarkedAssessAnswer> auditSocoreForAssessPaperAnswerItem(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                       @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                                       @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId,
                                                                                       @ApiParam(value = "评分详情") @RequestBody AssessAnswerScoringReq _req){
        GeneralContentResult<MarkedAssessAnswer> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            if(_userId == null)
                _userId = tempUserId;
            log.info("Going to scoring the [{}]'s [{}]-[{}] un-audit assess answer.", _userId, _assessPaperId, _assessId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_AUDIT_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            result = this.markedScoreService.auditScoreAssessAnswer(tempUserId, _assessPaperId, _assessId, _req);
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


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/markassessanswer", method = RequestMethod.POST)
    @ApiOperation(value = "提交某个用户某个卷子的答案初评总分")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitMarkSocoreForAssessPaper(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                            @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = _userId;
            String currentUserId = this.securityService.getCurrentUser().getUserId();
            if(_userId == null)
                tempUserId = currentUserId;
            log.info("Going to submit the scoring of [{}]'s [{}] assess paper.", tempUserId, _assessPaperId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_MARK_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            result = this.markedScoreService.submitAssessPaperScoring(_userId, _assessPaperId, currentUserId);
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

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/auditassessanswer", method = RequestMethod.POST)
    @ApiOperation(value = "提交某个用户某个卷子的答案复核总分")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitAuditScoreForAssessPaper(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                        @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId,
                                                        @ApiParam(value = "考核的等次") @RequestParam (value = "_level", required = false) Byte _level){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = _userId;
            String currentUserId = this.securityService.getCurrentUser().getUserId();
            Byte tempLevel = _level;
            if(_userId == null)
                tempUserId = currentUserId;
            if(_level == null)
                tempLevel = 1;
            log.info("Going to submit the audit Score of [{}]'s [{}] assess paper.", tempUserId, _assessPaperId);
            List<RoleItem> tempRuleList = this.securityService.getCurrentUser().getRoles();

            if(tempRuleList == null || tempRuleList.size() == 0)
                throw new YYException(ResultCode.ACCESS_LIMITED);
            boolean tempFlag = false;
            for(RoleItem tempItem : tempRuleList){
                if(tempItem.getCode().equals(SecurityConstant.ROLE_MARK_SCORE)){
                    tempFlag = true;
                    break;
                }
            }
            if(!tempFlag){
                throw new YYException(ResultCode.ACCESS_LIMITED);
            }
            result = this.markedScoreService.submitAssessPaperAuditScore(_userId, _assessPaperId, currentUserId, tempLevel);
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

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/category/scoring", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个卷子的分组评分设置列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<ApAcScoringItem>> getScoringCategoryListForAssessPaper(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId){
        GeneralContentResult<List<ApAcScoringItem>> result = new GeneralContentResult<>();
        try {
            log.info("Going to get the Category Scoring List for [{}] assess paper.", _assessPaperId);
            result = this.markedScoreService.getScoringCategoryListForPaper(_assessPaperId);
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

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/category/{_categoryId}/scoring", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取某个卷子的某个分组下试题的评分设置列表")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralPagingResult<List<ApAssessScoringItem>> getScoringAssessListForAssessPaper(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                             @ApiParam(value = "分组的ID") @PathVariable(value = "_categoryId", required = true) String _categoryId,
                                                                                             @PageableDefault(sort = { "seqNo" }, direction = Sort.Direction.ASC) Pageable _page){
        GeneralPagingResult<List<ApAssessScoringItem>> result = new GeneralPagingResult<>();
        try {
            log.info("Going to get the [{}]'s Assess Scoring List for [{}] assess paper.", _categoryId, _assessPaperId);
            result = this.markedScoreService.getScoringAssessListForPaper(_assessPaperId, _categoryId, _page);
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


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/category/scoring", method = RequestMethod.POST)
    @ApiOperation(value = "提交某个卷子的分组评分设置详情")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitCategoryScoringForAssessPaper(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                             @ApiParam(value = "评分设置详情") @RequestBody List<ApAcScoringReq> _reqList){
        GeneralResult result = new GeneralResult();
        try {
            String currentUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to submit the Scoring Settings for [{}] assess paper.", _assessPaperId);
            result = this.markedScoreService.commitScoringForApAc(currentUserId, _reqList);
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

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/category/{_categoryId}/scoring", method = RequestMethod.POST)
    @ApiOperation(value = "提交某个卷子某个分组的试题评分设置详情")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitAssessScoringForAssessPaper(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                           @ApiParam(value = "分组的ID") @PathVariable(value = "_categoryId", required = true) String _categoryId,
                                                             @ApiParam(value = "评分设置详情") @RequestBody List<ApAssessScoringReq> _reqList){
        GeneralResult result = new GeneralResult();
        try {
            String currentUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to submit the [{}]'s Scoring Settings for [{}] assess paper.", _categoryId, _assessPaperId);
            result = this.markedScoreService.commitScoringForApAssess(currentUserId, _reqList);
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
}
