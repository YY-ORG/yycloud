package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.api.admin.service.UserMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class UserMgmtController {


    private UserMgmtService userMgmtService;

    /**
     * 根据部门id 返回部门用户
     * @param departmentId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/approval/department/{departmentId}/users",method = RequestMethod.GET)
    @ApiOperation(value = "审批中心-待审批列表，根据部门ID 获取用户,返回结果中，key 为userid，value 为用户名（开发中）")
    public  GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(@PathVariable("departmentId") String departmentId){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"部门ID {}",departmentId);
       return userMgmtService.getUsersByDepartment(departmentId);
    }

    /**
     * 获取审批人列表
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/approval/department/{departmentId}/approvers",method = RequestMethod.GET)
    @ApiOperation(value = "审批中心-待审批列表，获取审批人列表，key 为userid，value 为用户名（开发中）")
    public  GeneralContentResult<List<CommonKeyValue>> getApprovers(@PathVariable("departmentId") String departmentId){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取审批人列表");
        return userMgmtService.getApprovers(departmentId);
    }


}
