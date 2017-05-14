package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.accountcenter.AdLoginReq;
import com.yy.cloud.common.data.dto.accountcenter.AdUserProfile;
import com.yy.cloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;

/**
 * ClassName: AdUserMgmtService <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月16日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface AdUserMgmtService {
	public GeneralPagingResult<List<AdUserProfile>> findAdUserProfile(String ldapSourceId,
			LdapUserCriteriaQuery criteriaQuery, Pageable pageable);

	public ExtLdapUser getAdUser(String login, String ldapId);

	public GeneralContentResult<String> createAdUser(AdUserProfile profile, String userId);

	public GeneralContentResult<String> loginAdUser(AdLoginReq loginReq);

	public ExtLdapUser findByUserId(String userId);
}
