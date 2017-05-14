package com.gemii.lizcloud.api.group.admin.service;

import java.util.List;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapSourceProfile;


/**
 * ClassName: LdapMgmtService <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月16日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface LdapMgmtService {

	public GeneralResult checkLdapSource(LdapSourceProfile ldapSource);
	
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

	public GeneralResult deleteLdapSource(String id);
	
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLoginLdapSourceList(String login);

}
