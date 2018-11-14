package com.yy.cloud.core.finance.controller;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.finance.SubjectProfileReq;
import com.yy.cloud.common.data.otd.finance.SubjectItemRes;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.finance.service.SubjectService;
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
    private SubjectService subjectService;

    @RequestMapping(value = "/authsec/finance/subject", method = RequestMethod.POST)
    @ApiOperation(value = "创建财务科目")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralContentResult<List<SubjectItemRes>> createAssessCategory(@ApiParam(value = "考题分组属性") @RequestBody List<SubjectProfileReq> _profileReq) {
        GeneralContentResult<List<SubjectItemRes>> result = new GeneralContentResult<>();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();
            log.info("Going to create Subject [{}].", tempUserId);
            result = this.subjectService.createSubjects(tempUserId, _profileReq);
            result.setResultCode(ResultCode.OPERATION_SUCCESS);
        } catch (Exception e) {
            log.error("Unexpected Error occured", e);
            result.setDetailDescription("Unexpected Error occured...");
            result.setResultCode(ResultCode.ASSESS_GET_FAILED);
        }
        return result;
    }
}
