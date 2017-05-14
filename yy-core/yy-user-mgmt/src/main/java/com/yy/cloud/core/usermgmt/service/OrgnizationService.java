package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationProfile;
import com.yy.cloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.yy.cloud.common.data.dto.usermgmt.OrganizationResourceQuotaUsageProfile;
import com.yy.cloud.common.data.otd.organization.OrganizationItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.user.OrganizationResourceItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationQuotaResp;

/**
 * Created by guof on 2016/10/27.
 */
public interface OrgnizationService {

    /**
     * 创建组织
     *
     * @param _organizationProfile 组织信息
     * @return 组织ID
     */
    GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile);

    GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile);

    /**
     * 删除组织
     *
     * @param _organizationId 组织ID
     */
    GeneralResult deleteOrgnization(String _organizationId);

    /**
     * 修改组织状态
     *
     * @param _organizationId 组织ID
     * @param _status         状态
     */
    void updateOrgnizationStatus(String _organizationId, Byte _status);

    /**
     * 分页列出组织
     *
     * @param _pageInfo
     * @param _status
     * @return
     */
    List<OrganizationItem> listOrganizationsByPage(PageInfo _pageInfo, Byte _status);

    OrganizationItem findOrganizationItemById(String _organizationId);

    List<PlatformItem> findNonOrganizationPlatforms();

    List<OrganizationItem> findOrganizationsByTenantId(String _tenantId);

    OrganizationResourceItem findResourceQuotaByOrganizationId(String _organizationId);

    void createResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile);

    GeneralResult updateResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile);

    GeneralContentResult<OrganizationQuotaResp> updateOrganizationUsedResourceQuota(String _organizationId, List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfileList);

    GeneralContentResult<OrganizationQuotaResp> validateOrganizationUsedResourceQuota(String _organizationId, List<OrganizationResourceQuotaUsageProfile> organizationResourceQuotaUsageProfileList);
}
