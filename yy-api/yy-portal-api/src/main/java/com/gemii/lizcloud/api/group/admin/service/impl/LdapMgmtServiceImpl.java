package com.gemii.lizcloud.api.group.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemii.lizcloud.api.group.admin.clients.LdapMgmtClient;
import com.gemii.lizcloud.api.group.admin.service.LdapMgmtService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapSourceProfile;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: LdapMgmtServiceImpl <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月16日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Service
@Slf4j
public class LdapMgmtServiceImpl implements LdapMgmtService {

	@Autowired
	private LdapMgmtClient ldapMgmtClient;

	@Override
	public GeneralResult checkLdapSource(LdapSourceProfile ldapSource) {
		return ldapMgmtClient.testLdapSource(ldapSource);
	}

	@Override
	public GeneralContentResult<String> createLdapSource(LdapSourceProfile ldapSource) {
		return ldapMgmtClient.createLdapSource(ldapSource);
	}

	@Override
	public GeneralContentResult<LdapSourceProfile> getLdapSource(String id) {
		return ldapMgmtClient.getLdapSource(id);
	}

	@Override
	public GeneralPagingResult<List<LdapSourceProfile>> getEnterpriseLdapSourceList(String enterpriseId, Integer page,
			Integer size) {
		if (page > 0) {
			page--;
		}
		return ldapMgmtClient.getEnterpriseLdapSourceList(enterpriseId, page, size);
	}

	@Override
	public GeneralPagingResult<List<LdapSourceProfile>> getLdapSourceList(Integer page, Integer size) {
		if (page > 0) {
			page--;
		}
		return ldapMgmtClient.getLdapSourceList(page, size);
	}

	@Override
	public GeneralResult updateLdapSourceStatus(String id, Byte status) {
		return ldapMgmtClient.updateLdapSourceStatus(id, status);
	}

	@Override
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLdapSourceList() {
		return ldapMgmtClient.getSimpleLdapSourceList();
	}

	@Override
	public GeneralContentResult<List<LdapSourceProfile>> getEnterpriseSimpleLdapSourceList(String enterpriseId) {
		return ldapMgmtClient.getEnterpriseSimpleLdapSourceList(enterpriseId);
	}

	@Override
	public GeneralResult updateLdapSource(LdapSourceProfile ldapSource) {
		return ldapMgmtClient.updateLdapSource(ldapSource.getId(), ldapSource);
	}

	@Override
	public GeneralResult updateLdapSourceAccount(LdapSourceProfile ldapSource) {
		return ldapMgmtClient.updateLdapSourceAccount(ldapSource.getId(), ldapSource);
	}

	@Override
	public GeneralResult deleteLdapSource(String id) {
		return ldapMgmtClient.deleteLdapSource(id);
	}

	@Override
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLoginLdapSourceList(String login) {
		return ldapMgmtClient.getSimpleLoginLdapSourceList(login);
	}

}
