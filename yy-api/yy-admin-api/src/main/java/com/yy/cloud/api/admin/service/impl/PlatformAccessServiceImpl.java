package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.yy.cloud.api.admin.clients.PlatformAccessClient;
import com.yy.cloud.api.admin.service.PlatformAccessService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneQuotaItem;
import com.yy.cloud.common.data.platformmgmt.Flavor;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenluo on 2016/11/30.
 */
@Service
@Slf4j
public class PlatformAccessServiceImpl implements PlatformAccessService {

    @Autowired
    private PlatformAccessClient platformAccessClient;
    @Override
    public GeneralContentResult<PlatformItem> updatePlatform(PlatformItem platformItem) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"更新平台信息为 {}",platformItem);
        return platformAccessClient.updatePlatform(platformItem);
    }

    @Override
    public GeneralContentResult<List<PlatformZoneQuotaItem>> getNewZoneList(@PathVariable("platformId") String platformId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台添加的可用区",platformId);
        GeneralContentResult<List<PlatformZoneQuotaItem>> result= platformAccessClient.getNewZoneList(platformId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台可用区返回结果为 {}",platformId, result);
        return result;
    }

    @Override
    public GeneralContentResult<PlatformZoneQuotaItem> getUpdateZoneList(String zoneId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取可用区 {} 配置变更",zoneId);
        GeneralContentResult<PlatformZoneQuotaItem> result= platformAccessClient.getUpdateZoneList(zoneId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"变更数据为 {}",result);
        return result;
    }
    
    @Override
    public GeneralContentResult<List<String>> syncNewZoneList(@RequestBody List<PlatformZoneQuotaItem> zoneList) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台添加的可用区",zoneList);
        GeneralContentResult<List<String>> result= platformAccessClient.syncNewZoneList(zoneList);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台可用区返回结果为 {}",result);
        return result;
    }

    @Override
    public GeneralContentResult<List<String>> syncUpdateZoneList(List<PlatformZoneQuotaItem> zone) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"更新可用区的配置 {}",zone);
        GeneralContentResult<List<String>> result= platformAccessClient.syncUpdateZoneList(zone);
        return result;
    }

    @Override
    public GeneralResult enableZone(String zoneId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"启用可用区{}",zoneId);
        GeneralResult tempResult = platformAccessClient.enableZone(zoneId);
        return tempResult;
    }
    
    @Override
    public GeneralResult suspendZone(String zoneId) {
    	log.debug(CommonConstant.LOG_DEBUG_TAG+"禁用可用区{}",zoneId);
        GeneralResult tempResult = this.platformAccessClient.suspendZone(zoneId);
        return tempResult;
    }

    /**
     * 云平台-管理云主机规格， 获取云主机规格列表
     * @param platformId
     * @return
     */
    @Override
    public GeneralContentResult<List<Flavor>> getFlavorList(String platformId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"云平台-管理云主机规格， 获取云主机规格列表, platformId:{}",platformId);
        return platformAccessClient.getFlavorList(platformId);
    }

    /**
     * 云平台-管理云主机规格， 同步云主机规格
     * @param platformId
     * @return
     */
    @Override
    public GeneralContentResult<List<String>> syncFlavorList(String platformId){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格， 同步云主机规格, platformId:{}" + platformId);
        return platformAccessClient.syncFlavorList(platformId);
    }

    /**
     * 云平台-管理云主机规格， 启用云主机规格
     * @param flavorId
     * @return
     */
    @Override
    public GeneralResult enableFlavor(String flavorId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格， 启用云主机规格, flavorId:{}" + flavorId);
        return platformAccessClient.enableFlavor(flavorId);
    }

    /**
     * 云平台-管理云主机规格， 禁用云主机规格
     * @param flavorId
     * @return
     */
    @Override
    public GeneralResult suspendFlavor(String flavorId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格， 禁用云主机规格, flavorId:{}" + flavorId);
        return platformAccessClient.suspendFlavor(flavorId);
    }
    
    @Override
    public GeneralContentResult<List<PlatformStorageQuotaItem>> getNewStorageList(@PathVariable("platformId") String platformId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台添加的存储区",platformId);
        GeneralContentResult<List<PlatformStorageQuotaItem>> result= platformAccessClient.getNewStorageList(platformId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台存储区返回结果为 {}",platformId, result);
        return result;
    }

    @Override
    public GeneralContentResult<PlatformStorageQuotaItem> getUpdateStorageList(String storageId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取存储区 {} 配置变更",storageId);
        GeneralContentResult<PlatformStorageQuotaItem> result= platformAccessClient.getUpdateStorageList(storageId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"变更数据为 {}",result);
        return result;
    }
    
    @Override
    public GeneralContentResult<List<String>> syncNewStorageList(@RequestBody List<PlatformStorageQuotaItem> storageList) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台添加的存储区",storageList);
        GeneralContentResult<List<String>> result= platformAccessClient.syncNewStorageList(storageList);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"同步平台存储区返回结果为 {}",result);
        return result;
    }

    @Override
    public GeneralContentResult<List<String>> syncUpdateStorageList(List<PlatformStorageQuotaItem> storage) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"更新存储区的配置 {}",storage);
        GeneralContentResult<List<String>> result= platformAccessClient.syncUpdateStorageList(storage);
        return result;
    }

    @Override
    public GeneralResult enableStorage(String storageId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"启用存储区{}",storageId);
        GeneralResult tempResult = platformAccessClient.enableStorage(storageId);
        return tempResult;
    }
    
    @Override
    public GeneralResult suspendStorage(String storageId) {
    	log.debug(CommonConstant.LOG_DEBUG_TAG+"禁用存储区{}",storageId);
        GeneralResult tempResult = this.platformAccessClient.suspendStorage(storageId);
        return tempResult;
    }

    @Override
    public GeneralContentResult<PlatformQuotaUsedProfile> validateAndUpdatePlatformUsedQuota(List<PlatformQuotaUsedProfile> platformQuotaUsedProfile) {
        return platformAccessClient.validateAndUpdatePlatformUsedQuota(platformQuotaUsedProfile);
    }
}
