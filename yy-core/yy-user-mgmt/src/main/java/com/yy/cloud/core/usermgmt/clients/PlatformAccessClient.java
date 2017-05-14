package com.yy.cloud.core.usermgmt.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformZoneQuotaProfile;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageQuotaItem;
import com.yy.cloud.common.data.platformaccess.Platform;
import com.yy.cloud.common.data.platformmgmt.Flavor;

import java.util.List;

@FeignClient("platformaccess")
public interface PlatformAccessClient {

    @RequestMapping(method = RequestMethod.POST, value = "/noauth/platforms/ids", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<List<PlatformItem>> findPlatformByIds(@RequestBody List<PlatformProfile> _platformProfiles);

    @RequestMapping(method = RequestMethod.POST, value = "/noauth/platforms/nonIds", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<List<PlatformItem>> findPlatformByNonIds(@RequestBody List<PlatformProfile> _platformProfiles);

}
