/**
 * Project Name:fox-enterprisemgmt
 * File Name:UserMgmtClient.java
 * Package Name:com.hpe.foxcloud.composite.enterprisemgmt.clients
 * Date:Oct 5, 201611:25:55 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ClassName:UserMgmtClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Oct 5, 2016 11:25:55 AM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */

@FeignClient("usermgmt")
// @FeignClient(url = "http://localhost:9101", name = "usermgmt")
public interface UserMgmtClient {


	/**
	 * updateEnterpriseStatus:更新企业状态. <br/>
	 *
	 * @param _tenantId
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/tenant/{_tenantId}/status/{_status}")
	@ResponseBody
	GeneralResult updateEnterpriseStatus(@PathVariable("_tenantId") String _tenantId,
			@PathVariable("_status") Byte _status);





	/**
	 * updateEnterpriseAdminStatus:更新企业管理员状态. <br/>
	 *
	 * @param _adminId
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/tenant/admin/{_adminId}/status/{_status}")
	@ResponseBody
	GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatus(@PathVariable("_adminId") String _adminId,
			@PathVariable("_status") Byte _status);


	/**
	 * deleteEnterpriseAdmin:删除某个企业管理员. <br/>
	 *
	 * @param _adminId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/tenant/admin/{_adminId}")
	@ResponseBody
	GeneralPagingResult<GeneralContent> deleteEnterpriseAdmin(@PathVariable("_adminId") String _adminId);




	/**
	 * 审批中心-待审批列表，根据部门ID 获取用户,返回结果中，key 为userid，value 为用户名
	 * 
	 * @param departmentId
	 * @return
	 */
	@RequestMapping(value = "/authsec/approval/department/{departmentId}/users", method = RequestMethod.GET)
	GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(@PathVariable("departmentId") String departmentId);

	/**
	 * 审批中心-待审批列表，获取审批人列表，key 为userid，value 为用户名
	 * 
	 * @return
	 */
	@RequestMapping(value = "/authsec/approval/approvers/department/{departmentId}", method = RequestMethod.GET)
	GeneralContentResult<List<CommonKeyValue>> getApprovers(@PathVariable("departmentId") String departmentId);

	/**
	 * 获取当前用户所属机构下的所有企业
	 * 
	 * @return key 为企业id，value 为企业名称
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprise/currentuser")
	GeneralContentResult<List<CommonKeyValue>> getCurrentUserEnterprises();

	/**
	 * 获取当前用户所属企业下的所有部门
	 * 
	 * @return key 为部门id，value 为部门名称
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/department/currentuser")
	GeneralContentResult<List<CommonKeyValue>> getCurrentUserDepartments();

	/**
	 * 根据企业获取部门
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/organization/enterprise/{enterprise_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	GeneralContentResult<List<OrganizationItem>> getOrganizationsByEnterpriseId(
			@PathVariable("enterprise_id") String enterprise_id);


	@RequestMapping(value = "/authsec/user/{user_id}/password", method = RequestMethod.PUT)
	public GeneralResult resetPassword(@PathVariable("user_id") String _userId);
	
	
	
	/**
	 * 获取所有部门信息
	 * @return
	 */
  @RequestMapping(value = "/noauth/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-获取部门")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralContentResult<List<OrganizationItem>> findAllorgnazation();
  
  
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
  public GeneralContentResult<String> createAdmUser(@RequestBody UserProfile _userProfile);  

  
  
  
  @RequestMapping(value = "/authsec/user/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "用户中心-账户管理，查询当前账户")
  @ApiImplicitParams({
          @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
  })
  public GeneralContentResult<UserDetailsItem> findCurrentUser();
  
  
  @RequestMapping(value = "/authsec/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	GeneralPagingResult<List<UserItem>> findUsers(@RequestParam(value = "status", required = false) Byte _status,
			@RequestParam(value = "page") Integer _page, @RequestParam(value = "size") Integer _size);

	@RequestMapping(value = "/authsec/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	GeneralContentResult<UserDetailsItem> findUserById(@PathVariable("user_id") String _userId);

	
	@RequestMapping(value = "/authsec/users/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用户中心-账户管理，通过用户名模糊查询账户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ") })
	public GeneralPagingResult<List<UserDetailsItem>> findUsersByUserName(
			@RequestParam(value = "userName", required = false) String _userName,
			@RequestParam(value = "page") Integer _page, @RequestParam(value = "size") Integer _size);
	
	
	
	@RequestMapping(value = "/authsec/user/{user_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "用户中心-账户管理，编辑账号，本地")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")
    })
    public GeneralResult modifyUser(
            @PathVariable("user_id") String _userId,
            @RequestBody UserProfile _userProfile) ;
  
}
