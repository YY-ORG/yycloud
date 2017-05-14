/**
 * Project Name:liz-sysbase
 * File Name:SysBaseServiceImpl.java
 * Package Name:com.yy.cloud.core.sysbase.service.impl
 * Date:Jul 12, 20165:15:03 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.core.usermgmt.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.*;
import com.yy.cloud.core.usermgmt.data.repositories.*;
import com.yy.cloud.core.usermgmt.exception.UserExistException;
import com.yy.cloud.core.usermgmt.service.EnterpriseAdminService;
import com.yy.cloud.core.usermgmt.utils.BeanCopyHelper;
import com.yy.cloud.core.usermgmt.utils.ObjectConverter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yy.cloud.common.constant.CommonConstant.DIC_GLOBAL_STATUS_INITIAL;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:SysBaseServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Jul 12, 2016 5:15:03 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class EnterpriseAdminServiceImpl implements EnterpriseAdminService {

	private static final long serialVersionUID = 2291356715967954332L;

	@Autowired
	private FoxUserRepository foxUserRepository;

	@Autowired
	private ExtLdapUserRepository extLdapUserRepository;

	@Autowired
	private FoxOrganizationRepository foxOrganizationRepository;

	@Autowired
	private FoxUserOrganizationRepository foxUserOrganizationRepository;

	@Autowired
	private FoxRoleRepository foxRoleRepository;

	@Autowired
	private FoxUserRoleRepository foxUserRoleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private FoxTenantRepository foxTenantRepository;

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public FoxUser createUser(EnterpriseAdminProfile adminProfile) {
		log.info(CommonConstant.LOG_DEBUG_TAG + "创建企业管理员,入参：{}" + adminProfile);

		// 增加唯一性验证: 登录名，类型(本地/AD), 企业ID三者联合唯一
//		foxUserRepository.findByLoginNameAndTypeAndTenantId(adminProfile.getLoginName(),
//				AdUserMgmtConstants.USER_TYPE_LOCAL, adminProfile.getEnterpriseId()).ifPresent(foxUser -> {
//					log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + adminProfile.getLoginName());
//					throw new UserExistException();
//				});

//        foxUserRepository.findByLoginNameAndType(adminProfile.getLoginName(), AdUserMgmtConstants.USER_TYPE_LOCAL).ifPresent(foxUser -> {
//            log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + adminProfile.getLoginName());
//            throw new UserExistException();
//        });

        // 只校验本地
        FoxUser foxUserExist = foxUserRepository.findByLoginNameAndType(adminProfile.getLoginName(), AdUserMgmtConstants.USER_TYPE_LOCAL);
        if(null != foxUserExist){
            log.info(CommonConstant.LOG_DEBUG_TAG + "该用户名已存在：" + adminProfile.getLoginName());
            throw new UserExistException();
        }

        FoxOrganization foxOrganization = foxOrganizationRepository.findByTenantIdAndStatusAndCode(
				adminProfile.getEnterpriseId(), Byte.valueOf("1"),
				CommonConstant.DEFAULT_ADMIN_DEPARTMENT_OF_ENTERPRISE_CODE);
		FoxUser foxUser = BeanCopyHelper.copyAdminProfile2FoxUser(adminProfile);
		String password = encoder.encode("12345");
		foxUser.setPassword(password);
		FoxRole foxRole = foxRoleRepository.findOneByRoleName(CommonConstant.ROLE_ENTERPRISE_ADMIN);
		foxUser.setStatus(DIC_GLOBAL_STATUS_INITIAL);
		foxUser = foxUserRepository.save(foxUser);
		FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
		if (foxUser != null) {
			if (foxOrganization != null) {
				foxUserOrganization.setOrganizationId(foxOrganization.getId());
				foxUserOrganization.setUserId(foxUser.getId());
				foxUserOrganizationRepository.save(foxUserOrganization);
			} else {
				log.error(CommonConstant.LOG_DEBUG_TAG + "未找到{} 企业的管理员部门", adminProfile.getEnterpriseId());
			}
			if (foxRole != null) {
				FoxUserRole foxUserRole = new FoxUserRole();
				foxUserRole.setStatus(Byte.valueOf("1"));
				foxUserRole.setUserId(foxUser.getId());
				foxUserRole.setRoleId(foxRole.getId());
				foxUserRoleRepository.save(foxUserRole);
			} else {
				log.error(CommonConstant.LOG_DEBUG_TAG + "未找到企业管理员角色");
			}
			// 根据企业类型设置管理员的认证类型
			// FoxTenant foxTenant =
			// foxTenantRepository.getOne(adminProfile.getEnterpriseId());
			if (null != foxUser.getType() && AdUserMgmtConstants.USER_TYPE_AD == foxUser.getType().byteValue()) {
				ExtLdapUser adUser = ObjectConverter.enterpriseAdminProfile2ExtLdapUser(adminProfile);
				adUser.setUserId(foxUser.getId());
				adUser.setStatus(AdUserMgmtConstants.STATUS_GLOBAL_ENABLE);
				extLdapUserRepository.save(adUser);
			}
		}
		log.info(CommonConstant.LOG_DEBUG_TAG + "创建企业管理员成功：{}", foxUser);
		return foxUser;
	}

	@Override
	public void updateUser(EnterpriseAdminProfile adminProfile) {
		FoxUser newInfo = BeanCopyHelper.copyAdminProfile2FoxUser(adminProfile);
		FoxUser existingInfo = foxUserRepository.findOne(adminProfile.getId());
		Byte status = existingInfo.getStatus();
		Byte type = existingInfo.getType();
		BeanUtils.copyProperties(newInfo, existingInfo);
		existingInfo.setId(adminProfile.getId());
		existingInfo.setStatus(status);
		existingInfo.setType(type);
		foxUserRepository.saveAndFlush(existingInfo);
	}

	@Override
	public FoxUser findUserById(String id) {
		return foxUserRepository.findOne(id);
	}

	@Override
	public Page<FoxUser> listAllUsers(Integer page, Integer size) {
		PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "createDate");
		return foxUserRepository.findAll(pageRequest);
	}

	@Override
	public Page<FoxUser> listUsersByTenantId(Integer page, Integer size, String tenantId) {
		PageRequest pageRequest = new PageRequest(page, size, Sort.Direction.DESC, "createDate");
		return foxUserRepository.findByTenantId(pageRequest, tenantId);
	}

	@Override
	public void deleteUser(String id) {
		foxUserRepository.delete(id);
	}

	@Override
	public void deleteUsersBatch(List<EnterpriseAdminProfile> adminProfiles) {
		adminProfiles.forEach(adminProfile -> {
			FoxUser foxUser = foxUserRepository.findOne(adminProfile.getId());
			if (foxUser != null) {
				foxUserRepository.delete(foxUser);
			}
		});
	}

	@Override
	public void updateUserStatus(String id, Byte status) {
		FoxUser foxUser = foxUserRepository.findOne(id);
		foxUser.setStatus(status);
		foxUserRepository.saveAndFlush(foxUser);
	}

	@Override
	public void updateUsersStatusBatch(List<EnterpriseAdminProfile> adminProfiles, Byte status) {
		adminProfiles.forEach(adminProfile -> {
			FoxUser foxUser = foxUserRepository.findOne(adminProfile.getId());
			if (foxUser != null) {
				foxUser.setStatus(status);
				foxUserRepository.saveAndFlush(foxUser);
			}
		});
	}

	@Override
	public GeneralPagingResult<EnterpriseAdminItem> findAdminByTenantId(String tenantId) {
		List<String> roleNames = new ArrayList<String>();
		roleNames.add(CommonConstant.ROLE_ENTERPRISE_ADMIN); // 值查询角色为管理员的用户
		log.debug(CommonConstant.LOG_DEBUG_TAG + "查询企业ID：{}下，所有该：{}角色成员", tenantId, roleNames);
		GeneralPagingResult<EnterpriseAdminItem> generalPagingResult = new GeneralPagingResult<EnterpriseAdminItem>();
		generalPagingResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		List<FoxUser> foxUsers = foxUserRepository.findMppUserByRoleList(tenantId, roleNames);
		// 一个企业下只有一个管理员
		if (CollectionUtils.isNotEmpty(foxUsers)) {
			FoxUser foxUser = foxUsers.get(0);
			// EnterpriseAdminItem enterpriseAdminItem =
			// modelMapper.map(foxUser, EnterpriseAdminItem.class);
			EnterpriseAdminItem enterpriseAdminItem = BeanCopyHelper.copyFoxUser2AdminItem(foxUser);
			generalPagingResult.setResultContent(enterpriseAdminItem);
		} else {
			log.warn(CommonConstant.LOG_DEBUG_TAG + "该企业下未查询到管理员：{}", tenantId);
			generalPagingResult.setResultContent(null);
		}

		log.debug(CommonConstant.LOG_DEBUG_TAG + "查询企业ID：{}下，管理员结果：{}", tenantId, generalPagingResult);
		return generalPagingResult;
	}

	// @Override
	// public List<FoxMenu> getMenuList() throws RuntimeException {
	//
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public CurrentUser getUserDetailInfo(String _userName) throws
	// RuntimeException {
	// log.debug("Going to retrieve [{}]'s detail info...", _userName);
	//
	// FoxUser acmeUser =
	// userRepository.findOneByLoginName(_userName).orElseThrow(
	// () -> new NoRecordFoundException("User was not found in the database."));
	// log.info("Loaded user {}.", acmeUser.getUserName());
	//
	// log.info("Going to retrieve the authorities of the user {}.",
	// acmeUser.getUserName());
	//
	// Collection<GrantedAuthority> tempBAuth = new
	// ArrayList<GrantedAuthority>();
	//// tempBAuth.addAll(acmeUser.getFoxUserRoles());
	// CurrentUser userDetails = new CurrentUser(acmeUser.getUserName(),
	// acmeUser.getPassword(),
	// acmeUser.getStatus() == SecurityConstant.SEC_USER_STATUS_ACTIVE,
	// tempBAuth);
	//
	// userDetails.setId(acmeUser.getId());
	// //userDetails.setActivated(acmeUser.isActivated());
	// // acmeUserDetails.setActivationKey(acmeUser.get);
	// userDetails.setCreateBy(acmeUser.getCreateBy());
	// userDetails.setCreateDate(acmeUser.getCreateDate());
	// userDetails.setEmail(acmeUser.getEmail());
	// userDetails.setId(acmeUser.getId());
	// userDetails.setMobile(acmeUser.getMobile());
	// userDetails.setAddress(acmeUser.getAddress());
	// userDetails.setSecQue(acmeUser.getSecQue());
	// userDetails.setSecAnswer(acmeUser.getSecAnswer());
	// userDetails.setStatus(acmeUser.getStatus());
	// userDetails.setTitle(acmeUser.getTitle());
	// // acmeUserDetails.setUserName(acmeUser.getUserName());
	//
	// // AcmeUserDetails acmeUserDetails = new AcmeUserDetails(acmeUser);
	// return userDetails;
	// }

}
