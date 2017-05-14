/**
 * Project Name:liz-admin
 * File Name:PlatformMgmtClient.java
 * Package Name:com.gemii.lizcloud.api.admin.clients
 * Date:Sep 18, 20164:41:45 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.clients;

import io.swagger.annotations.ApiOperation;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.*;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.dto.enterprise.ServicePageQueryCondition;
import com.yy.cloud.common.data.dto.enterprise.VMServiceQueryCondition;
import com.yy.cloud.common.data.dto.platformmgmt.*;
import com.yy.cloud.common.data.otd.platformmgmt.*;
import com.yy.cloud.common.data.platformaccess.SynchronizeResult;
import com.yy.cloud.common.data.platformmgmt.*;

import java.util.List;

/**
 * ClassName:PlatformMgmtClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 18, 2016 4:41:45 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@FeignClient("platformmgmt")
public interface PlatformMgmtClient {

    /**
     * createService:创建服务. Wutao <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/services/type/vm",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    GeneralResult createVMService(@RequestBody VMServiceProfile _profile);

    /**
     * createDiskService:创建云磁盘服务/产品目录. <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/services/type/disk",
           produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    GeneralResult createDiskService(@RequestBody DiskServiceProfile _profile);

    /**
     * getServiceByRegion:依据区域来检索服务. <br/>
     *
     * @param _regionId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/region/{_regionId}/services")
    @ResponseBody
    GeneralPagingResult<List<ServiceItem>> getServiceByRegion(@PathVariable("_regionId") String _regionId);

    /**
     * getService:获取所有平台下的所有服务. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/services")
    @ResponseBody
    GeneralPagingResult<List<ServiceItem>> getService();

    /**
     * getServiceSimpleItemList:获取所有的服务目录，为下拉框准备. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/services/simple")
    @ResponseBody
    GeneralContentResult<List<ServiceSimpleItem>> getServiceSimpleItemList();

    /**
     * getServiceByPaing:分页获取平台下的所有服务目录. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/services/paging")
    @ResponseBody
    GeneralPagingResult<List<ServiceItem>> getServiceByPaing(@RequestBody PageParameter _request);

    /**
     * getServiceByConnPaing:依据检索条件，分页获取所有服务目录 Wutao. <br/>
     *
     * @param _request
     * @param _conn
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/services/search/paging")
    @ResponseBody
    GeneralPagingResult<List<ServiceItem>> getServiceByConnPaing(@RequestBody ServicePageQueryCondition _conn);

    /**
     * getServiceByRegionPaing:分页获取某平台下所有服务目录. <br/>
     *
     * @param _serviceId
     * @param _request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/{_regionId}/services/paging")
    @ResponseBody
    GeneralPagingResult<List<ServiceItem>> getServiceByRegionPaing(@PathVariable("_regionId") String _serviceId,
                                                                   @RequestBody PageParameter _request);

    /**
     * getServiceProfile:获取某个服务的所有信息. <br/>
     *
     * @param _platformId
     * @param _serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/platform/{_platformId}/service/{_serviceId}/profile")
    @ResponseBody
    GeneralPagingResult<VMServiceProfile> getServiceProfile(@PathVariable("_platformId") String _platformId,
                                                            @PathVariable("_serviceId") String _serviceId);

    /**
     * getServiceProfileList:获取某个平台下所有服务的profile. <br/>
     *
     * @param _platformId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/platform/{_platformId}/services/profiles")
    @ResponseBody
    GeneralPagingResult<List<VMServiceProfile>> getServiceProfileList(
            @PathVariable("_platformId") String _platformId);

    /**
     * updateService: 更新某个服务. <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/authsec/platform/service",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    GeneralPagingResult<GeneralContent> updateService(@RequestBody VMServiceProfile _profile);

    /**
     * updateServiceStatus:更新某个服务的状态. Wutao<br/>
     *
     * @param _serviceId
     * @param _status
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/authsec/service/{_serviceId}/status/{_status}")
    @ResponseBody
    GeneralResult updateServiceStatus(@PathVariable("_serviceId") String _serviceId,
                                      @PathVariable("_status") Byte _status);

    /**
     * deleteService:删除某个服务. Wutao<br/>
     *
     * @param _serviceId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/authsec/service/{_serviceId}")
    @ResponseBody
    GeneralPagingResult<GeneralContent> deleteService(@PathVariable("_serviceId") String _serviceId);

    /**
     * getServiceSKU:分页获取某个服务下所有的SKU. Wutao<br/>
     *
     * @param _serviceId
     * @param _request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/platform/service/{_serviceId}/skus/paging")
    @ResponseBody
    GeneralPagingResult<List<ServiceSku>> getServiceSKU(@PathVariable("_serviceId") String _serviceId,
                                                        @RequestBody PageParameter _request);

    /**
     * getZoneByPlatform:获取某个平台下的所有可用区. <br/>
     *
     * @param _platformId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/platform/{_platformId}/zones")
    @ResponseBody
    GeneralPagingResult<List<ZoneItem>> getZoneByPlatform(@PathVariable("_platformId") String _platformId);

    /**
     * getPlatformResouceQuota:获取所有平台下的所有资源限额. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/authsec/platforms/resources/quotas/paging")
    @ResponseBody
    GeneralPagingResult<List<PlatformResourceQuota>> getPlatformResourceQuota(
            @RequestBody PageParameter _request);

    /**
     * getRegions:获取所有平台下的所有区域. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/platforms/regions")
    @ResponseBody
    GeneralPagingResult<List<Region>> getRegions();

    /**
     * getRegions:获取所有平台下的某类型的区域. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/platforms/regions/type/{_type}")
    @ResponseBody
    GeneralPagingResult<List<Region>> getRegionsByType(@PathVariable("_type") Byte _type);

    /**
     * getStartupDisk:获取某个平台下的storage信息. <br/>
     *
     * @param _platformId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/authsec/platform/{_platformId}/storages")
    @ResponseBody
    GeneralPagingResult<List<StartUpDisk>> getStartupDisk(@PathVariable("_platformId") String _platformId);

    @RequestMapping(method = RequestMethod.GET, value = "/authsec/servicetemplates")
    @ResponseBody
    GeneralContentResult<List<ServiceTemplateItem>> getServiceTemplate(@RequestParam(value = "status", required = false) Byte status);

    /**
     * registerPlatform:创建平台. <br/>
     *
     * @param platform
     * @return 返回的Content是新创建的Platform的ID
     */
    @RequestMapping(value = "/authsec/platforms", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<String> registerPlatform(@RequestBody PlatformProfile platform);

    /**
     * getAllActivePlatforms:非分页，获取所有激活平台的简单信息，Guofeng. <br/>
     *
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/simpleinfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<List<PlatformSimpleItem>> getAllActivePlatforms();

    @RequestMapping(value = "/authsec/platforms/{page}/{size}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralPagingResult<List<PlatformProfile>> listPlatformsByPage(@PathVariable("page") Integer page,
                                                                   @PathVariable("size") Integer size);

    @RequestMapping(value = "/authsec/platforms/{_platformId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralPagingResult<PlatformProfile> findPlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/synchronizeCounts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralPagingResult<SynchronizeObjectInfo> synchronizeCounts(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/zones/synchronize", method = RequestMethod.GET)
    GeneralContentResult<SynchronizeResult> synchronizeZones(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/storages/synchronize", method = RequestMethod.GET)
    GeneralContentResult<SynchronizeResult> synchronizeStorages(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/flavors/synchronize", method = RequestMethod.GET)
    GeneralContentResult<SynchronizeResult> synchronizeFlavors(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/images/synchronize", method = RequestMethod.GET)
    GeneralContentResult<SynchronizeResult> synchronizeImages(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/regions/synchronize", method = RequestMethod.GET)
    GeneralContentResult<SynchronizeResult> synchronizeRegions(@PathVariable("_platformId") String _platformId);

    /**
     * synchronizeHosts:同步宿主机. <br/>
     *
     * @param _platformId
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/{_platformId}/regions/hosts", method = RequestMethod.GET)
    GeneralContentResult<SynchronizeResult> synchronizeHosts(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/zoneQuotas", method = RequestMethod.GET)
    GeneralContentResult<List<PlatformZoneQuotaItem>> listZoneQuotas(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/storageQuotas", method = RequestMethod.GET)
    GeneralContentResult<List<PlatformStorageQuotaItem>> listStorageQuotas(
            @PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/flavors", method = RequestMethod.GET)
    GeneralContentResult<List<Flavor>> listFlavors(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/zoneQuotas", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult modifyZoneQuotas(@PathVariable("_platformId") String _platformId,
                                   @RequestBody PlatformZoneQuotaProfile[] zoneQuotas);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/storageQuotas", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult modifyStorageQuotas(@PathVariable("_platformId") String _platformId,
                                      @RequestBody PlatformStorageQuotaProfile[] storageQuotas);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/flavors", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult modifyFlavors(@PathVariable("_platformId") String _platformId, @RequestBody Flavor[] flavors);

    @RequestMapping(value = "/authsec/platforms/{_platformId}", method = RequestMethod.DELETE)
    GeneralResult deletePlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/active", method = RequestMethod.GET)
    GeneralResult activePlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platforms/{_platformId}/suspend", method = RequestMethod.GET)
    GeneralResult suspendPlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platform/{_platformId}/images", method = RequestMethod.GET)
    GeneralContentResult<List<PlatformImageItem>> getPlatformImages(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/authsec/platform/{_platformId}/images", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult updatePlatformImages(@PathVariable("_platformId") String _platformId,
                                       @RequestBody PlatformImageProfile[] _profile);

    /**
     * getVmServicePlatformDetailInfo:获取平台相关信息，为创建VM产品目录做准备. <br/>
     *
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/groupinfo/vm", method = RequestMethod.GET)
    GeneralContentResult<VmPlatformDetailInfoResp> getVmServicePlatformDetailInfo(@RequestBody VMServiceQueryCondition _conn);

    /**
     * getDiskServicePlatformDetailInfo:获取平台相关信息，为创建Disk产品目录做准备. <br/>
     *
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/groupinfo/disk", method = RequestMethod.GET)
    GeneralContentResult<DiskPlatformDetailInfoResp> getDiskServicePlatformDetailInfo();

}
