package com.yy.cloud.core.assess.controller;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.assess.AssessMenuItem;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;
import com.yy.cloud.common.data.dto.assess.AssessTemplateReq;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.assess.service.DoingAssessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/authsec/assesspaper/{_assessPaperId}/assess/{_assessId}/assessanswer", method = RequestMethod.POST)
    @ApiOperation(value = "考生提交某个卷子某个题的答案")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult submitAssessAnswer(@ApiParam(value = "Assess Paper ID") @PathVariable(value = "_assessPaperId", required = true) String _assessPaperId,
                                            @ApiParam(value = "Assess ID") @PathVariable(value = "_assessId", required = true) String _assessId,
                                            @ApiParam(value = "题的答案，以题的模板为单位来封装，如果一个题有多个答案，则一个模板则会封装多个List元素") @RequestBody List<AssessTemplateReq> _groupSummaryReq){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("[{}]Is going to submit answer for AssessPaper [{}] -> assess [{}]", tempUserId, _assessPaperId, _assessId);

            AssessAnswerReq tempAnswerReq = new AssessAnswerReq();
            tempAnswerReq.setAssessId(_assessId);
            tempAnswerReq.setAssessPaperId(_assessPaperId);

            result = this.doingAssessService.submitAssessAnswer(tempUserId, tempAnswerReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
