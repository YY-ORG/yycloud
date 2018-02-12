package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.constant.SecurityConstant;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerScoringReq;
import com.yy.cloud.common.data.otd.assess.AssessPaperExamineeMapItem;
import com.yy.cloud.common.data.otd.assess.MarkedAssessAnswer;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperAnswerItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.service.MarkedScoreService;
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
            String tempUserId = _userId;
            if(_userId == null)
                tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to scoring the [{}]'s [{}]-[{}] un-marked assess answer.", tempUserId, _assessPaperId, _assessId);
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
            String tempUserId = _userId;
            if(_userId == null)
                tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to scoring the [{}]'s [{}]-[{}] un-audit assess answer.", tempUserId, _assessPaperId, _assessId);
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
}
