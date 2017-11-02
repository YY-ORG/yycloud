package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.service.OrgnizationService;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationProfile;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrgnizationServiceImpl implements OrgnizationService {
	
	@Override
	public GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResult deleteOrgnization(String _organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOrgnizationStatus(String _organizationId, Byte _status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OrganizationItem> listOrganizationsByPage(PageInfo _pageInfo, Byte _status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganizationItem findOrganizationItemById(String _organizationId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
