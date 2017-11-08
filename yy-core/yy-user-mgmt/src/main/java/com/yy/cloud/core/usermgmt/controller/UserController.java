package com.yy.cloud.core.usermgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.PasswordProfile;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.usermgmt.FoxUserItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.exception.PasswordNotMatchException;
import com.yy.cloud.core.usermgmt.exception.UserExistException;
import com.yy.cloud.core.usermgmt.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

	

    /**
     * 创建账号
     * @param _userProfile
     * @return
     */
    @RequestMapping(value = "/noauth/user/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，ADM创建账号，本地")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> createAdmUser(
            @RequestBody UserProfile _userProfile) {
        GeneralContentResult<String> result = new GeneralContentResult<>();
        String userId = null;
        try {
            userId = userService.createUser(_userProfile);
        } catch (UserExistException e) {
            result.setResultCode(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION);
            result.setDetailDescription(String.format("用户名 %s 已存在.", _userProfile.getLoginName()));
            return result;
        }
        result.setResultContent(userId);
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        return result;
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
    	
        return userService.findAllorgnazation();
    }


    
    
    
    

    @RequestMapping(value = "/authsec/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，查询单个账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<UserItem> findUserById(
            @PathVariable("user_id") String _userId) {
        GeneralContentResult<UserItem> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        UserItem userItem = userService.findUserById(_userId);
        result.setResultContent(userItem);
        return result;
    }

    @RequestMapping(value = "/authsec/user/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，查询当前账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<UserDetailsItem> findCurrentUser() {
        GeneralContentResult<UserDetailsItem> result = new GeneralContentResult<>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前登录用户：{}" + userDetailsItem);
        result.setResultContent(userDetailsItem);
        return result;
    }

    

    @RequestMapping(value = "/authsec/user/password/modify", method = RequestMethod.PUT)
    @ApiOperation(value = "用户中心-账户管理，修改当前密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult modifyPassword(
            @RequestBody PasswordProfile _passwordProfile) {
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        String userId = securityService.getCurrentUser().getUserId();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前登录用户ID：{}" + userId);
        _passwordProfile.setId(userId);

        try {
            userService.modifyPassword(_passwordProfile);
        } catch (PasswordNotMatchException e) {
            result.setResultCode(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION);
            result.setDetailDescription("password not match.");
            return result;
        }

        return result;
    }

   

    
    
    /**
     * 获取
     * @param _status
     * @param _page
     * @param _size
     * @return
     */
    @RequestMapping(value = "/authsec/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，获取所有账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserItem>> findUsers(
            @RequestParam(value = "status", required = false) Byte _status,
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size) {
        GeneralPagingResult<List<UserItem>> result = new GeneralPagingResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_page);
        pageInfo.setPageSize(_size);

        List<UserItem> userItems = userService.listUsersByPage(pageInfo, _status);

        result.setResultContent(userItems);
        result.setPageInfo(pageInfo);
        return result;
    }

    @RequestMapping(value = "/authsec/users/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，通过用户名模糊查询账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserDetailsItem>> findUsersByUserName(
            @RequestParam(value = "userName", required = false) String _userName,
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size) {
        GeneralPagingResult<List<UserDetailsItem>> result = new GeneralPagingResult<List<UserDetailsItem>>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_page);
        pageInfo.setPageSize(_size);

        List<UserDetailsItem> userItems = userService.listUsersByUserName(pageInfo, _userName);

        result.setResultContent(userItems);
        result.setPageInfo(pageInfo);
        return result;
    }

    @RequestMapping(value = "/authsec/user/{user_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，编辑账号，本地")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult modifyUser(
            @PathVariable("user_id") String _userId,
            @RequestBody UserProfile _userProfile) {
    	  log.debug("The method of UserController.modifyUser is begin");
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        _userProfile.setId(_userId);
        
        userService.modifyUser(_userProfile);
        return result;
    }

    @RequestMapping(value = "/authsec/user/{user_id}/status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult updateStatus(
            @PathVariable("user_id") String _userId,
            @RequestParam("status") Byte _status) {
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        userService.updateUserStatus(_userId, _status);
        return result;
    }

    @RequestMapping(value = "/authsec/users/organization/{organization_id}/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，获得属于指定机构下所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserDetailsItem>> getMembersInOrganization(
            @PathVariable("organization_id") String _organizationId,   @PathVariable(value = "page") Integer _page,
            @PathVariable(value = "size") Integer _size) {
    	GeneralPagingResult<List<UserDetailsItem>> result = new GeneralPagingResult<List<UserDetailsItem>>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_page);
        pageInfo.setPageSize(_size);
        List<UserDetailsItem> userItems = userService.listUsersInOrganization(_organizationId,pageInfo );
        result.setResultContent(userItems);
        result.setPageInfo(pageInfo);
        return result;
    }

    @RequestMapping(value = "/authsec/users/nonorganization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，获取所有无所属机构账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size) {
        GeneralPagingResult<List<UserItem>> result = new GeneralPagingResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(_page);
        pageInfo.setPageSize(_size);

        List<UserItem> userItems = userService.listNonOrganizationMembers(pageInfo);

        result.setResultContent(userItems);
        result.setPageInfo(pageInfo);
        return result;
    }

    @RequestMapping(value = "/authsec/users/loadByLoginName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<UserDetailsItem> loadUserByLoginName(
            @RequestParam("login_name") String _loginName) {
        GeneralContentResult<UserDetailsItem> result = new GeneralContentResult<UserDetailsItem>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        UserDetailsItem userDetailsItem = null;
        try {
            userDetailsItem = userService.loadUserByLoginName(_loginName);
            result.setResultContent(userDetailsItem);
        } catch (Exception e) {
            result.setResultCode(ResultCode.USERMGMT_USER_NOT_FOUND);
        }

        return result;
    }

    @RequestMapping(value = "/authsec/users/loadById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<UserDetailsItem> loadUserById(
            @RequestParam("user_id") String _userId) {
        GeneralContentResult<UserDetailsItem> result = new GeneralContentResult<UserDetailsItem>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        UserDetailsItem userDetailsItem = userService.loadUserByUserId(_userId);
        result.setResultContent(userDetailsItem);
        return result;
    }

    /**
     * 根据loginName 或者 userId获取用户信息
     * @param _loginNameOrId
     * @return
     */
    @RequestMapping(value = "/noauth/user/loginnameorid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GeneralContentResult<UserDetailsItem> loadUserByLoginNameOrId(
            @RequestParam("_loginNameOrId") String _loginNameOrId) {
        GeneralContentResult<UserDetailsItem> result = new GeneralContentResult<UserDetailsItem>();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        UserDetailsItem userDetailsItem = userService.loadUserByLoginNameOrId(_loginNameOrId);
        result.setResultContent(userDetailsItem);
        return result;
    }

    /**
     * 验证本地用户登录名是否存在
     * @param _loginName
     * @return
     */
    @RequestMapping(value = "/authsec/user/{_loginName}/validation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，验证帐号唯一性")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> validateLoginName(
            @PathVariable("_loginName") String _loginName) {
        GeneralContentResult<String> result = userService.validateLoginName(_loginName);
        return result;
    }

    @RequestMapping(value="/noauth/user/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="校验用户类型，0：前台；1：后台; 2:登录名不存在")
    public GeneralContentResult<FoxUserItem> validateUserType(@RequestParam(value = "loginName") String loginName){
        return userService.validateUserType(loginName);
    }
    
    
    @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "用户中心-账号管理，删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult deleteUser(
            @PathVariable("user_id") String _userId) {
        return userService.deleteUser(_userId);
    }
    
}
