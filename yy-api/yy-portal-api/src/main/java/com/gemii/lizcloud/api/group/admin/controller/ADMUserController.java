package com.gemii.lizcloud.api.group.admin.controller;

import com.gemii.lizcloud.api.group.admin.service.UserService;
import com.gemii.lizcloud.common.constant.ResultCode;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.PageInfo;
import com.gemii.lizcloud.common.data.dto.accountcenter.PasswordProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.UserProfile;
import com.gemii.lizcloud.common.data.otd.role.RoleItem;
import com.gemii.lizcloud.common.data.otd.user.UserDetailsItem;
import com.gemii.lizcloud.common.data.otd.user.UserItem;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ADMUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authsec/adm/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，创建账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> createUser(@RequestBody UserProfile _userProfile) {
        return userService.createAdmUser(_userProfile);
    }

    @RequestMapping(value = "/authsec/adm/currentEnterpriseId", method = RequestMethod.GET)
    @ApiOperation(value = "用户中心-账户管理，获得当前用户企业ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> getCurrentEnterpriseId() {
        return userService.getCurrentAdmEnterpriseId();
    }

    @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，编辑账号，本地")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult modifyUser(
            @PathVariable("user_id") String _userId,
            @RequestBody UserProfile _userProfile) {
        return userService.modifyUser(_userId, _userProfile);
    }

    @RequestMapping(value = "/authsec/adm/user/{user_id}/resetPassword", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-账户管理，重置密码 MOCK")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult resetPassword(
            @PathVariable("user_id") String _userId) {
        GeneralResult result = new GeneralResult();
        result.setResultCode(ResultCode.OPERATION_SUCCESS);
        return result;
    }

    @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，查询单个账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<UserDetailsItem> findUserById(
            @PathVariable("user_id") String _userId) {
        return userService.findUserById(_userId);
    }

    @RequestMapping(value = "/authsec/adm/users/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，获取所有账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserItem>> findUsers(
            @RequestParam(value = "status", required = false) Byte _status,
            @PathVariable("page") Integer _page,
            @PathVariable("size") Integer _size) {
        return userService.findUsers(_status, _page, _size);
    }

    @RequestMapping(value = "/authsec/adm/user/{user_id}/enable", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-账号管理，启用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult enableUser(
            @PathVariable("user_id") String _userId) {
        return userService.enableUser(_userId);
    }

    @RequestMapping(value = "/authsec/adm/user/{user_id}/disable", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-账号管理，禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult disableUser(
            @PathVariable("user_id") String _userId) {
        return userService.disableUser(_userId);
    }

    @RequestMapping(value = "/authsec/adm/user/{user_id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "ADM用户中心-账号管理，删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult deleteUser(
            @PathVariable("user_id") String _userId) {
        return userService.deleteUser(_userId);
    }

    @RequestMapping(value = "/authsec/adm/user/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，查询当前账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<UserDetailsItem> findCurrentUser() {
        return userService.findCurrentUser();
    }

    /**
     * 等学进完成 --WXDOK
     * @param _passwordProfile
     * @return
     */
    @RequestMapping(value = "/authsec/adm/user/password/modify", method = RequestMethod.PUT)
    @ApiOperation(value = "ADM用户中心-账户管理，修改当前密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult modifyPassword(
            @RequestBody PasswordProfile _passwordProfile) {
        //return userService.modifyPassword(_passwordProfile);
        return userService.modifyAdmPassword(_passwordProfile);
    }

    @RequestMapping(value = "/authsec/adm/users/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，获得属于指定机构下所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<UserItem>> getMembersInOrganization(
            @PathVariable("organization_id") String _organizationId) {
        return userService.getMembersInOrganization(_organizationId);
    }

    @RequestMapping(value = "/authsec/adm/users/nonorganization/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，获得所有无机构所属用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(
            @PathVariable("page") Integer _page,
            @PathVariable("size") Integer _size) {
        return userService.getNonOrganizationMembers(_page, _size);
    }

    @RequestMapping(value = "/authsec/adm/users/search/page/{page}/size/{size}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "ADM用户中心-账户管理，通过用户名模糊查询账户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralPagingResult<List<UserItem>> findUsersByUserName(
            @RequestParam(value = "userName", required = false) String _userName,
            @PathVariable("page") Integer _page,
            @PathVariable("size") Integer _size) {
        return userService.findUsersByUserName(_userName, _page, _size);
    }

    /**
     * 验证ADM本地用户登录名是否存在
     * @param _loginName
     * @return
     */
    @RequestMapping(value = "/authsec/adm/user/{_loginName}/validation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，验证ADM本地帐号唯一性")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<String> validateLoginName(
            @PathVariable("_loginName") String _loginName) {
        GeneralContentResult<String> result = userService.validateLoginName(_loginName);
        return result;
    }
}
