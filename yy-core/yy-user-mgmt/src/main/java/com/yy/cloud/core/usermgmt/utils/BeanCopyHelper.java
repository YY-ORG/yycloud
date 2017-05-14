package com.yy.cloud.core.usermgmt.utils;

import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseGeneralItem;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.FoxTenant;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;

/**
 * Created by guof on 2016/10/8.
 */
public class BeanCopyHelper {

	public static FoxUser copyAdminProfile2FoxUser(EnterpriseAdminProfile adminProfile) {
		FoxUser foxUser = new FoxUser();
		foxUser.setUserName(adminProfile.getUserName());
		foxUser.setLoginName(adminProfile.getLoginName());
		foxUser.setPassword(adminProfile.getPassword());
		foxUser.setTenantId(adminProfile.getEnterpriseId());
		foxUser.setPhone(adminProfile.getContactPhone());
		if (adminProfile.getAuthMode() != null
				&& adminProfile.getAuthMode().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
			foxUser.setType(AdUserMgmtConstants.USER_TYPE_AD);
		} else {
			foxUser.setType(AdUserMgmtConstants.USER_TYPE_LOCAL);
		}
		foxUser.setDescription(adminProfile.getDescription());
		return foxUser;
	}

	public static EnterpriseAdminItem copyFoxUser2AdminItem(FoxUser foxUser) {
		EnterpriseAdminItem adminItem = new EnterpriseAdminItem();
		adminItem.setId(foxUser.getId());
		adminItem.setUserName(foxUser.getUserName());
		adminItem.setContactPhone(foxUser.getPhone());
		adminItem.setEnterpriseId(foxUser.getTenantId());
		adminItem.setLoginName(foxUser.getLoginName());
		if (foxUser.getType() != null && foxUser.getType().byteValue() == AdUserMgmtConstants.USER_TYPE_AD) {
			adminItem.setAuthMode(AdUserMgmtConstants.USER_TYPE_AD);
		} else {
			adminItem.setAuthMode(AdUserMgmtConstants.USER_TYPE_LOCAL);
		}
		adminItem.setStatus(foxUser.getStatus());
		adminItem.setDescription(foxUser.getDescription());
		return adminItem;
	}

	public static FoxTenant copyEnterpriseProfile2FoxTenant(EnterpriseProfile enterpriseProfile) {
		FoxTenant foxTenant = new FoxTenant();
		foxTenant.setName(enterpriseProfile.getName());
		foxTenant.setCode(enterpriseProfile.getCode());
		foxTenant.setContactor(enterpriseProfile.getUserName());
		// foxTenant.setPhone(enterpriseProfile.get);
		// foxTenant.setEmail(enterpriseProfile.getEmail());
		foxTenant.setDescription(enterpriseProfile.getDescription());
		// foxTenant.setCurrencyType(enterpriseProfile.getCurrencyType());
		foxTenant.setAuthmode(enterpriseProfile.getAuthenticationMode());
		foxTenant.setUrl(enterpriseProfile.getUrl());
		foxTenant.setSslEnable(enterpriseProfile.getSslEnable());
		return foxTenant;
	}

	public static EnterpriseGeneralItem copyFoxTenant2EnterpriseGeneralItem(FoxTenant foxTenant) {
		EnterpriseGeneralItem generalItem = new EnterpriseGeneralItem();
		generalItem.setId(foxTenant.getId());
		generalItem.setCode(foxTenant.getCode());
		generalItem.setName(foxTenant.getName());
		generalItem.setDescription(foxTenant.getDescription());
		generalItem.setStatus(foxTenant.getStatus());
		generalItem.setLoginName(foxTenant.getContactor());
		return generalItem;
	}
}
