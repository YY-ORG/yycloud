package com.gemii.lizcloud.api.group.admin.clients;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.OrganizationResourceProfile;
import com.gemii.lizcloud.common.data.otd.organization.OrganizationItem;
import com.gemii.lizcloud.common.data.otd.platformmgmt.PlatformItem;
import com.gemii.lizcloud.common.data.otd.user.OrganizationResourceItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("usermgmt")
public interface OrgnizationClient {

    @RequestMapping(value = "/authsec/organization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<String> createOrganization(@RequestBody OrganizationProfile _organizationProfile);

    @RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.DELETE)
    GeneralResult deleteOrganization(@PathVariable("organization_id") String _organizationId);

    @RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.PUT)
    GeneralResult modifyOrganization(@PathVariable("organization_id") String _organizationId, @RequestBody OrganizationProfile _organizationProfile);

    @RequestMapping(value = "/authsec/organization/{organization_id}/status", method = RequestMethod.PUT)
    GeneralResult updateOrganizationStatus(
            @PathVariable("organization_id") String _organizationId,
            @RequestParam(value = "status") Byte _status);

    @RequestMapping(value = "/authsec/organizations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<OrganizationItem>> findOrganizations(
            @RequestParam(value = "status", required = false) Byte _status,
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size);

    @RequestMapping(value = "/authsec/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<OrganizationItem> findOrganizationById(
            @PathVariable("organization_id") String _organizationId);

    @RequestMapping(value = "/authsec/organization/platforms/nonorganization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<List<PlatformItem>> getNonOrganizationPlatforms();

    @RequestMapping(value = "/authsec/organization/enterprise/{enterprise_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<List<OrganizationItem>> getOrganizationsByEnterpriseId(@PathVariable("enterprise_id") String enterpriseId);

    @RequestMapping(value = "/authsec/organization/resource/{organization_id}", method = RequestMethod.POST)
    GeneralResult createOrganizationResourceQuota(
            @PathVariable("organization_id") String _organizationId,
            @RequestBody OrganizationResourceProfile _organizationResourceProfile);

    @RequestMapping(value = "/authsec/organization/resource/{organization_id}", method = RequestMethod.PUT)
    GeneralResult updateOrganizationResourceQuota(@PathVariable("organization_id") String _organizationId,
                                                  @RequestBody OrganizationResourceProfile _organizationResourceProfile);

    @RequestMapping(value = "/authsec/organization/resource/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<OrganizationResourceItem> getOrganizationResourceQuota(@PathVariable("organization_id") String _organizationId);

}
