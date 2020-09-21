package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationProfile;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.exception.NoRecordFoundException;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.data.domain.YYOrganization;
import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.domain.YYUserOrganization;
import com.yy.cloud.core.usermgmt.data.repositories.YYOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserRepository;
import com.yy.cloud.core.usermgmt.service.OrgnizationService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrgnizationServiceImpl implements OrgnizationService {

	@Autowired
	private YYOrganizationRepository yyOrganizationRepository;

	@Autowired
	private YYUserRepository foxUserRepository;

	@Autowired
	private YYUserOrganizationRepository foxUserOrganizationRepository;

	@Autowired
	private ModelMapper modelMapp;

	@Autowired
	private SecurityService securityService;


	@Override
	@Transactional
	public GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "The mehtod createOrgnization is  begin");
		log.debug(CommonConstant.LOG_DEBUG_TAG + "_organizationProfile name is \'" + _organizationProfile.getName()
				+ "\'");
		GeneralContentResult<String> result = new GeneralContentResult<String>();
		
		
		String orgName = _organizationProfile.getName();
		List<YYOrganization> countOfList = yyOrganizationRepository.findByNameAndStatusLessThan(orgName,
				CommonConstant.DIC_GLOBAL_STATUS_DELETED);
		if (countOfList !=null && countOfList.size()>0) {
			result.setResultCode(ResultCode.USERMGMT_ORG_EXISTED);
			return result;
		}
		
		YYOrganization yyOrganization = new YYOrganization();
		yyOrganization.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		yyOrganization.setDescription(_organizationProfile.getDescription());
		yyOrganization.setName(_organizationProfile.getName());
		yyOrganization.setLeadeShip(_organizationProfile.getLeaderId());
		yyOrganization.setSerial(_organizationProfile.getSerial());
		yyOrganization = yyOrganizationRepository.save(yyOrganization);
		result.setResultContent(yyOrganization.getId());
		result.setResultCode(ResultCode.OPERATION_SUCCESS);
		return result;
	}

	@Override
	@Transactional
	public GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，ID:{}, profile:{}", _organizationId, _organizationProfile);
		GeneralResult generalResult = new GeneralResult();
		generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);

		YYOrganization YYOrganization = yyOrganizationRepository.findOne(_organizationId);
		log.info(CommonConstant.LOG_DEBUG_TAG + "修改机构/部门：{}", YYOrganization);

		YYOrganization.setSerial(_organizationProfile.getSerial());
		YYOrganization.setName(_organizationProfile.getName());
		YYOrganization.setDescription(_organizationProfile.getDescription());
		YYOrganization.setLeadeShip(_organizationProfile.getLeaderId());
		yyOrganizationRepository.save(YYOrganization);
		log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织成功:{}", _organizationProfile);

		return generalResult;
	}

	/**
	 * 1，清除机构成员 2，清除机构（修改状态）
	 */
	@Override
	@Transactional
	public GeneralResult deleteOrgnization(String _organizationId) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织：{}", _organizationId);
		GeneralResult generalResult = new GeneralResult();
		generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		// 清除机构成员列表
		List<YYUserOrganization> foxUserOrganizations = foxUserOrganizationRepository
				.findByOrganizationId(_organizationId);
		foxUserOrganizationRepository.deleteInBatch(foxUserOrganizations);
		log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织/机构成功：{}", _organizationId);
		yyOrganizationRepository.delete(_organizationId);
		return generalResult;
	}

	@Override
	public void updateOrgnizationStatus(String _organizationId, Byte _status) {
		YYOrganization YYOrganization = Optional.ofNullable(yyOrganizationRepository.findOne(_organizationId))
				.orElseThrow(() -> new NoRecordFoundException("organization " + _organizationId + " not exist."));
		YYOrganization.setStatus(_status);
		yyOrganizationRepository.save(YYOrganization);
	}

	@Override
	public List<OrganizationItem> listOrganizationsByPage(PageInfo _pageInfo, Byte _status, String _name) {
		PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
				Sort.Direction.DESC, "createDate");
		// 后台登录用户
		UserDetailsItem userDetailsItem = securityService.getCurrentUser();

		List<RoleItem> roles = userDetailsItem.getRoles();
		boolean isAdmin = false;
		if (roles != null) {
			for (RoleItem roleItem : roles) {
				if ("系统管理员".equalsIgnoreCase(roleItem.getRoleName())) {
					isAdmin = true;
				}
			}
		}
		Page<YYOrganization> YYOrganizations = null;
		log.info(CommonConstant.LOG_DEBUG_TAG + "该用户是后台用户，查询后台机构列表，status:{}, _name:{}", _status, _name);

		if (isAdmin) {
			if (null == _status) {
				if(StringUtils.isBlank(_name)) {
					YYOrganizations = yyOrganizationRepository
							.findByStatusLessThan(CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
				} else {
					YYOrganizations = yyOrganizationRepository
							.findByStatusLessThanAndNameContaining(CommonConstant.DIC_GLOBAL_STATUS_DELETED, _name.trim(), pageRequest);
				}
			} else {
				// 查询状态为_status的后台机构
				if(StringUtils.isBlank(_name)) {
					YYOrganizations = yyOrganizationRepository.findByStatus(_status, pageRequest);
				} else {
					YYOrganizations = yyOrganizationRepository.findByStatusAndNameContaining(_status, _name, pageRequest);
				}
			}
		} else {
			if(StringUtils.isBlank(_name)) {
				YYOrganizations = yyOrganizationRepository.findByStatusLessThan(CommonConstant.DIC_GLOBAL_STATUS_DELETED,
						pageRequest);
			} else {
				YYOrganizations = yyOrganizationRepository.findByStatusLessThanAndNameContaining(CommonConstant.DIC_GLOBAL_STATUS_DELETED, _name.trim(),
						pageRequest);
			}
		}
		
		List<OrganizationItem> organizationItems = new ArrayList<>();
		YYOrganizations.forEach(yyOrganization -> {
			OrganizationItem organizationItem = new OrganizationItem();
			organizationItem.setDesc(yyOrganization.getDescription());
			organizationItem.setId(yyOrganization.getId());
			organizationItem.setLeaderId(yyOrganization.getLeadeShip());
			organizationItem.setOrganizitionName(yyOrganization.getName());
			organizationItem.setSerial(yyOrganization.getSerial());
			
			organizationItems.add(organizationItem);
		});

		_pageInfo.setTotalPage(YYOrganizations.getTotalPages());
		_pageInfo.setTotalRecords(new Long(YYOrganizations.getTotalElements()));

		return organizationItems;
	}

	@Override
	public OrganizationItem findOrganizationItemById(String _organizationId) {
		YYOrganization YYOrganization = Optional.ofNullable(yyOrganizationRepository.findOne(_organizationId))
				.orElseThrow(
						() -> new NoRecordFoundException(String.format("organization %s not found.", _organizationId)));

		OrganizationItem organizationItem = modelMapp.map(YYOrganization, OrganizationItem.class);

		List<UserItem> userItems = new ArrayList<>();

		List<YYUserOrganization> foxUserOrganizations = foxUserOrganizationRepository
				.findByOrganizationId(_organizationId);
		foxUserOrganizations.forEach(foxUserOrganization -> {
			String userId = foxUserOrganization.getUserId();
			YYUser foxUser = foxUserRepository.findOne(userId);
			UserItem userItem = modelMapp.map(foxUser, UserItem.class);
			userItems.add(userItem);
		});

		if (!StringUtils.isBlank(YYOrganization.getLeadeShip())) {
			Optional.ofNullable(foxUserRepository.findOne(YYOrganization.getLeadeShip())).ifPresent(foxUser -> {
				organizationItem.setLeaderName(foxUser.getUserInfo().getUserName());
			});
		}
		return organizationItem;
	}

}
