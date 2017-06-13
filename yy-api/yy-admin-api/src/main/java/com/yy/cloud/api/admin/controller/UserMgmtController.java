package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.api.admin.service.UserMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.service.SecurityService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserMgmtController {


	@Autowired
    private UserMgmtService userMgmtService;

    @Autowired
    private SecurityService securityService;

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

    
    
    
    
    
    

    @ApiOperation(value = "获取当前用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/user/currentuser")
    public GeneralContentResult<UserDetailsItem> getCurrentUserInfo(){
        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        log.debug("当前用户的信息为 {}",userDetailsItem);
        GeneralContentResult<UserDetailsItem> current= new GeneralContentResult<>();
        current.setResultContent(userDetailsItem);
        current.setResultCode("100");
        return current;
    }

    
    
    /**
     * 获取部门信息
     * @return
     */
   @RequestMapping(value = "/authsec/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-获取部门")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralContentResult<List<OrganizationItem>> findAllorgnazation() {
       return userMgmtService.findAllorgnazation();
   }

   
   /**
    * 创建账号
    * @param _userProfile
    * @return
    */
   @RequestMapping(value = "/authsec/user/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，创建账号")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralContentResult<String> createAdmUser(
           @RequestBody UserProfile _userProfile) {
	   log.debug("The value of _userProfile is \' "+_userProfile+"\'");
       return  userMgmtService.createAdmUser(_userProfile);
   }

    // 费用中心 end

}
