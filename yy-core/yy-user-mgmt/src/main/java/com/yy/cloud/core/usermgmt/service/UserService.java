package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;

public interface UserService {

    String createUser(UserProfile _userProfile);

    UserItem findUserById(String _userId);

    List<UserItem> listUsersByPage(PageInfo _pageInfo, Byte _status);

    List<UserItem> listUsersByUserName(PageInfo _pageInfo, String _userName);

    void modifyUser(UserProfile _userProfile);

    void updateUserStatus(String _userId, Byte _status);

    void modifyPassword(PasswordProfile _passwordProfile);

    List<UserItem> listUsersInOrganization(String _organizationId);

    List<UserItem> listNonOrganizationMembers(PageInfo _pageInfo);

    UserDetailsItem loadUserByLoginName(String _loginName);

    UserDetailsItem loadUserByUserId(String _userId);

    boolean isProviderUser();

    UserDetailsItem loadUserByLoginNameOrId(String loginNameOrId);

    GeneralContentResult<String> validateLoginName(String loginName);

    GeneralContentResult<FoxUserItem> validateUserType(String loginName);
}
