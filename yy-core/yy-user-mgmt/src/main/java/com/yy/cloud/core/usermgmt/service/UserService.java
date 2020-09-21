package com.yy.cloud.core.usermgmt.service;

import java.util.List;
import java.util.Map;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.PasswordProfile;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.usermgmt.FoxUserItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;

public interface UserService {

    String createUser(UserProfile _userProfile);

    UserItem findUserById(String _userId);

    List<UserItem> listUsersByPage(PageInfo _pageInfo, Byte _status);

    List<UserDetailsItem> listUsersByUserName(PageInfo _pageInfo, String _userName, String _orgId);

    void modifyUser(UserProfile _userProfile);

    void updateUserStatus(String _userId, Byte _status);

    void modifyPassword(PasswordProfile _passwordProfile);

    List<UserDetailsItem> listUsersInOrganization(String _organizationId, PageInfo pageinfo);

    Map<String, UserDetailsItem> listUsersInOrganization(String _organizationId);

    List<UserItem> listNonOrganizationMembers(PageInfo _pageInfo);

    UserDetailsItem loadUserByLoginName(String _loginName);

    UserDetailsItem loadUserByUserId(String _userId);

    UserDetailsItem loadUserByLoginNameOrId(String loginNameOrId);

    GeneralContentResult<String> validateLoginName(String loginName);

    GeneralContentResult<FoxUserItem> validateUserType(String loginName);
    
    
    /**
     * 获取所有部门信息
     * @return
     */
    public GeneralContentResult<List<OrganizationItem>>   findAllorgnazation();
    
    
	public GeneralResult deleteUser(String id);

    
}
