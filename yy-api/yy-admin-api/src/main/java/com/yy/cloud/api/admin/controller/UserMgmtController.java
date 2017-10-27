package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.api.admin.service.UserMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserMgmtController {


	@Autowired
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

    @ApiOperation(value = "获取当前用户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/user/currentuser")
    public GeneralContentResult<UserDetailsItem> getCurrentUserInfo(){
        return userMgmtService.findCurrentUser();
    }
    
    /**
     * 获取部门信息
     * @return
     */
   @RequestMapping(value = "/noauth/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
   @RequestMapping(value = "/noauth/user/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
   
   
   @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，编辑账号，本地")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralResult modifyUser(
           @PathVariable("user_id") String _userId,
           @RequestBody UserProfile _userProfile) {
       return userMgmtService.modifyUser(_userId, _userProfile);
   }

   @RequestMapping(value = "/authsec/adm/user/{user_id}/resetPassword", method = RequestMethod.PUT)
   @ApiOperation(value = "用户中心-账户管理，重置密码 MOCK")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralResult resetPassword(
           @PathVariable("user_id") String _userId) {
       GeneralResult result = userMgmtService.resetPassword(_userId);
       return result;
   }

   @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，查询单个账户")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralContentResult<UserDetailsItem> findUserById(
           @PathVariable("user_id") String _userId) {
       return userMgmtService.findUserById(_userId);
   }

   @RequestMapping(value = "/authsec/adm/users/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，获取所有账户")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralPagingResult<List<UserItem>> findUsers(
           @RequestParam(value = "status", required = false) Byte _status,
           @PathVariable("page") Integer _page,
           @PathVariable("size") Integer _size) {
       return userMgmtService.findUsers(_status, _page, _size);
   }

   @RequestMapping(value = "/authsec/adm/user/{user_id}/enable", method = RequestMethod.PUT)
   @ApiOperation(value = "用户中心-账号管理，启用")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralResult enableUser(
           @PathVariable("user_id") String _userId) {
       return userMgmtService.enableUser(_userId);
   }

   @RequestMapping(value = "/authsec/adm/user/{user_id}/disable", method = RequestMethod.PUT)
   @ApiOperation(value = "用户中心-账号管理，禁用")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralResult disableUser(
           @PathVariable("user_id") String _userId) {
       return userMgmtService.disableUser(_userId);
   }

   @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.DELETE)
   @ApiOperation(value = "用户中心-账号管理，删除")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralResult deleteUser(
           @PathVariable("user_id") String _userId) {
       return userMgmtService.deleteUser(_userId);
   }

   @RequestMapping(value = "/authsec/adm/user/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，查询当前账户")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralContentResult<UserDetailsItem> findCurrentUser() {
       return userMgmtService.findCurrentUser();
   }
   
   
   @RequestMapping(value = "/authsec/adm/users/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，获得属于指定机构下所有用户")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralContentResult<List<UserItem>> getMembersInOrganization(
           @PathVariable("organization_id") String _organizationId) {
       return userMgmtService.getMembersInOrganization(_organizationId);
   }
   
   
   @RequestMapping(value = "/authsec/adm/users/search/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiOperation(value = "用户中心-账户管理，通过用户名模糊查询账户")
   @ApiImplicitParams({
           @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
   })
   public GeneralPagingResult<List<UserItem>> findUsersByUserName(
           @RequestParam(value = "userName", required = false) String _userName,
           @PathVariable("page") Integer _page,
           @PathVariable("size") Integer _size) {
       return userMgmtService.findUsersByUserName(_userName, _page, _size);
   }


}
