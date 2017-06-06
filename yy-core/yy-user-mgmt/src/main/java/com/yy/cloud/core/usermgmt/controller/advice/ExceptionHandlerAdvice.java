package com.yy.cloud.core.usermgmt.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;

@ControllerAdvice("com.yy.cloud.core.usermgmt.controller")
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResult handleException(Exception e) {
        log.error(e.getMessage());
        GeneralResult response = new GeneralResult();
        response.setResultCode(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION);
        response.setDetailDescription("unexpected exception occurred.");
        return response;
    }
}
