package com.yy.cloud.core.filesys.controller.advice;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/8/18 10:43 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public GeneralContentResult<String> handleError1(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        GeneralContentResult<String> tempResult = new GeneralContentResult<>();
        log.error("Get Exception while upload file.", e);
        tempResult.setDetailDescription("Unexpected Error occured...");
        tempResult.setResultCode(ResultCode.FILE_EXCEED_SIZE_MAX);
        return tempResult;
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<?> handleMultipartException(HttpServletRequest request, Throwable ex) throws Throwable {
        Throwable cause = ex.getCause();
        if (cause instanceof IllegalStateException) {
            Throwable cause2 = cause.getCause();
            log.error("Get MultipartException:", cause2);

            if (cause2 instanceof FileUploadBase.SizeLimitExceededException) {

                GeneralContentResult<String> tempResult = new GeneralContentResult<>();
                tempResult.setDetailDescription("Unexpected Error occured...");
                tempResult.setResultCode(ResultCode.FILE_EXCEED_SIZE_MAX);

                return new ResponseEntity<>(tempResult, HttpStatus.OK);
            }
        }

        throw ex;
    }

//    @ExceptionHandler(MultipartException.class)
//    @ResponseBody
//    public GeneralContentResult<String> handleError2(MultipartException e, RedirectAttributes redirectAttributes) {
//        log.error("Get MultipartException:", e);
//        GeneralContentResult<String> tempResult = new GeneralContentResult<>();
//        tempResult.setDetailDescription("Unexpected Error occured...");
//        tempResult.setResultCode(ResultCode.FILE_EXCEED_SIZE_MAX);
//        return tempResult;
//    }
}
