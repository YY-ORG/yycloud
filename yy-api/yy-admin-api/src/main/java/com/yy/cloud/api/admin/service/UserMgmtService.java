package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.sysbase.PasswordProfile;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;

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
    
    


    GeneralContentResult<String> createUser(UserProfile _userProfile);


    GeneralContentResult<UserDetailsItem> findUserById(String _userId);

    GeneralPagingResult<List<UserItem>> findUsers(Byte _status, Integer _page, Integer _size);

    GeneralContentResult<String> getCurrentEnterpriseId();

    public GeneralContentResult<String> getCurrentAdmEnterpriseId();

    GeneralResult modifyUser(String _userId, UserProfile _userProfile);

    GeneralResult deleteUser(String _userId);

    GeneralResult enableUser(String _userId);

    GeneralResult disableUser(String _userId);


    GeneralResult modifyPassword(PasswordProfile _passwordProfile);

    GeneralResult modifyAdmPassword(PasswordProfile _passwordProfile);

    GeneralContentResult<List<UserItem>> getMembersInOrganization(String _organizationId);

    GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(Integer _page, Integer _size);

    GeneralContentResult<UserDetailsItem> loadUserById(String _userId);

    GeneralPagingResult<List<UserDetailsItem>> findUsersByUserName(String _userName, Integer _page, Integer _size);

    GeneralContentResult<String> validateLoginName(String _loginName);

	GeneralResult resetPassword(String _userId);



    
}
