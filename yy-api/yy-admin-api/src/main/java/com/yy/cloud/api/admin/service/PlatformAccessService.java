package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneQuotaItem;
import com.yy.cloud.common.data.platformmgmt.Flavor;

/**
 * Created by chenluo on 2016/11/30.
 */
public interface PlatformAccessService {

    GeneralContentResult<PlatformItem> updatePlatform(PlatformItem productItem);

    GeneralContentResult<List<PlatformZoneQuotaItem>> getNewZoneList(String platformId);

    GeneralContentResult<PlatformZoneQuotaItem> getUpdateZoneList( String zoneId);

    GeneralContentResult<List<String>> syncNewZoneList( List<PlatformZoneQuotaItem> zoneList);

    GeneralContentResult<List<String>> syncUpdateZoneList(List<PlatformZoneQuotaItem> zone);

    GeneralResult enableZone( String zoneId);
    
    GeneralResult suspendZone( String zoneId);

    /**
     * 云平台-管理云主机规格， 获取云主机规格列表
     * @param platformId
     * @return
     */
    GeneralContentResult<List<Flavor>> getFlavorList(String platformId);

    /**
     * 云平台-管理云主机规格， 同步云主机规格
     * @param platformId
     * @return
     */
    GeneralContentResult<List<String>> syncFlavorList(String platformId);

    /**
     * 云平台-管理云主机规格， 启用云主机规格
     * @param flavorId
     * @return
     */
    GeneralResult enableFlavor(String flavorId);

    /**
     * 云平台-管理云主机规格， 禁用云主机规格
     * @param flavorId
     * @return
     */
    GeneralResult suspendFlavor(String flavorId);
    
    GeneralContentResult<List<PlatformStorageQuotaItem>> getNewStorageList(String platformId);

    GeneralContentResult<PlatformStorageQuotaItem> getUpdateStorageList( String storageId);

    GeneralContentResult<List<String>> syncNewStorageList( List<PlatformStorageQuotaItem> storageList);

    GeneralContentResult<List<String>> syncUpdateStorageList(List<PlatformStorageQuotaItem> storage);

    GeneralResult enableStorage( String storageId);
    
    GeneralResult suspendStorage( String storageId);
    
    GeneralContentResult<PlatformQuotaUsedProfile> validateAndUpdatePlatformUsedQuota(List<PlatformQuotaUsedProfile> platformQuotaUsedProfile);

}
