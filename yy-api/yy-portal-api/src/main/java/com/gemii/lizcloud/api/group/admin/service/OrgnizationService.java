package com.gemii.lizcloud.api.group.admin.service;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.gemii.lizcloud.common.data.otd.organization.OrganizationItem;
import com.gemii.lizcloud.common.data.otd.platformmgmt.PlatformItem;
import com.gemii.lizcloud.common.data.otd.user.OrganizationResourceItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface OrgnizationService {

    GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile);

    GeneralResult modifyOrganization(String _organizationId, OrganizationProfile _organizationProfile);

    GeneralPagingResult<List<OrganizationItem>> findOrganizations(Byte _status, Integer _page, Integer _size);

    GeneralResult enableOrgnization(String _organizationId);

    GeneralResult disableOrgnization(String _organizationId);

    GeneralResult deleteOrganization(String _organizationId);

    GeneralContentResult<OrganizationItem> findOrganizationById(String _organizationId);

    GeneralContentResult<List<PlatformItem>> getNonOrganizationPlatforms();

    GeneralContentResult<List<OrganizationItem>> getOrganizationsByEnterpriseId(String enterpriseId);

    GeneralResult createOrganizationResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile);

    GeneralResult updateOrganizationResourceQuota(String _organizationId, OrganizationResourceProfile _organizationResourceProfile);

    GeneralContentResult<OrganizationResourceItem> getOrganizationResourceQuota(String _organizationId);

}
