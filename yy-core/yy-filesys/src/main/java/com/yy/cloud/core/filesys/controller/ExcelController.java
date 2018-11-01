package com.yy.cloud.core.filesys.controller;

import com.yy.cloud.common.constant.ExceptionCode;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.utils.MD5Utils;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.filesys.data.domain.YyFile;
import com.yy.cloud.core.filesys.service.ExcelFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/14/18 8:10 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
@Api("Excel文件相关API")
public class ExcelController {
    @Autowired
    private ExcelFileService excelFileService;
    /**
     * 上传Excel接口
     * @param _file
     * @return
     */
    @RequestMapping(value = "/authsec/excel", method = RequestMethod.POST)
    @ApiOperation(value = "上传Excel")
    @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true,
            value = "Token", defaultValue = "bearer ")
    @ResponseBody
    public GeneralResult handleFileUpload(@ApiParam(value = "待上传Excel") @RequestParam("file") MultipartFile _file,
                                          @ApiParam(value = "上传Excel的类型：0为来源期刊，1为核心期刊：") @RequestParam (value = "_type", required = false) Byte _type) {
        GeneralResult result = new GeneralResult();
        try {
            log.debug("Going to upload Excel [{}] this time.", _file.getOriginalFilename());
            if(_type == null || _type == 1){
                result = this.excelFileService.importSourceJounalExcelFile(_file);
            } else
                result = this.excelFileService.importKeyJounalExcelFile(_file);

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
