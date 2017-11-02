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
import com.yy.cloud.core.usermgmt.data.repositories.SystemDictionaryRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserOrganizationRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserRepository;
import com.yy.cloud.core.usermgmt.service.OrgnizationService;
import com.yy.cloud.core.usermgmt.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrgnizationServiceImpl implements OrgnizationService {

	@Autowired
	private YYOrganizationRepository YYOrganizationRepository;

	@Autowired
	private YYUserRepository foxUserRepository;

	@Autowired
	private YYUserOrganizationRepository foxUserOrganizationRepository;

	

	@Autowired
	private ModelMapper modelMapp;

	

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@Autowired
	private SystemDictionaryRepository systemDictionaryRepository;

	private static Byte QUOTA_USE = new Byte("0"); // 使用资源
	private static Byte QUOTA_RELEASE = new Byte("1"); // 释放资源

	@Override
	@Transactional
	public GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile) {
		
		GeneralContentResult<String>  result = new GeneralContentResult<String>();
		
		return result;
	}


	


	@Override
	@Transactional
	public GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织，ID:{}, profile:{}", _organizationId, _organizationProfile);
		GeneralResult generalResult = new GeneralResult();
		generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);

		YYOrganization YYOrganization = YYOrganizationRepository.findOne(_organizationId);
		log.info(CommonConstant.LOG_DEBUG_TAG + "修改机构/部门：{}", YYOrganization);

		
		// 机构成员列表
		List<YYUserOrganization> foxUserOrganizations = foxUserOrganizationRepository
				.findByOrganizationId(_organizationId);
		foxUserOrganizationRepository.deleteInBatch(foxUserOrganizations);

	/*	if (!CollectionUtils.isEmpty(_organizationProfile.getMembers())) {
			_organizationProfile.getMembers().forEach(accountProfile -> {
				FoxUserOrganization foxUserOrganization = new FoxUserOrganization();
				foxUserOrganization.setOrganizationId(_organizationId);
				foxUserOrganization.setUserId(accountProfile.getId());
				foxUserOrganizationRepository.save(foxUserOrganization);
			});
		}*/


		YYOrganization.setName(_organizationProfile.getName());
		YYOrganization.setDescription(_organizationProfile.getDescription());
		YYOrganization.setLeadeShip(_organizationProfile.getLeaderId());
		YYOrganizationRepository.save(YYOrganization);
		log.debug(CommonConstant.LOG_DEBUG_TAG + "修改组织成功:{}", _organizationProfile);

		return generalResult;
	}

	
	
	/**
	 * 1，清除机构成员
	 * 2，清除机构（修改状态）
	 */
	@Override
	@Transactional
	public GeneralResult deleteOrgnization(String _organizationId) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织：{}", _organizationId);
		GeneralResult generalResult = new GeneralResult();
		generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		// 查询当前机构
		YYOrganization YYOrganization = YYOrganizationRepository.findOne(_organizationId);
		// 清除机构成员列表
		List<YYUserOrganization> foxUserOrganizations = foxUserOrganizationRepository
				.findByOrganizationId(_organizationId);
		foxUserOrganizationRepository.deleteInBatch(foxUserOrganizations);
		log.debug(CommonConstant.LOG_DEBUG_TAG + "删除组织/机构成功：{}", _organizationId);
		YYOrganizationRepository.delete(_organizationId);
		return generalResult;
	}

	@Override
	public void updateOrgnizationStatus(String _organizationId, Byte _status) {
		YYOrganization YYOrganization = Optional.ofNullable(YYOrganizationRepository.findOne(_organizationId))
				.orElseThrow(() -> new NoRecordFoundException("organization " + _organizationId + " not exist."));
		YYOrganization.setStatus(_status);
		YYOrganizationRepository.save(YYOrganization);
	}

	@Override
	public List<OrganizationItem> listOrganizationsByPage(PageInfo _pageInfo, Byte _status) {
		PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(),
				Sort.Direction.DESC, "createDate");
		// 后台登录用户
		UserDetailsItem userDetailsItem = securityService.getCurrentUser();
		
	    List<RoleItem> roles=userDetailsItem.getRoles();
	   boolean isAdmin=  false;
	    if(roles!=null){
	    	
	    	for(RoleItem roleItem :roles){
	    		
	    		if("系统管理员".equalsIgnoreCase(roleItem.getRoleName())){
	    			isAdmin=true;
	    		}
	    		
	    	}
	    	
	    }

		Page<YYOrganization> YYOrganizations=null;
			log.info(CommonConstant.LOG_DEBUG_TAG + "该用户是后台用户，查询后台机构列表，status:{}", _status);
			if (isAdmin) {
				if (null == _status) {
					YYOrganizations = YYOrganizationRepository.findByStatusLessThan( CommonConstant.DIC_GLOBAL_STATUS_DELETED, pageRequest);
				} else {
					// 查询状态为_status的后台机构
					YYOrganizations = YYOrganizationRepository.findByStatus(
							_status, pageRequest);
				}
			} else {
				YYOrganizations = YYOrganizationRepository.findByUserId(userDetailsItem.getUserId(), pageRequest);
			}
		List<OrganizationItem> organizationItems = new ArrayList<>();
		YYOrganizations.forEach(yyOrganization -> {
			OrganizationItem organizationItem = modelMapp.map(yyOrganization, OrganizationItem.class);
			if (!StringUtils.isBlank(yyOrganization.getLeadeShip())) {
				Optional.ofNullable(foxUserRepository.findOne(yyOrganization.getLeadeShip())).ifPresent(foxUser -> {
					organizationItem.setLeaderName(foxUser.getUserInfo().getUserName());
				});
			}
			organizationItems.add(organizationItem);
		});

		_pageInfo.setTotalPage(YYOrganizations.getTotalPages());
		_pageInfo.setTotalRecords(new Long(YYOrganizations.getTotalElements()).intValue());

		return organizationItems;
	}

	@Override
	public OrganizationItem findOrganizationItemById(String _organizationId) {
		YYOrganization YYOrganization = Optional.ofNullable(YYOrganizationRepository.findOne(_organizationId))
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
