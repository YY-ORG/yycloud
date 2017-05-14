/**
 * Project Name:liz-admin
 * File Name:PlatformMgmtService.java
 * Package Name:com.gemii.lizcloud.api.admin.service
 * Date:Sep 18, 20164:21:36 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.service;

import java.util.List;

import com.gemii.lizcloud.cloudconnector.dto.PlatformAuth;
import com.gemii.lizcloud.cloudconnector.model.RegionItem;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.dto.enterprise.ServiceQueryCondition;
import com.yy.cloud.common.data.dto.enterprise.VMServiceQueryCondition;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformImageProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformStorageQuotaProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformZoneQuotaProfile;
import com.yy.cloud.common.data.dto.platformmgmt.VMServiceProfile;
import com.yy.cloud.common.data.otd.enterprise.VMServiceDetailInfoItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformAdpImageItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformImageItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformSimpleItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceItem;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceSimpleItem;
import com.yy.cloud.common.data.otd.platformmgmt.VmPlatformDetailInfoResp;
import com.yy.cloud.common.data.platformaccess.SynchronizeResult;
import com.yy.cloud.common.data.platformmgmt.Flavor;
import com.yy.cloud.common.data.platformmgmt.PlatformResourceQuota;
import com.yy.cloud.common.data.platformmgmt.Region;
import com.yy.cloud.common.data.platformmgmt.ServiceSku;
import com.yy.cloud.common.data.platformmgmt.ServiceTemplateItem;
import com.yy.cloud.common.data.platformmgmt.StartUpDisk;
import com.yy.cloud.common.data.platformmgmt.SynchronizeObjectInfo;
import com.yy.cloud.common.data.platformmgmt.ZoneItem;

/**
 * ClassName:PlatformMgmtService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 18, 2016 4:21:36 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface PlatformMgmtService {

    GeneralContentResult<String> createPlatform(PlatformProfile _profile);

    GeneralPagingResult<List<PlatformItem>> getAllPlatformsByPage(Integer _page, Integer _size, Byte _status);

    /**
     * getAllPlatforms: 获取所有已激活的平台. <br/>
     *
     * @return
     */
    GeneralContentResult<List<PlatformSimpleItem>> getPlatformSimpleList();

    GeneralContentResult<PlatformItem> getPlatformById(String _id);
    
    GeneralContentResult<PlatformZoneItem> getZoneById(String _id);
    
    GeneralContentResult<PlatformAdpImageItem> getImageById(String _id);
    
    GeneralContentResult<PlatformStorageItem> getStorageById(String _id);

    GeneralContentResult<List<PlatformZoneQuotaItem>> getZoneQuotaByPlatformId(String _id);

    GeneralContentResult<SynchronizeObjectInfo> synchronizeCounts(String _platformId);
    
    GeneralContentResult<List<EnterpriseProfile>> getEnterprisesByPlatformId(String _platformId);

    /**
     * synchronizeZones:同步可用区. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<SynchronizeResult> synchronizeZones(String _platformId);

    /**
     * synchronizeStorages:同步存储. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<SynchronizeResult> synchronizeStorages(String _platformId);

    /**
     * synchronizeFlavors:同步主机规格. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<SynchronizeResult> synchronizeFlavors(String _platformId);

    /**
     * synchronizeImages:同步镜像. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<SynchronizeResult> synchronizeImages(String _platformId);

    /**
     * synchronizeRegions:同步区域. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<SynchronizeResult> synchronizeRegions(String _platformId);


    /**
     * synchronizeHosts:同步宿主机. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<SynchronizeResult> synchronizeHosts(String _platformId);

    /**
     * deletePlatform:(Description). <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralResult deletePlatform(String _platformId);

    /**
     * activePlatform:(Description). <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralResult activePlatform(String _platformId);

    /**
     * suspendPlatform:(Description). <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralResult suspendPlatform(String _platformId);

    /**
     * updateZoneQuota: 获取可用区资源配额. <br/>
     *
     * @param _platformId
     * @param _profile
     * @return
     */
    GeneralResult updateZoneQuota(String _platformId, PlatformZoneQuotaProfile[] _profile);

    /**
     * getStorageQuotaByPlatform: 获取存储区资源配额. <br/>
     *
     * @param _id
     * @return
     */
    GeneralContentResult<List<PlatformStorageQuotaItem>> getStorageQuotaByPlatform(String _id);

    /**
     * updateStorageQuota: 更新存储区资源配额. <br/>
     *
     * @param _platformId
     * @param _profile
     * @return
     */
    GeneralResult updateStorageQuota(String _platformId, PlatformStorageQuotaProfile[] _profile);

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * getPlatformImages: 获取某个平台下的所有镜像. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<List<PlatformImageItem>> getPlatformImages(String _platformId);

    /**
     * updatePlatformImages: 更新某个平台下的镜像. <br/>
     *
     * @param _platformId
     * @param _profile
     * @return
     */
    GeneralResult updatePlatformImages(String _platformId, PlatformImageProfile[] _profile);

    /**
     * getFlavorsByPlatformId:(Description). <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralContentResult<List<Flavor>> getFlavorsByPlatform(String _platformId);

    /**
     * updateFlavors:(Description). <br/>
     *
     * @param _profile
     * @return
     */
    GeneralResult updateFlavors(String _platformId, Flavor[] _profile);


    /**
     * createVmService:创建VM服务目录. <br/>
     *
     * @param _profile
     * @return
     */
    GeneralResult createVMService(VMServiceProfile _profile);


    /**
     * updateService:(Description). <br/>
     *
     * @param _profile
     * @return
     */
    GeneralPagingResult<GeneralContent> updateService(VMServiceProfile _profile);

    /**
     * getServicesByPlatformId:(Description). <br/>
     *
     * @param _id
     * @return
     */
    GeneralPagingResult<List<VMServiceProfile>> getServicesByPlatformId(String _id);

    /**
     * getZoneByPlatformId:获取平台下的可用区. <br/>
     *
     * @param _id
     * @return
     */
    GeneralPagingResult<List<ZoneItem>> getZoneByPlatform(String _id);

    /**
     * getServiceById:(Description). <br/>
     *
     * @param _platformId
     * @param _serviceId
     * @return
     */
    GeneralPagingResult<VMServiceProfile> getServiceById(String _platformId, String _serviceId);

    /**
     * getServiceItemList:获取服务列表. <br/>
     *
     * @return
     */
    GeneralPagingResult<List<ServiceItem>> getServiceItemList();

    /**
     * getServiceDetailInfoItemList: 获取VM产品目录详情. <br/>
     *
     * @param _serviceId
     * @return
     */
    GeneralContentResult<VMServiceDetailInfoItem> getVMServiceDetailInfoItemList(String _serviceId);

    /**
     * getServiceSimpleItemList:获取所有的服务目录，为下拉框准备. <br/>
     *
     * @return
     */
    GeneralContentResult<List<ServiceSimpleItem>> getServiceSimpleItemList();

    /**
     * getServiceItemListByPage:分页获取服务目录列表. <br/>
     *
     * @return
     */
    GeneralPagingResult<List<ServiceItem>> getServiceItemListByPage(Integer _page, Integer _size);

    /**
     * getServiceItemListByConnPage:条件检索所有服务目录. <br/>
     *
     * @return
     */
    GeneralPagingResult<List<ServiceItem>> getServiceItemListByConnPage(Integer _page, Integer _size,
                                                                        ServiceQueryCondition _conn);

    /**
     * getServiceItemListByRegionPaging:分页获取某个平台下的服务目录列表. <br/>
     *
     * @param _regionId
     * @param _page
     * @param _size
     * @return
     */
    GeneralPagingResult<List<ServiceItem>> getServiceItemListByRegionPaging(String _regionId, Integer _page,
                                                                            Integer _size);

    /**
     * deleteServiceById:删除服务. <br/>
     *
     * @param _serviceId
     * @return
     */
    GeneralPagingResult<GeneralContent> deleteService(String _serviceId);

    /**
     * getServiceItemListByRegion:获取某个区域下的服务列表. <br/>
     *
     * @param _regionId
     * @return
     */
    GeneralPagingResult<List<ServiceItem>> getServiceItemByRegion(String _regionId);

    /**
     * getServiceTemplateItemList:获取服务模板列表. <br/>
     *
     * @return
     */
    GeneralContentResult<List<ServiceTemplateItem>> getServiceTemplateItemList(Byte status);

    /**
     * getAllRegion:(Description). <br/>
     *
     * @return
     */
    GeneralContentResult<List<Region>> getAllRegion();

    GeneralContentResult<List<Region>> getRegionByPlatformId(String _id);

    /**
     * getRegionByType:(Description). <br/>
     *
     * @param _type
     * @return
     */
    GeneralContentResult<List<Region>> getRegionByType(Byte _type);

    /**
     * getStartupDisk:获取某个平台下的存储. <br/>
     *
     * @param _platformId
     * @return
     */
    GeneralPagingResult<List<StartUpDisk>> getStartupDisk(String _platformId);

    /**
     * updateServiceStatus:更新Service状态. <br/>
     *
     * @param _serviceId
     * @param _status
     * @return
     */
    GeneralResult updateServiceStatus(String _serviceId, Byte _status);

    /**
     * getVmServicePlatformDetailInfo: 获取平台服务相关信息，用于VM服务目录创建. <br/>
     *
     * @param _conn
     * @return
     */
    GeneralContentResult<List<VmPlatformDetailInfoResp>> getVmServicePlatformDetailInfo(VMServiceQueryCondition _conn);


    /**
     * getPlatformResouceQuota:获取所有平台的资源配额. <br/>
     *
     * @param _page
     * @param _size
     * @return
     */
    GeneralPagingResult<List<PlatformResourceQuota>> getPlatformResourceQuota(Integer _page, Integer _size);

    /**
     * getServiceSKUByService:根据Service来检索SKU. <br/>
     *
     * @param _serviceId
     * @param _page
     * @param _size
     * @return
     */
    GeneralPagingResult<List<ServiceSku>> getServiceSKUByService(String _serviceId, Integer _page,
                                                                 Integer _size);

    GeneralPagingResult<List<PlatformResourceQuota>> getPlatformResourceQuota(PageParameter pageParameter);

    GeneralContentResult<List<RegionItem>> listRegions(PlatformAuth platformAuth);

}
