package com.gemii.lizcloud.api.group.admin.service.impl;

import com.gemii.lizcloud.api.group.admin.clients.OrgnizationClient;
import com.gemii.lizcloud.api.group.admin.service.OrgnizationService;
import com.gemii.lizcloud.common.constant.CommonConstant;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.gemii.lizcloud.common.data.otd.organization.OrganizationItem;
import com.gemii.lizcloud.common.data.otd.platformmgmt.PlatformItem;
import com.gemii.lizcloud.common.data.otd.user.OrganizationResourceItem;
import com.gemii.lizcloud.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgnizationServiceImpl implements OrgnizationService {

    @Autowired
    private OrgnizationClient orgnizationClient;

    @Override
    public GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile) {
        return orgnizationClient.createOrganization(_organizationProfile);
    }

    @Override
    public GeneralResult modifyOrganization(String _organizationId, OrganizationProfile _organizationProfile) {
        return orgnizationClient.modifyOrganization(_organizationId, _organizationProfile);
    }

    @Override
    public GeneralPagingResult<List<OrganizationItem>> findOrganizations(Byte _status, Integer _page, Integer _size) {
        return orgnizationClient.findOrganizations(_status, PageUtils.getPageNo(_page), _size);
    }

    @Override
    public GeneralResult enableOrgnization(String _organizationId) {
        return orgnizationClient.updateOrganizationStatus(_organizationId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
    }

    @Override
    public GeneralResult disableOrgnization(String _organizationId) {
        return orgnizationClient.updateOrganizationStatus(_organizationId, CommonConstant.DIC_GLOBAL_STATUS_CANCELLED);
    }

    @Override
    public GeneralResult deleteOrganization(String _organizationId) {
        return orgnizationClient.deleteOrganization(_organizationId);
    }

    @Override
    public GeneralContentResult<OrganizationItem> findOrganizationById(String _organizationId) {
        return orgnizationClient.findOrganizationById(_organizationId);
    }

    @Override
    public GeneralContentResult<List<PlatformItem>> getNonOrganizationPlatforms() {
        return orgnizationClient.getNonOrganizationPlatforms();
    }

    @Override
    public GeneralContentResult<List<OrganizationItem>> getOrganizationsByEnterpriseId(String _enterpriseId) {
        return orgnizationClient.getOrganizationsByEnterpriseId(_enterpriseId);
    }

    @Override
    public GeneralResult createOrganizationResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile) {
        return orgnizationClient.createOrganizationResourceQuota(_organizationId, _organizationResourceProfile);
    }

    @Override
    public GeneralResult updateOrganizationResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile) {
        return orgnizationClient.updateOrganizationResourceQuota(_organizationId, _organizationResourceProfile);
    }

    /**
     * 查询单个机构下的资源配额
     * @param _organizationId
     * @return
     */
    @Override
    public GeneralContentResult<OrganizationResourceItem> getOrganizationResourceQuota(String _organizationId) {
        return orgnizationClient.getOrganizationResourceQuota(_organizationId);
    }
}
