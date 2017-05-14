package com.yy.cloud.api.admin.clients;

import com.gemii.lizcloud.cloudconnector.dto.PlatformAuth;
import com.gemii.lizcloud.cloudconnector.model.RegionItem;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.dto.enterprise.VMServiceQueryCondition;
import com.yy.cloud.common.data.dto.platformmgmt.*;
import com.yy.cloud.common.data.otd.platformmgmt.*;
import com.yy.cloud.common.data.platformaccess.SynchronizeResult;
import com.yy.cloud.common.data.platformmgmt.*;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("platformaccess")
public interface PlatformAccessClient {
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
    @RequestMapping(value = "/noauth/platforms/simpleinfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<List<PlatformSimpleItem>> getAllActivePlatforms();

    @RequestMapping(value = "/noauth/platforms/{page}/{size}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralPagingResult<List<PlatformItem>> listPlatformsByPage(@PathVariable("page") Integer page,
                                                                @PathVariable("size") Integer size,
                                                                @RequestParam(value = "status", required = false) Byte _status);

    @RequestMapping(value = "/noauth/platforms/{_platformId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<PlatformItem> findPlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/noauth/platform/zone/{_zoneId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<PlatformZoneItem> getZoneById(@PathVariable("_zoneId") String _zoneId);

    @RequestMapping(value = "/noauth/platform/image/{_imageId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<PlatformAdpImageItem> getImageById(@PathVariable("_imageId") String _imageId);

    @RequestMapping(value = "/noauth/platform/storage/{_storageId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<PlatformStorageItem> getStorageById(@PathVariable("_storageId") String _storageId);

    @RequestMapping(value = "/noauth/platforms/{_platformId}/synchronizeCounts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<SynchronizeObjectInfo> synchronizeCounts(@PathVariable("_platformId") String _platformId);

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

    /**
     * 获取平台的可用区配额
     * @param _platformId
     * @return
     */
    @RequestMapping(value = "/noauth/platforms/{_platformId}/zoneQuotas", method = RequestMethod.GET)
    GeneralContentResult<List<PlatformZoneQuotaItem>> listZoneQuotas(@PathVariable("_platformId") String _platformId);

    /**
     * 获取平台的储存配额
     * @param _platformId
     * @return
     */
    @RequestMapping(value = "/noauth/platforms/{_platformId}/storageQuotas", method = RequestMethod.GET)
    GeneralContentResult<List<PlatformStorageQuotaItem>> listStorageQuotas(
            @PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/noauth/platforms/{_platformId}/flavors", method = RequestMethod.GET)
    GeneralContentResult<List<Flavor>> listFlavors(@PathVariable("_platformId") String _platformId);

    /**
     * 创建企业时判断平台配额是否足够
     * @param platformQuotaUsedProfiles
     * @return
     */
    @RequestMapping(value = "/noauth/platform/resouce/quota/validationupdate", method = RequestMethod.PUT)
    GeneralContentResult<List<PlatformQuotaUsedProfile>> CreateEnterprisePermit(
            @RequestBody List<PlatformQuotaUsedProfile> platformQuotaUsedProfiles);
    /**
     * 更新可用区与配额
     * @param _platformId
     * @param zoneQuotas
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/{_platformId}/zoneQuotas", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult modifyZoneQuotas(@PathVariable("_platformId") String _platformId,
                                   @RequestBody PlatformZoneQuotaProfile[] zoneQuotas);

    /**
     * 更新存储区与配额
     * @param _platformId
     * @param storageQuotas
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/{_platformId}/storageQuotas", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult modifyStorageQuotas(@PathVariable("_platformId") String _platformId,
                                      @RequestBody PlatformStorageQuotaProfile[] storageQuotas);

    @RequestMapping(value = "/noauth/platforms/{_platformId}/flavors", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult modifyFlavors(@PathVariable("_platformId") String _platformId, @RequestBody Flavor[] flavors);

    @RequestMapping(value = "/noauth/platforms/{_platformId}", method = RequestMethod.DELETE)
    GeneralResult deletePlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/noauth/platforms/{_platformId}/active", method = RequestMethod.GET)
    GeneralResult activePlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/noauth/platforms/{_platformId}/suspend", method = RequestMethod.GET)
    GeneralResult suspendPlatform(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/noauth/platform/{_platformId}/images", method = RequestMethod.GET)
    GeneralContentResult<List<PlatformImageItem>> getPlatformImages(@PathVariable("_platformId") String _platformId);

    @RequestMapping(value = "/noauth/platform/{_platformId}/images", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralResult updatePlatformImages(@PathVariable("_platformId") String _platformId,
                                       @RequestBody PlatformImageProfile[] _profile);

    /**
     * getVmServicePlatformDetailInfo:获取平台相关信息，为创建VM产品目录做准备. <br/>
     *
     * @return
     */
    @RequestMapping(value = "/noauth/platforms/groupinfo/vm", method = RequestMethod.POST)
    GeneralContentResult<List<VmPlatformDetailInfoResp>> getVmServicePlatformDetailInfo(@RequestBody VMServiceQueryCondition _conn);

    /**
     * getDiskServicePlatformDetailInfo:获取平台，zone 及储存等信息，为创建Disk产品目录做准备. <br/>
     *
     * @return
     */
    @RequestMapping(value = "/authsec/platforms/groupinfo/disk", method = RequestMethod.GET)
    GeneralContentResult<List<DiskPlatformDetailInfoResp>> getDiskServicePlatformDetailInfo();

    /**
     * getZoneByPlatform:获取某个平台下的所有可用区. <br/>
     *
     * @param _platformId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/noauth/platform/{_platformId}/zones")
    @ResponseBody
    GeneralPagingResult<List<ZoneItem>> getZoneByPlatform(@PathVariable("_platformId") String _platformId);

    /**
     * getPlatformResouceQuota:获取所有平台下的所有资源限额. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/noauth/platforms/resources/quotas/paging")
    @ResponseBody
    GeneralPagingResult<List<PlatformResourceQuota>> getPlatformResourceQuota(
            @RequestBody PageParameter _request);

    /**
     * getRegions:获取所有平台下的所有区域. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/noauth/platforms/regions")
    @ResponseBody
    GeneralContentResult<List<Region>> getRegions();


    @RequestMapping(value = "/noauth/platform/{_platformId}/region", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<List<Region>> getRegionByPlatformId(@PathVariable("_platformId") String _platformId);

    /**
     * getRegions:获取所有平台下的某类型的区域. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/noauth/platforms/regions/type/{_type}")
    @ResponseBody
    GeneralContentResult<List<Region>> getRegionsByType(@PathVariable("_type") Byte _type);

    @RequestMapping(value = "/noauth/regions", method = RequestMethod.POST)
    GeneralContentResult<List<RegionItem>> listRegions(@RequestBody PlatformAuth platformAuth);

    //      sprint 4 start
    /**
     * 更新平台基础信息
     * @param productItem 平台基础信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/noauth/platform",method = RequestMethod.PUT)
    public GeneralContentResult<PlatformItem> updatePlatform( @RequestBody PlatformItem productItem);

    /**
     * 云平台详细-同步可用区，获取平台添加的可用区
     * @param platformId 平台ID
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/{platformId}/zones", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<List<PlatformZoneQuotaItem>> getNewZoneList(@PathVariable("platformId") String platformId);

    /**
     * 云平台详细-同步可用区，获取可用区规格变更
     * @param zoneId 可用区ID
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/zones/{zoneId}/spec", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<PlatformZoneQuotaItem> getUpdateZoneList( @PathVariable("zoneId") String zoneId);

    /**
     * 云平台详细-同步可用区，同步新增可用区
     * @param zoneList 平台信息
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/{platformId}/zones", method = RequestMethod.POST)
    @ResponseBody
    public GeneralContentResult<List<String>> syncNewZoneList(@RequestBody List<PlatformZoneQuotaItem> zoneList);

    /**
     * 云平台详细-同步可用区，同步可用区规格变更
     * @param zone 平台信息
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/zones/spec", method = RequestMethod.PUT)
    @ResponseBody
    public GeneralContentResult<List<String>> syncUpdateZoneList(@RequestBody List<PlatformZoneQuotaItem> zone);

    /**
     * 启用可用区
     * @param zoneId
     * @return
     */
    @RequestMapping(value = "/noauth/platform/zone/{zoneId}/enable", method = RequestMethod.GET)
    GeneralResult enableZone(@PathVariable("zoneId") String zoneId);

    /**
     * 禁用可用区
     * @param zoneId
     * @return
     */
    @RequestMapping(value = "/noauth/platform/zone/{zoneId}/suspend", method = RequestMethod.GET)
    GeneralResult suspendZone(@PathVariable("zoneId") String zoneId);

    /**
     * 云平台-管理启动盘，获取平台启动盘列表
     * @param platformId
     * @return
     */
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/zones/bootstorage",method = RequestMethod.GET)
    public GeneralPagingResult<DiskServicePlatformZoneStorageReq> getPlatformBooStorage(@PathVariable("platformId") String platformId);

    /**
     * 云平台-管理启动-新建启动盘，获取可用区下的存储列表,忽略字段SKUID
     * @param zoneId
     * @return
     */
    @RequestMapping(value = "/authsec/sync/platform/zones/{zoneId}/storage",method = RequestMethod.GET)
    public GeneralPagingResult<PlatformDiskZoneStorageItem> getZoneStorage(@PathVariable("zoneId") String zoneId);

    /**
     * 云平台-管理启动-新建启动盘，保存新建启动盘
     * @param bootStorageReq
     * @return
     */
    @RequestMapping(value = "/authsec/sync/platform/zones/storage",method = RequestMethod.POST)
    public GeneralContentResult<String> createBootStorage(@RequestBody BootStorageReq bootStorageReq);

    /**
     * 云平台-管理云主机规格， 获取云主机规格列表
     * @param platformId
     * @return
     */
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/flavor", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<List<Flavor>> getFlavorList(@PathVariable("platformId") String platformId);

    /**
     * 云平台-管理云主机规格， 同步云主机
     * @param platformId
     * @return
     */
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/flavor", method = RequestMethod.POST)
    @ResponseBody
    public GeneralContentResult<List<String>> syncFlavorList(@PathVariable("platformId") String platformId);

    /**
     * 云平台-管理云主机规格， 启用云主机规格
     * @param flavorId
     * @return
     */
    @RequestMapping(value = "/authsec/platform/flavor/{flavorId}/enable", method = RequestMethod.PUT)
    GeneralResult enableFlavor(@PathVariable("flavorId") String flavorId);

    /**
     * 云平台-管理云主机规格， 禁用云主机规格
     * @param flavorId
     * @return
     */
    @RequestMapping(value = "/authsec/platform/flavor/{flavorId}/suspend", method = RequestMethod.PUT)
    GeneralResult suspendFlavor(@PathVariable("flavorId") String flavorId);

    /**
     * 云平台详细-同步存储区，获取平台添加的存储区
     * @param platformId 平台ID
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/{platformId}/storages", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<List<PlatformStorageQuotaItem>> getNewStorageList(@PathVariable("platformId") String platformId);

    /**
     * 云平台详细-同步存储区，获取存储区规格变更
     * @param storageId 存储区ID
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/storages/{storageId}/spec", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<PlatformStorageQuotaItem> getUpdateStorageList( @PathVariable("storageId") String storageId);

    /**
     * 云平台详细-同步存储区，同步新增存储区
     * @param storageList 平台信息
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/{platformId}/storages", method = RequestMethod.POST)
    @ResponseBody
    public GeneralContentResult<List<String>> syncNewStorageList(@RequestBody List<PlatformStorageQuotaItem> storageList);

    /**
     * 云平台详细-同步存储区，同步存储区规格变更
     * @param storage 平台信息
     * @return
     */
    @RequestMapping(value = "/noauth/sync/platform/storages/spec", method = RequestMethod.PUT)
    @ResponseBody
    public GeneralContentResult<List<String>> syncUpdateStorageList(@RequestBody List<PlatformStorageQuotaItem> storage);

    /**
     * 启用存储区
     * @param storageId
     * @return
     */
    @RequestMapping(value = "/noauth/platform/storage/{storageId}/enable", method = RequestMethod.GET)
    GeneralResult enableStorage(@PathVariable("storageId") String storageId);

    /**
     * 禁用存储区
     * @param storageId
     * @return
     */
    @RequestMapping(value = "/noauth/platform/storage/{storageId}/suspend", method = RequestMethod.GET)
    GeneralResult suspendStorage(@PathVariable("storageId") String storageId);

    /**
     * 校验并更新平台配额的使用量
     * @param platformQuotaUsedProfile
     * @return
     */
    @RequestMapping(value = "/noauth/platform/resouce/quota/validationupdate", method = RequestMethod.PUT)
    public GeneralContentResult<PlatformQuotaUsedProfile> validateAndUpdatePlatformUsedQuota(@RequestBody List<PlatformQuotaUsedProfile> platformQuotaUsedProfile);


}
