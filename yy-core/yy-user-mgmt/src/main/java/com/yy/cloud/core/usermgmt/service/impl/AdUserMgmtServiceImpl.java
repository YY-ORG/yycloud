package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.accountcenter.AdLoginReq;
import com.yy.cloud.common.data.dto.accountcenter.AdUserProfile;
import com.yy.cloud.common.data.dto.accountcenter.LdapUserCriteriaQuery;
import com.yy.cloud.common.data.dto.accountcenter.UserProfile;
import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapSource;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;
import com.yy.cloud.core.usermgmt.data.domain.FoxUserOrganization;
import com.yy.cloud.core.usermgmt.data.domain.FoxUserRole;
import com.yy.cloud.core.usermgmt.data.repositories.ExtLdapSourceRepository;
import com.yy.cloud.core.usermgmt.data.repositories.ExtLdapUserRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxUserOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxUserRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxUserRoleRepository;
import com.yy.cloud.core.usermgmt.ldap.dao.PersonDao;
import com.yy.cloud.core.usermgmt.ldap.domain.Person;
import com.yy.cloud.core.usermgmt.service.AdUserMgmtService;
import com.yy.cloud.core.usermgmt.utils.ObjectConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: AdUserMgmtServiceImpl <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月16日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Repository
@Service
@Slf4j
public class AdUserMgmtServiceImpl implements AdUserMgmtService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private ExtLdapSourceRepository extLdapSourceRepository;

	@Autowired
	private ExtLdapUserRepository extLdapUserRepository;

	@Autowired
	private FoxUserRepository foxUserRepository;

	@Autowired
	private FoxUserOrganizationRepository foxUserOrganizationRepository;

	@Autowired
	private FoxUserRoleRepository foxUserRoleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public GeneralPagingResult<List<AdUserProfile>> findAdUserProfile(String ldapSourceId,
			LdapUserCriteriaQuery criteriaQuery, Pageable pageable) {
		GeneralPagingResult<List<AdUserProfile>> result = new GeneralPagingResult<List<AdUserProfile>>();
		ExtLdapSource ldap = extLdapSourceRepository.findOne(ldapSourceId);
		if (ldap != null) {
			List<Person> people = personDao.findByFilter(ldap, criteriaQuery.getFilter());
			log.debug("findAdUserProfile filter=" + criteriaQuery.getFilter() + ";result=" + people);
			if (people != null) {
				ArrayList<AdUserProfile> profiles = new ArrayList<AdUserProfile>();
				for (Person p : people) {
					AdUserProfile profile = ObjectConverter.ldapPerson2AdUserProfile(p);
					profiles.add(profile);
				}
				result.setResultContent(profiles);
			}
			result.setResultCode(ResultCode.OPERATION_SUCCESS);
		} else {
			result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralContentResult<String> createAdUser(AdUserProfile profile, String userId) {
		GeneralContentResult<String> result = new GeneralContentResult<String>();
		ExtLdapUser user = ObjectConverter.adUserProfile2ExtLdapUser(profile);
		user.setUserId(userId);
		user.setStatus(AdUserMgmtConstants.STATUS_GLOBAL_ENABLE);
		user = extLdapUserRepository.save(user);
		result.setResultCode(ResultCode.OPERATION_SUCCESS);
		result.setResultContent(userId);
		return result;
	}

	// private String createUser(UserProfile _userProfile) {
	// FoxUser foxUser = modelMapper.map(_userProfile, FoxUser.class);
	// foxUser.setType(AdUserMgmtConstants.USER_TYPE_AD);
	// foxUser.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
	// foxUserRepository.save(foxUser);
	// // 绑定角色
	// if (_userProfile.getRoles() != null &&
	// !_userProfile.getRoles().isEmpty()) {
	// _userProfile.getRoles().forEach(roleProfile -> {
	// FoxUserRole foxUserRole = new FoxUserRole();
	// foxUserRole.setRoleId(roleProfile.getId());
	// foxUserRole.setUserId(foxUser.getId());
	// foxUserRoleRepository.save(foxUserRole);
	// });
	// }
	//
	// // 绑定机构
	// if (_userProfile.getOrganizations() != null &&
	// !_userProfile.getOrganizations().isEmpty()) {
	// _userProfile.getOrganizations().forEach(organizationProfile -> {
	// FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
	// foxUserOrganization.setUserId(foxUser.getId());
	// foxUserOrganization.setOrganizationId(organizationProfile.getId());
	// foxUserOrganizationRepository.save(foxUserOrganization);
	// });
	// }
	//
	// return foxUser.getId();
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public GeneralContentResult<String> loginAdUser(AdLoginReq loginReq) {
		GeneralContentResult<String> result = new GeneralContentResult<String>();
		ExtLdapUser adUser = null;
		if (loginReq.getLdapId() == null) {
			Long count = extLdapUserRepository.countByLogin(loginReq.getLogin());
			if (count != null) {
				if (count == 0) {
					result.setResultCode(ResultCode.ADUSERMGMT_LOGIN_NOT_EXIST_EXCEPTION);
				} else if (count > 1) {
					result.setResultCode(ResultCode.ADUSERMGMT_LOGIN_MULTI_LDAPS_EXCEPTION);
				} else {
					adUser = extLdapUserRepository.findOneByLogin(loginReq.getLogin());
				}
			} else {
				result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
			}
		} else {
			adUser = extLdapUserRepository.findOneByLoginAndLdapSourceId(loginReq.getLogin(), loginReq.getLdapId());
			if (adUser == null) {
				result.setResultCode(ResultCode.ADUSERMGMT_LOGIN_NOT_EXIST_EXCEPTION);
			}
		}
		if (adUser != null) {
			ExtLdapSource ldap = extLdapSourceRepository.findOne(adUser.getLdapSourceId());
			if (ldap == null) {
				result.setResultCode(ResultCode.ADUSERMGMT_UNEXPECTED_EXCEPTION);
			} else {
				Person person = personDao.login(ldap, adUser, loginReq.getPassword());
				if (person == null) {
					log.warn("ldap login failed, ad user id = " + adUser.getId());
					if (loginReq.getPassword().equals(adUser.getPassword())) {
						result.setResultCode(ResultCode.OPERATION_SUCCESS);
						result.setResultContent(adUser.getUserId());
					} else {
						result.setResultCode(ResultCode.ADUSERMGMT_LOGIN_LDAP_REJECT_EXCEPTION);
					}
				} else {
					result.setResultCode(ResultCode.OPERATION_SUCCESS);
					result.setResultContent(adUser.getUserId());
					if (!loginReq.getPassword().equals(adUser.getPassword())) {
						log.debug("update ad user's password");
						extLdapUserRepository.setPasswordFor(loginReq.getPassword(), adUser.getId());
					}
				}
			}
		}
		return result;
	}

	@Override
	public ExtLdapUser findByUserId(String userId) {
		return extLdapUserRepository.findOneByUserId(userId);
	}

	@Override
	public ExtLdapUser getAdUser(String login, String ldapId) {
		ExtLdapUser user = extLdapUserRepository.findOneByLoginAndLdapSourceId(login, ldapId);
		return user;
	}

}
