package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.accountcenter.LdapSourceProfile;

/**
 * ClassName: LdapMgmtService <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月4日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface LdapMgmtService {

	public GeneralResult checkLdapSource(LdapSourceProfile ldapSource);
	
	public Object onlyTest(String input);

	public GeneralContentResult<String> createLdapSource(LdapSourceProfile ldapSource);

	public GeneralContentResult<LdapSourceProfile> getLdapSource(String id);

	public GeneralPagingResult<List<LdapSourceProfile>> getEnterpriseLdapSourceList(String enterpriseId, Integer page,
			Integer size);

	public GeneralPagingResult<List<LdapSourceProfile>> getLdapSourceList(Integer page, Integer size);

	public GeneralResult updateLdapSourceStatus(String id, Byte status);

	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLdapSourceList();

	public GeneralContentResult<List<LdapSourceProfile>> getEnterpriseSimpleLdapSourceList(String enterpriseId);

	public GeneralResult updateLdapSource(LdapSourceProfile ldapSource);

	public GeneralResult updateLdapSourceAccount(LdapSourceProfile ldapSource);

	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLoginLdapSourceList(String login);

}
