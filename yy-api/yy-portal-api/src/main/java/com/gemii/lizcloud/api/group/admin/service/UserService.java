package com.gemii.lizcloud.api.group.admin.service;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.PasswordProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.UserProfile;
import com.gemii.lizcloud.common.data.otd.user.UserDetailsItem;
import com.gemii.lizcloud.common.data.otd.user.UserItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by guof on 2016/11/10.
 */
public interface UserService {

    GeneralContentResult<String> createUser(UserProfile _userProfile);

    public GeneralContentResult<String> createAdmUser(UserProfile _userProfile);

    GeneralContentResult<UserDetailsItem> findUserById(String _userId);

    GeneralPagingResult<List<UserItem>> findUsers(Byte _status, Integer _page, Integer _size);

    GeneralContentResult<String> getCurrentEnterpriseId();

    public GeneralContentResult<String> getCurrentAdmEnterpriseId();

    GeneralResult modifyUser(String _userId, UserProfile _userProfile);

    GeneralResult deleteUser(String _userId);

    GeneralResult enableUser(String _userId);

    GeneralResult disableUser(String _userId);

    GeneralContentResult<UserDetailsItem> findCurrentUser();

    GeneralResult modifyPassword(PasswordProfile _passwordProfile);

    GeneralResult modifyAdmPassword(PasswordProfile _passwordProfile);

    GeneralContentResult<List<UserItem>> getMembersInOrganization(String _organizationId);

    GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(Integer _page, Integer _size);

    GeneralContentResult<UserDetailsItem> loadUserById(String _userId);

    GeneralPagingResult<List<UserItem>> findUsersByUserName(String _userName, Integer _page, Integer _size);

    GeneralContentResult<String> validateLoginName(String _loginName);

}
