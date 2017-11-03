package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.OrgnizationClient;
import com.yy.cloud.api.admin.service.OrgnizationService;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationProfile;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OrgnizationServiceImpl implements OrgnizationService {
	
	
	 @Autowired
     private OrgnizationClient orgnizationClient;
	
	@Override
	public GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile) {
		return orgnizationClient.createOrganization(_organizationProfile);
	}

	@Override
	public GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile) {
		return orgnizationClient.modifyOrganization(_organizationId, _organizationProfile);
	}

	@Override
	public GeneralResult deleteOrgnization(String _organizationId) {
		return orgnizationClient.deleteOrganization(_organizationId);
	}

	@Override
	public void updateOrgnizationStatus(String _organizationId, Byte _status) {
		orgnizationClient.updateOrganizationStatus(_organizationId, _status);
	}

	@Override
	public GeneralPagingResult<List<OrganizationItem>>  listOrganizationsByPage(Byte _status, Integer _page, Integer _size) {
		return orgnizationClient.findOrganizations(_status, _page, _size);
	}

	@Override
	public GeneralContentResult<OrganizationItem> findOrganizationItemById(String _organizationId) {
		return orgnizationClient.findOrganizationById(_organizationId);
	}
	
	
	
}
