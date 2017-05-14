package com.gemii.lizcloud.api.group.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemii.lizcloud.api.group.admin.clients.AdUserMgmtClient;
import com.gemii.lizcloud.api.group.admin.service.AdUserMgmtService;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.AdUserProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: AdUserMgmtServiceImpl <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月21日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Service
@Slf4j
public class AdUserMgmtServiceImpl implements AdUserMgmtService {

	@Autowired
	private AdUserMgmtClient adUserMgmtClient;

	@Override
	public GeneralContentResult<String> createAdUser(AdUserProfile profile) {
		GeneralContentResult<String> result = adUserMgmtClient.createAdUser(profile);
		return result;
	}

	@Override
	public GeneralPagingResult<List<AdUserProfile>> getLdapUserList(String id, Integer page, Integer size,
			LdapUserCriteriaQuery criteriaQuery) {
		return adUserMgmtClient.getLdapUserList(id, page, size, criteriaQuery);
	}

}
