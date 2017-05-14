package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.accountcenter.LdapSourceProfile;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapSource;
import com.yy.cloud.core.usermgmt.data.repositories.ExtLdapSourceRepository;
import com.yy.cloud.core.usermgmt.service.LdapMgmtService;
import com.yy.cloud.core.usermgmt.utils.ObjectConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: LdapMgmtServiceImpl <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月4日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Repository
@Service
@Slf4j
public class LdapMgmtServiceImpl implements LdapMgmtService {

	@Autowired
	private ExtLdapSourceRepository extLdapSourceRepository;

	@Override
	public Object onlyTest(String input) {
		log.debug("onlyTest " + input);
		return input;
	}

	@Override
	public GeneralResult checkLdapSource(LdapSourceProfile ldapSource) {
		GeneralResult result = new GeneralResult();
		try {
			LdapContextSource cs = new LdapContextSource();
			cs.setCacheEnvironmentProperties(false);
			cs.setUrl(ldapSource.getUrl());
			cs.setAuthenticationSource(new AuthenticationSource() {
				@Override
				public String getCredentials() {
					return ldapSource.getPassword();
				}

				@Override
				public String getPrincipal() {
					return ldapSource.getUserName();
				}
			});
			LdapTemplate ldapTemplate = new LdapTemplate(cs);
			Object o = ldapTemplate.lookup(LdapUtils.emptyLdapName());
			log.debug("checkLdapSource root=" + o);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} catch (Exception e) {
			log.error("checkLdapSource ex=" + e.toString());
			result.setResultCode(ResultCode.ADUSERMGMT_LDAP_CONNECT_EXCEPTION);
			result.setDetailDescription(e.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralContentResult<String> createLdapSource(LdapSourceProfile profile) {
		GeneralContentResult<String> result = new GeneralContentResult<String>();
		if (profile != null) {
			ExtLdapSource ldap = ObjectConverter.ldapSourceProfile2ExtLdapSource(profile);
			ldap.setStatus(AdUserMgmtConstants.STATUS_GLOBAL_ENABLE);
			ldap = extLdapSourceRepository.save(ldap);
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			result.setResultContent(ldap.getId());
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public GeneralContentResult<LdapSourceProfile> getLdapSource(String id) {
		GeneralContentResult<LdapSourceProfile> result = new GeneralContentResult<LdapSourceProfile>();
		ExtLdapSource ldap = extLdapSourceRepository.findOne(id);
		if (ldap != null) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			LdapSourceProfile profile = ObjectConverter.extLdapSource2LdapSourceProfile(ldap);
			result.setResultContent(profile);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public GeneralPagingResult<List<LdapSourceProfile>> getEnterpriseLdapSourceList(String enterpriseId, Integer page,
			Integer size) {
		GeneralPagingResult<List<LdapSourceProfile>> result = new GeneralPagingResult<List<LdapSourceProfile>>();
		PageRequest pageRequest = new PageRequest(page, size);
		Page<ExtLdapSource> profilePage = extLdapSourceRepository.findByTenantId(enterpriseId, pageRequest);
		if (profilePage != null) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			result.setPageInfo(ObjectConverter.retrievePageInfo(profilePage));
			if (profilePage.getContent() != null) {
				ArrayList<LdapSourceProfile> profiles = new ArrayList<LdapSourceProfile>();
				for (ExtLdapSource ldap : profilePage.getContent()) {
					profiles.add(ObjectConverter.extLdapSource2LdapSourceProfile(ldap));
				}
				result.setResultContent(profiles);
			}
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public GeneralPagingResult<List<LdapSourceProfile>> getLdapSourceList(Integer page, Integer size) {
		GeneralPagingResult<List<LdapSourceProfile>> result = new GeneralPagingResult<List<LdapSourceProfile>>();
		PageRequest pageRequest = new PageRequest(page, size);
		Page<ExtLdapSource> profilePage = extLdapSourceRepository.findByTenantIdIsNull(pageRequest);
		if (profilePage != null) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			result.setPageInfo(ObjectConverter.retrievePageInfo(profilePage));
			if (profilePage.getContent() != null) {
				ArrayList<LdapSourceProfile> profiles = new ArrayList<LdapSourceProfile>();
				for (ExtLdapSource ldap : profilePage.getContent()) {
					profiles.add(ObjectConverter.extLdapSource2LdapSourceProfile(ldap));
				}
				result.setResultContent(profiles);
			}
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralResult updateLdapSourceStatus(String id, Byte status) {
		GeneralResult result = new GeneralResult();
		int ret = extLdapSourceRepository.setStatusFor(status, id);
		if (ret > 0) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLdapSourceList() {
		GeneralContentResult<List<LdapSourceProfile>> result = new GeneralContentResult<List<LdapSourceProfile>>();
		List<ExtLdapSource> ldaps = extLdapSourceRepository.findByTenantIdIsNull();
		if (ldaps != null) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			ArrayList<LdapSourceProfile> profiles = new ArrayList<LdapSourceProfile>();
			for (ExtLdapSource ldap : ldaps) {
				profiles.add(ObjectConverter.extLdapSource2LdapSourceProfile(ldap));
			}
			result.setResultContent(profiles);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public GeneralContentResult<List<LdapSourceProfile>> getEnterpriseSimpleLdapSourceList(String enterpriseId) {
		GeneralContentResult<List<LdapSourceProfile>> result = new GeneralContentResult<List<LdapSourceProfile>>();
		List<ExtLdapSource> ldaps = extLdapSourceRepository.findByTenantId(enterpriseId);
		if (ldaps != null) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			ArrayList<LdapSourceProfile> profiles = new ArrayList<LdapSourceProfile>();
			for (ExtLdapSource ldap : ldaps) {
				profiles.add(ObjectConverter.extLdapSource2LdapSourceProfile(ldap));
			}
			result.setResultContent(profiles);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralResult updateLdapSource(LdapSourceProfile ldapSource) {
		GeneralResult result = new GeneralResult();
		int ret = extLdapSourceRepository.setNameAndDescriptionFor(ldapSource.getName(), ldapSource.getDesc(),
				ldapSource.getId());
		if (ret > 0) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralResult updateLdapSourceAccount(LdapSourceProfile ldapSource) {
		GeneralResult result = new GeneralResult();
		int ret = extLdapSourceRepository.setLdapAccountFor(ldapSource.getUrl(), ldapSource.getUserName(),
				ldapSource.getPassword(), ldapSource.getId());
		if (ret > 0) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Override
	public GeneralContentResult<List<LdapSourceProfile>> getSimpleLoginLdapSourceList(String login) {
		GeneralContentResult<List<LdapSourceProfile>> result = new GeneralContentResult<List<LdapSourceProfile>>();
		List<ExtLdapSource> ldaps = extLdapSourceRepository.findByLoginName(login);
		if (ldaps != null) {
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
			ArrayList<LdapSourceProfile> profiles = new ArrayList<LdapSourceProfile>();
			for (ExtLdapSource ldap : ldaps) {
				profiles.add(ObjectConverter.extLdapSource2LdapSourceProfile(ldap));
			}
			result.setResultContent(profiles);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

}
