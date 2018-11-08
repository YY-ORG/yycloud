package com.yy.cloud.core.finance.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.file.SimpleSourceJournalItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.finance.service.DirectoryService;
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
 * Date:     5/14/18 8:52 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@RestController
@Api("名录管理相关API")
public class DirectoryController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private DirectoryService directoryService;

    @RequestMapping(value = "/authsec/assessdirectory", method = RequestMethod.POST)
    @ApiOperation(value = "批量创建考核的名录")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    public GeneralResult uploadAssessDirectory(@ApiParam(value = "名录详情") @RequestBody List<SimpleSourceJournalItem> _req){
        GeneralResult result = new GeneralResult();
        try {
            String tempUserId = this.securityService.getCurrentUser().getUserId();

            log.info("Going to create the assess directory [{}] by [{}] .", _req.size(), tempUserId);
            result = this.directoryService.createAssessDirectoryByBatch(_req);
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
