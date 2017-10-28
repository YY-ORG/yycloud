package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.UserMgmtClient;
import com.yy.cloud.api.admin.service.UserMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.sysbase.PasswordProfile;
import com.yy.cloud.common.data.dto.sysbase.UserProfile;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.utils.PageUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMgmtServiceImpl implements UserMgmtService {

    @Autowired
    private UserMgmtClient userMgmtClient;

    @Override
    public GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(String departmentId) {
        GeneralContentResult<List<CommonKeyValue>> res= userMgmtClient.getUsersByDepartment(departmentId);
        log.debug("根据部门ID 获取部门用户返回结果: {}"+res);
        return res;
    }

    @Override
    public GeneralContentResult<List<CommonKeyValue>> getApprovers(String departmentId) {
        GeneralContentResult<List<CommonKeyValue>> result= userMgmtClient.getApprovers(departmentId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取审批人列表，返回结果 {}",result);
        return result;
    }

	@Override
	public GeneralContentResult<List<OrganizationItem>> findAllorgnazation() {
		  GeneralContentResult<List<OrganizationItem>> result= userMgmtClient.findAllorgnazation();
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取部门列表，返回结果 {}",result);
		return result;
	}

	@Override
	public GeneralContentResult<String> createAdmUser(UserProfile _userProfile) {
		GeneralContentResult<String> result= userMgmtClient.createAdmUser(_userProfile);
	        log.debug(CommonConstant.LOG_DEBUG_TAG+"创建账号，返回结果 {}",result);
			return result;
	}

	@Override
	public GeneralContentResult<UserDetailsItem> findCurrentUser() {
		// TODO Auto-generated method stub
		return userMgmtClient.findCurrentUser();
	}

	@Override
	public GeneralContentResult<String> createUser(UserProfile _userProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralContentResult<UserDetailsItem> findUserById(String _userId) {
		return userMgmtClient.findUserById(_userId);
	}

	@Override
	public GeneralPagingResult<List<UserItem>> findUsers(Byte _status, Integer _page, Integer _size) {
		 return userMgmtClient.findUsers(_status, PageUtils.getPageNo(_page), _size);		
	}

	@Override
	public GeneralContentResult<String> getCurrentEnterpriseId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralContentResult<String> getCurrentAdmEnterpriseId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult modifyUser(String _userId, UserProfile _userProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult deleteUser(String _userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult enableUser(String _userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult disableUser(String _userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult modifyPassword(PasswordProfile _passwordProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult modifyAdmPassword(PasswordProfile _passwordProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralContentResult<List<UserItem>> getMembersInOrganization(String _organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(Integer _page, Integer _size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralContentResult<UserDetailsItem> loadUserById(String _userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralPagingResult<List<UserItem>> findUsersByUserName(String _userName, Integer _page, Integer _size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralContentResult<String> validateLoginName(String _loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult resetPassword(String _userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
