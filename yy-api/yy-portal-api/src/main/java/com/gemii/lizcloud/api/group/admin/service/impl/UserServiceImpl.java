package com.gemii.lizcloud.api.group.admin.service.impl;

import com.gemii.lizcloud.api.group.admin.clients.UserClient;
import com.gemii.lizcloud.api.group.admin.service.UserService;
import com.gemii.lizcloud.common.constant.CommonConstant;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.PasswordProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.UserProfile;
import com.gemii.lizcloud.common.data.otd.user.UserDetailsItem;
import com.gemii.lizcloud.common.data.otd.user.UserItem;
import com.gemii.lizcloud.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;

    @Override
    public GeneralContentResult<String> createUser(UserProfile _userProfile) {
        return userClient.createUser(_userProfile);
    }

    /**
     * --WXDOK
     * @param _userProfile
     * @return
     */
    @Override
    public GeneralContentResult<String> createAdmUser(UserProfile _userProfile) {
        return userClient.createAdmUser(_userProfile);
    }

    @Override
    public GeneralContentResult<String> getCurrentEnterpriseId() {
        return userClient.getCurrentEnterpriseId();
    }

    @Override
    public GeneralContentResult<String> getCurrentAdmEnterpriseId() {
        return userClient.getCurrentAdmEnterpriseId();
    }

    @Override
    public GeneralContentResult<UserDetailsItem> findUserById(String _userId) {
        return userClient.loadUserById(_userId);
    }

    @Override
    public GeneralPagingResult<List<UserItem>> findUsers(Byte _status, Integer _page, Integer _size) {
        return userClient.findUsers(_status, PageUtils.getPageNo(_page), _size);
    }

    @Override
    public GeneralResult modifyUser(String _userId, UserProfile _userProfile) {
        return userClient.modifyUser(_userId, _userProfile);
    }

    @Override
    public GeneralResult deleteUser(String _userId) {
        return userClient.updateStatus(_userId, CommonConstant.DIC_GLOBAL_STATUS_DELETED);
    }

    @Override
    public GeneralResult enableUser(String _userId) {
        return userClient.updateStatus(_userId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
    }

    @Override
    public GeneralResult disableUser(String _userId) {
        return userClient.updateStatus(_userId, CommonConstant.DIC_GLOBAL_STATUS_CANCELLED);
    }

    @Override
    public GeneralContentResult<UserDetailsItem> findCurrentUser() {
        return userClient.findCurrentUser();
    }

    @Override
    public GeneralResult modifyPassword(PasswordProfile _passwordProfile) {
        return userClient.modifyPassword(_passwordProfile);
    }

    @Override
    public GeneralResult modifyAdmPassword(PasswordProfile _passwordProfile) {
        return userClient.modifyAdmPassword(_passwordProfile);
    }

    @Override
    public GeneralContentResult<List<UserItem>> getMembersInOrganization(String _organizationId) {
        return userClient.getMembersInOrganization(_organizationId);
    }

    @Override
    public GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(Integer _page, Integer _size) {
        return userClient.getNonOrganizationMembers(PageUtils.getPageNo(_page), _size);
    }

    @Override
    public GeneralContentResult<UserDetailsItem> loadUserById(String _userId) {
        return userClient.loadUserById(_userId);
    }

    @Override
    public GeneralPagingResult<List<UserItem>> findUsersByUserName(String _userName, Integer _page, Integer _size) {
        return userClient.findUsersByUserName(_userName, PageUtils.getPageNo(_page), _size);
    }

    @Override
    public GeneralContentResult<String> validateLoginName(String _loginName) {
        return userClient.validateLoginName(_loginName);
    }
}
