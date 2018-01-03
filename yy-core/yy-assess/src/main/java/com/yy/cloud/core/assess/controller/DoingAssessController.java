package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;
import com.yy.cloud.common.data.dto.assess.AssessTemplateReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessAnswerItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperAnswerItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.service.DoingAssessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 10:48 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */

@Slf4j
@RestController
@Api("做题相关API")
public class DoingAssessController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private DoingAssessService doingAssessService;

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/group/{_groupId}/assess/{_assessId}/assessanswer", method = RequestMethod.POST)
    @ApiOperation(value = "考生提交某个卷子某个题(单答案题)的答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitAssessAnswer(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                            @ApiParam(value = "题所属的试卷分组ID") @PathVariable(value = "_groupId", required = true) String _groupId,
                                            @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                      //      @ApiParam(value = "答案的类型：0为单答案题的答案；1为单答案题的子项答案；2为多答案题的某个或者某些答案") @RequestParam(value = "_type", defaultValue = "0") Byte _type,
                                            @ApiParam(value = "题的答案，以题的模板为单位来封装，如果一个题有多个答案，则一个模板则会封装多个List元素") @RequestBody List<AssessTemplateReq> _groupSummaryReq){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to submit answer for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setGroupId(_groupId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);
            tempAnswerReq.setAnswerList(_groupSummaryReq);

            result = this.doingAssessService.submitSingleAnswerAssessAnswer(tempUserId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/group/{_groupId}/assess/{_assessId}/assessanswer/subanswer", method = RequestMethod.POST)
    @ApiOperation(value = "考生增加某个卷子某个题的元素项答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<String>> addAssessAnswerSubAnser(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                      @ApiParam(value = "题所属的试卷分组ID") @PathVariable(value = "_groupId", required = true) String _groupId,
                                                                         @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                         @ApiParam(value = "题的答案，以题的模板为单位来封装，如果一个题有多个答案，则一个模板则会封装多个List元素") @RequestBody List<AssessTemplateReq> _groupSummaryReq){
        GeneralContentResult<List<String>> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to submit sub answer for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setGroupId(_groupId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);
            tempAnswerReq.setAnswerList(_groupSummaryReq);

            result = this.doingAssessService.addAssessSubAnswer(tempUserId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/group/{_groupId}/assess/{_assessId}/assessanswer/subanswer/{_subAnswerId}", method = RequestMethod.PUT)
    @ApiOperation(value = "考生更新某个卷子某个题的元素项的某个答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<String> updateAssessAnswerSubAnser(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                      @ApiParam(value = "题所属的试卷分组ID") @PathVariable(value = "_groupId", required = true) String _groupId,
                                                                      @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                         @ApiParam(value = "待更新的子项的答案的ID") @PathVariable(value = "_subAnswerId", required = true) String _subAnswerId,
                                                                      @ApiParam(value = "题的答案，以题的模板为单位来封装，如果一个题有多个答案，则一个模板则会封装多个List元素") @RequestBody List<AssessTemplateReq> _groupSummaryReq){
        GeneralContentResult<String> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to update sub answer for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setGroupId(_groupId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);
            tempAnswerReq.setAnswerList(_groupSummaryReq);

            result = this.doingAssessService.updateAssessSubAnswer(tempUserId, _subAnswerId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/assessanswer/subanswer/{_subAnswerId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "考生删除某个卷子某个题的子元素项的某个答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult deleteAssessAnswerSubAnser(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                    @ApiParam(value = "某个元素项的某个答案的ID") @PathVariable(value = "_subAnswerId", required = true) String _subAnswerId){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("oing to delete sub answer [{}].", _subAnswerId);
            List<String> subIdList = new ArrayList<>();
            subIdList.add(_subAnswerId);
            result = this.doingAssessService.deleteAssessSubAnswer(tempUserId, _assessPaperId, subIdList);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/assessanswer/subanswers", method = RequestMethod.DELETE)
    @ApiOperation(value = "考生删除某个卷子某个题的子元素项的多个答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult deleteAssessAnswerSubAnserInBatch(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                           @ApiParam(value = "待删除的子项答案集") @RequestBody List<String> _subAnswerIdList){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("oing to delete sub answer [{}] in batch.", _subAnswerIdList);
            result = this.doingAssessService.deleteAssessSubAnswer(tempUserId, _assessPaperId, _subAnswerIdList);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/group/{_groupId}/assess/{_assessId}/assessanswer/answeritem", method = RequestMethod.POST)
    @ApiOperation(value = "考生增加某个卷子某个多答案题的答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<String>> addMultiAssessAnswerItems(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                      @ApiParam(value = "题所属的试卷分组ID") @PathVariable(value = "_groupId", required = true) String _groupId,
                                                                      @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                      @ApiParam(value = "题的答案，以题的模板为单位来封装，如果一个题有多个答案，则一个模板则会封装多个List元素") @RequestBody List<AssessTemplateReq> _groupSummaryReq){
        GeneralContentResult<List<String>> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to submit sub answer for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setGroupId(_groupId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);
            tempAnswerReq.setAnswerList(_groupSummaryReq);

            result = this.doingAssessService.addMultiAnswerAssessAnswer(tempUserId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/group/{_groupId}/assess/{_assessId}/assessanswer/answeritem/{_answerItemId}", method = RequestMethod.PUT)
    @ApiOperation(value = "考生更新某个卷子某个多答案题的答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<String> updateMultiAssessAnswerItems(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                        @ApiParam(value = "题所属的试卷分组ID") @PathVariable(value = "_groupId", required = true) String _groupId,
                                                                        @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                           @ApiParam(value = "要更改的答案ID") @PathVariable(value = "_answerItemId", required = true) String _answerItemId,
                                                                        @ApiParam(value = "题的答案，以题的模板为单位来封装，如果一个题有多个答案，则一个模板则会封装多个List元素") @RequestBody List<AssessTemplateReq> _groupSummaryReq){
        GeneralContentResult<String> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to update answer item for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setGroupId(_groupId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);
            tempAnswerReq.setAnswerList(_groupSummaryReq);

            result = this.doingAssessService.updateMultiAnswerAssessAnswer(tempUserId, _answerItemId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/group/{_groupId}/assess/{_assessId}/assessanswer", method = RequestMethod.PUT)
    @ApiOperation(value = "考生提交某个多答案题（提交的时候无须再提交内容）")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitMultiAssessAnswer(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                        @ApiParam(value = "题所属的试卷分组ID") @PathVariable(value = "_groupId", required = true) String _groupId,
                                                                        @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to submit answer for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setGroupId(_groupId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);

            result = this.doingAssessService.submitMultiAnswerAssessAnswer(tempUserId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/assessanswer/{_answerItemId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "考生删除某个卷子某个多答案题的某个答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult deleteMultiAnswersOneAssessAnswer(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                           @ApiParam(value = "某个元素项的某个答案的ID") @PathVariable(value = "_answerItemId", required = true) String _answerItemId){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("oing to delete answer Item [{}].", _answerItemId);
            List<String> subIdList = new ArrayList<>();
            subIdList.add(_answerItemId);
            result = this.doingAssessService.deleteMultiAnswerAssessAnswer(tempUserId, _assessPaperId, subIdList);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/assessanswers", method = RequestMethod.DELETE)
    @ApiOperation(value = "考生删除某个卷子某个多答案题的多个答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult deleteMultiAnswersAssessAnswers(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                         @ApiParam(value = "待删除的答案集") @RequestBody List<String> _answerItemIdList){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to delete sub answer [{}] in batch.", _answerItemIdList);
            result = this.doingAssessService.deleteMultiAnswerAssessAnswer(tempUserId, _assessPaperId, _answerItemIdList);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/{_assessId}/assessanswer", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个用户某个题的答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<SimpleAssessAnswerItem>> getAssessAnswerItemList(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                      @ApiParam(value = "题的ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                                                                      @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId){
        GeneralContentResult<List<SimpleAssessAnswerItem>> result = new GeneralContentResult<>();
        try {
            String tempUserId = _userId;
            if(_userId == null)
                tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to get current user's [{}]->[{}]'s answer.", _assessPaperId, _assessId);
            result = this.doingAssessService.getAssessAnswerItemList(tempUserId, _assessPaperId, _assessId);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assessanswerlist", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个用户某个试卷的分组答案的答题详情")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<SimpleAssessPaperAnswerItem> getAssessPaperAnswerItemList(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                                                                          @ApiParam(value = "做题人的ID, 为空则表示当前用户") @RequestParam (value = "_userId", required = false) String _userId){
        GeneralContentResult<SimpleAssessPaperAnswerItem> result = new GeneralContentResult<>();
        try {
            String tempUserId = _userId;
            if(_userId == null)
                tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to get [{}] user's [{}]'s answer detail info.", tempUserId, _assessPaperId);
            result = this.doingAssessService.getAssessPaperAnswerSumByUser(tempUserId, _assessPaperId);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }


    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/paperanswer", method = RequestMethod.PUT)
    @ApiOperation(value = "考生提交考卷")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitAssessPaperAnswer(@ApiParam(value = "试卷的ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            String tempOrgId = this.securityService.getCurrentUser().getDeptId();
            Byte tempTitle = this.securityService.getCurrentUser().getProfessionalTitle();
            log.info("[{}]Is going to submit AssessPaper [{}].", tempUserId, _assessPaperId);

            result = this.doingAssessService.submitAssessPaperAnswer(tempUserId, tempOrgId, tempTitle, _assessPaperId);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (YYException ex){
            log.info("The Exception Code is: {}", ex.getCode());
            result.setDetailDescription(ExceptionCode.EXCEPTION_MSG.get(ex.getCode()));
            result.setResultCode(ex.getCode());
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
