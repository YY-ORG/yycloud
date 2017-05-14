package com.gemii.lizcloud.api.group.admin.service;

import java.util.List;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.AdUserProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;

/**
 * ClassName: AdUserMgmtService <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月21日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface AdUserMgmtService {

	public GeneralContentResult<String> createAdUser(AdUserProfile profile);

	public GeneralPagingResult<List<AdUserProfile>> getLdapUserList(String id, Integer page, Integer size,
			LdapUserCriteriaQuery criteriaQuery);

}
