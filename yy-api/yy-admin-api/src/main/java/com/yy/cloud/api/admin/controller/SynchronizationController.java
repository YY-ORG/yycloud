package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.api.admin.service.PlatformAccessService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneQuotaItem;
import com.yy.cloud.common.data.platformmgmt.Flavor;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenluo on 2016/11/29.
 */
@RestController
@Slf4j
public class SynchronizationController {

    @Autowired
    private PlatformAccessService platformAccessService;

    /**
     * 获取平台添加的可用区
     * @param platformId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/zones", method = RequestMethod.GET)
    @ApiOperation(value = "云平台详细-同步可用区，获取平台添加的可用区")
    public GeneralContentResult<List<PlatformZoneQuotaItem>> getNewZoneList(@ApiParam("平台ID") @PathVariable("platformId") String platformId){
        return platformAccessService.getNewZoneList(platformId);
    }

    /**
     * 获取可用区规格变更
     * @param zoneId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/zones/{zoneId}/spec", method = RequestMethod.GET)
    @ApiOperation(value = "云平台详细-同步可用区，获取可用区规格变更")
    public GeneralContentResult<PlatformZoneQuotaItem> getUpdateZoneList(@ApiParam("可用区 id") @PathVariable("zoneId") String zoneId){
        return platformAccessService.getUpdateZoneList(zoneId);
    }
    
    /**
     * 同步新增可用区
     * @param zoneList
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/zones", method = RequestMethod.POST)
    @ApiOperation(value = "云平台详细-同步可用区，同步可用区-新增")
    public GeneralContentResult<List<String>> syncNewZoneList(@ApiParam("平台信息") @RequestBody List<PlatformZoneQuotaItem> zoneList){
        return platformAccessService.syncNewZoneList(zoneList);
    }

    /**
     * 同步变更可用区
     * @param zone
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/zones/spec", method = RequestMethod.PUT)
    @ApiOperation(value = "云平台详细-同步可用区，同步可用区规格变更")
    public GeneralContentResult<List<String>> syncUpdateZoneList(@ApiParam("可用区信息") @RequestBody List<PlatformZoneQuotaItem> zone){
        return platformAccessService.syncUpdateZoneList(zone);
    }

    /**
     * 启用可用区
     * @param zoneId
     * @param status
     * @return
     */
    @RequestMapping(value = "/authsec/platform/zone/{zoneId}/enable", method = RequestMethod.PUT)
    @ApiOperation(value = "启用可用区(未完成)", notes = "Active an OpenStack platform zone")
    public GeneralResult enableZone(@PathVariable("zoneId") String zoneId){
        log.debug("Going to enable the zone {}.", zoneId);
        GeneralResult tempResult = this.platformAccessService.enableZone(zoneId);
        return tempResult;
    }
    
    /**
     * 禁用可用区
     * @param zoneId
     * @param status
     * @return
     */
    @RequestMapping(value = "/authsec/platform/zone/{zoneId}/suspend", method = RequestMethod.PUT)
    @ApiOperation(value = "禁用可用区(未完成)", notes = "Suspend an OpenStack platform zone")
    public GeneralResult suspendZone(@PathVariable("zoneId") String zoneId){
        log.debug("Going to suspend the zone {}.", zoneId);
        GeneralResult tempResult = this.platformAccessService.suspendZone(zoneId);
        return tempResult;
    }
    
    /**
     * 云平台-管理云主机规格，获取云主机规格列表
     * @param platformId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/flavor", method = RequestMethod.GET)
    @ApiOperation(value = "云平台详细-管理云主机规格，获取云主机规格")
    public GeneralContentResult<List<Flavor>> getFlavorList(@ApiParam("平台 id") @PathVariable("platformId") String platformId){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，获取云主机规格列表，platformId:{}", platformId);
        GeneralContentResult<List<Flavor>> result = platformAccessService.getFlavorList(platformId);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，获取云主机规格列表结果：{}", result);
        return result;
    }

    /**
     * 云平台-管理云主机规格，同步云主机规格列表
     * @param platformId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/flavor", method = RequestMethod.POST)
    @ApiOperation(value = "云平台详细-管理云主机规格，同步云主机规格")
    public GeneralContentResult<List<String>> syncFlavorList(@ApiParam("平台 id") @PathVariable("platformId") String platformId){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，同步云主机规格列表，platformId:{}", platformId);
        GeneralContentResult<List<String>> result = platformAccessService.syncFlavorList(platformId);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，同步云主机规格列表结果:{}", result);
        return result;
    }

    /**
     * 获取平台添加的存储区
     * @param platformId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/storages", method = RequestMethod.GET)
    @ApiOperation(value = "云平台详细-同步存储区，获取平台添加的存储区")
    public GeneralContentResult<List<PlatformStorageQuotaItem>> getNewStorageList(@ApiParam("平台ID") @PathVariable("platformId") String platformId){
        return platformAccessService.getNewStorageList(platformId);
    }

    /**
     * 获取存储区规格变更
     * @param storageId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/storages/{storageId}/spec", method = RequestMethod.GET)
    @ApiOperation(value = "云平台详细-同步存储区，获取存储区规格变更")
    public GeneralContentResult<PlatformStorageQuotaItem> getUpdateStorageList(@ApiParam("存储区 id") @PathVariable("storageId") String storageId){
        return platformAccessService.getUpdateStorageList(storageId);
    }
    
    /**
     * 同步新增存储区
     * @param storageList
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/storages", method = RequestMethod.POST)
    @ApiOperation(value = "云平台详细-同步存储区，同步存储区-新增")
    public GeneralContentResult<List<String>> syncNewStorageList(@ApiParam("平台信息") @RequestBody List<PlatformStorageQuotaItem> storageList){
        return platformAccessService.syncNewStorageList(storageList);
    }

    /**
     * 同步变更存储区
     * @param storage
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/storages/spec", method = RequestMethod.PUT)
    @ApiOperation(value = "云平台详细-同步存储区，同步存储区规格变更")
    public GeneralContentResult<List<String>> syncUpdateStorageList(@ApiParam("存储区信息") @RequestBody List<PlatformStorageQuotaItem> storage){
        return platformAccessService.syncUpdateStorageList(storage);
    }

    /**
     * 启用存储区
     * @param storageId
     * @param status
     * @return
     */
    @RequestMapping(value = "/authsec/platform/storage/{storageId}/enable", method = RequestMethod.PUT)
    @ApiOperation(value = "启用存储区(未完成)", notes = "Active an OpenStack platform storage")
    public GeneralResult enableStorage(@PathVariable("storageId") String storageId){
        log.debug("Going to enable the storage {}.", storageId);
        GeneralResult tempResult = this.platformAccessService.enableStorage(storageId);
        return tempResult;
    }
    
    /**
     * 禁用存储区
     * @param storageId
     * @param status
     * @return
     */
    @RequestMapping(value = "/authsec/platform/storage/{storageId}/suspend", method = RequestMethod.PUT)
    @ApiOperation(value = "禁用存储区(未完成)", notes = "Suspend an OpenStack platform storage")
    public GeneralResult suspendStorage(@PathVariable("storageId") String storageId){
        log.debug("Going to suspend the storage {}.", storageId);
        GeneralResult tempResult = this.platformAccessService.suspendStorage(storageId);
        return tempResult;
    }
}
