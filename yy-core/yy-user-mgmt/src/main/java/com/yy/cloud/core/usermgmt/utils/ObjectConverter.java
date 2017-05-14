package com.yy.cloud.core.usermgmt.utils;

import org.springframework.data.domain.Page;

import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.accountcenter.AdUserProfile;
import com.yy.cloud.common.data.dto.accountcenter.LdapSourceProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapSource;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;
import com.yy.cloud.core.usermgmt.ldap.domain.Person;

public class ObjectConverter {
	public static AdUserProfile ldapPerson2AdUserProfile(Person person) {
		AdUserProfile profile = new AdUserProfile();
		profile.setEmail(person.getEmail());
		profile.setLoginName(person.getLogin());
		profile.setPhone(person.getPhone());
		profile.setLastName(person.getLastName());
		profile.setUserName(person.getFullName());
		profile.setDescription(person.getDescription());
		profile.setType(person.getType());
		return profile;
	}

	public static ExtLdapUser adUserProfile2ExtLdapUser(AdUserProfile profile) {
		ExtLdapUser user = new ExtLdapUser();
		user.setLogin(profile.getLoginName());
		user.setUserId(profile.getAdUserId());
		user.setLdapSourceId(profile.getLdapId());
		return user;
	}
	
	public static ExtLdapUser enterpriseAdminProfile2ExtLdapUser(EnterpriseAdminProfile profile) {
		ExtLdapUser user = new ExtLdapUser();
		user.setLogin(profile.getLoginName());
		user.setUserId(profile.getAdUserId());
		user.setLdapSourceId(profile.getLdapId());
		return user;
	}

	public static LdapSourceProfile extLdapSource2LdapSourceProfile(ExtLdapSource ldap) {
		LdapSourceProfile profile = new LdapSourceProfile();
		profile.setId(ldap.getId());
		profile.setDesc(ldap.getDescription());
		profile.setEnterpriseId(ldap.getTenantId());
		profile.setLoginProp(ldap.getLoginProp());
		profile.setUrl(ldap.getUrl());
		profile.setName(ldap.getName());
		profile.setPassword(ldap.getPassword());
		profile.setStatus(ldap.getStatus());
		profile.setUserName(ldap.getUsername());
		return profile;
	}

	public static ExtLdapSource ldapSourceProfile2ExtLdapSource(LdapSourceProfile profile) {
		ExtLdapSource ldap = new ExtLdapSource();
		String userName = profile.getUserName();
		String[] strArray = userName.split("@");
		if (strArray != null && strArray.length == 2) {
			String domain = strArray[1];
			String[] dcs = domain.split("\\.");
			if (dcs != null && dcs.length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < dcs.length; i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append("dc=" + dcs[i]);
				}
				ldap.setBasedn(sb.toString());
			}
		}
		ldap.setName(profile.getName());
		ldap.setDescription(profile.getDesc());
		ldap.setLoginProp(profile.getLoginProp());
		ldap.setPassword(profile.getPassword());
		ldap.setUsername(userName);
		ldap.setUrl(profile.getUrl());
		ldap.setTenantId(profile.getEnterpriseId());
		ldap.setStatus(profile.getStatus());
		return ldap;
	}

	public static PageInfo retrievePageInfo(Page<?> page) {
		PageInfo pi = new PageInfo();
		pi.setCurrentPage(page.getNumber());
		pi.setPageSize(page.getSize());
		pi.setTotalPage(page.getTotalPages());
		pi.setTotalRecords((int) page.getTotalElements());
		return pi;
	}
}
