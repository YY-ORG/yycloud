package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.constant.SecurityConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.assess.AssessPaperExamineeMapItem;
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
                if(tempItem.getRoleName().equals(SecurityConstant.ROLE_MARK_SCORE)){
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
    @ApiOperation(value = "获取当前用户所在部门的未评分的试卷列表")
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
                if(tempItem.getRoleName().equals(SecurityConstant.ROLE_AUDIT_SCORE)){
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

}
