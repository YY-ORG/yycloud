package com.yy.cloud.api.admin.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

public interface UserMgmtService {


    /**
     * 审批中心-待审批列表，根据部门ID 获取用户,返回结果中，key 为userid，value 为用户名
     * @param departmentId
     * @return
     */
    public GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(String departmentId);

    /**
     * 审批中心-待审批列表，获取审批人列表，key 为userid，value 为用户名
     * @return
     */
    public GeneralContentResult<List<CommonKeyValue>> getApprovers(String departmentId);


    
    /**
     * 获取所有部门信息
     * @return
     */
    public GeneralContentResult<List<OrganizationItem>>   findAllorgnazation();
    
    
    /**
     * 创建账号
     * @param _userProfile
     * @return
     */
    GeneralContentResult<String> createAdmUser(UserProfile _userProfile);
    
  
    public GeneralContentResult<UserDetailsItem> findCurrentUser();

    
}
