/**
 * Project Name:liz-admin
 * File Name:PlatformMgmgController.java
 * Package Name:com.gemii.lizcloud.api.admin.controller
 * Date:Sep 18, 20164:18:04 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gemii.lizcloud.cloudconnector.dto.PlatformAuth;
import com.gemii.lizcloud.cloudconnector.model.RegionItem;
import com.yy.cloud.api.admin.service.PlatformAccessService;
import com.yy.cloud.api.admin.service.PlatformMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformImageProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformStorageQuotaProfile;
import com.yy.cloud.common.data.dto.platformmgmt.PlatformZoneQuotaProfile;
import com.yy.cloud.common.data.dto.platformmgmt.VMServiceProfile;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformAdpImageItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformImageItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformSimpleItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformStorageQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneItem;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformZoneQuotaItem;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceItem;
import com.yy.cloud.common.data.platformaccess.SynchronizeResult;
import com.yy.cloud.common.data.platformmgmt.Flavor;
import com.yy.cloud.common.data.platformmgmt.PlatformResourceQuota;
import com.yy.cloud.common.data.platformmgmt.Region;
import com.yy.cloud.common.data.platformmgmt.ServiceSku;
import com.yy.cloud.common.data.platformmgmt.StartUpDisk;
import com.yy.cloud.common.data.platformmgmt.SynchronizeObjectInfo;
import com.yy.cloud.common.data.platformmgmt.ZoneItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:PlatformMgmgController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 18, 2016 4:18:04 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class PlatformMgmgController {

    @Autowired
    private PlatformMgmtService platformService;

    @Autowired
    private PlatformAccessService platformAccessService;

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/regions", method = RequestMethod.POST)
    public GeneralContentResult<List<RegionItem>> listRegions(
            @RequestBody PlatformAuth platformAuth) {
        return platformService.listRegions(platformAuth);
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建云平台步骤1（已完成）", notes = "Create an OpenStack platformaccess")
    public GeneralContentResult<String> createPlatform(@ApiParam(name = "_profile",
            value = "Platform's Profile", required = true) @RequestBody PlatformProfile _profile) {
        log.debug("Going to create the service {}.", _profile);

        GeneralContentResult<String> tempResult = this.platformService.createPlatform(_profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/activation", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "激活云平台（已完成）", notes = "Active an OpenStack platformaccess")
    public GeneralResult activePlatform(@PathVariable String _id) {
        log.debug("Going to active the platformaccess {}.", _id);
        GeneralResult tempResult = this.platformService.activePlatform(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/suspend", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "禁用云平台（已完成）", notes = "Active an OpenStack platformaccess")
    public GeneralResult suspendPlatform(@PathVariable String _id) {
        log.debug("Going to active the platformaccess {}.", _id);
        GeneralResult tempResult = this.platformService.suspendPlatform(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除云平台（已完成）", notes = "Delete an OpenStack platformaccess")
    public GeneralResult deletePlatform(@PathVariable String _id) {
        log.debug("Going to delete the platformaccess {}.", _id);
        GeneralResult tempResult = this.platformService.deletePlatform(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platforms/page/{_page}/size/{_size}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取所有云平台（已完成）", notes = "Get all of the platforms")
    public GeneralPagingResult<List<PlatformItem>> getAllPlatformsByPage(@PathVariable("_page") Integer _page,
                                                                         @PathVariable("_size") Integer _size,
                                                                         @RequestParam(value = "_status", required = false) Byte _status) {
        log.debug("Going to retrieve the platformaccess {}.");
        return this.platformService.getAllPlatformsByPage(_page, _size, _status);
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platforms/status/activation", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有已激活云平台, 为下拉框准备（已完成）", notes = "Get all of the platforms")
    public GeneralContentResult<List<PlatformSimpleItem>> getPlatformSimpleList() {
        log.debug("Going to retrieve the platformaccess {}.");
        return this.platformService.getPlatformSimpleList();
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个{_id}云平台（已完成）", notes = "Get someone of the platforms")
    public GeneralContentResult<PlatformItem> getOnePlatform(
            @ApiParam(required = true, name = "_id", value = "Platform's Id") @PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess {}.", _id);
        return this.platformService.getPlatformById(_id);
    }
    
    /**
     * 云平台-获取某个云平台下所有的企业
     * @param platform_id
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{platform_id}/enterprises",method = RequestMethod.GET)
    @ApiOperation(value = "云平台-获取某个云平台下所有的企业")
    public GeneralContentResult<List<EnterpriseProfile>> getEnterprisesByPlatformId(
    		@ApiParam(required = true, name = "platform_id", value = "Platform's Id") @PathVariable("platform_id") String _platformId){
    	log.debug("Getting enterprises by platform_id.");
    	return this.platformService.getEnterprisesByPlatformId(_platformId);
    }
    
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/zone/{_zoneId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个{_zoneId}可用区（已完成）", notes = "Get someone of the zones")
    public GeneralContentResult<PlatformZoneItem> getOneZone(
    		@ApiParam(required = true, name = "_zoneId", value = "Zone's Id") @PathVariable String _zoneId) {
    	log.debug("Going to retrieve the zone {}.", _zoneId);
    	return this.platformService.getZoneById(_zoneId);
    }
    
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/image/{_imageId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个{_imageId}镜像（已完成）", notes = "Get someone of the images")
    public GeneralContentResult<PlatformAdpImageItem> getOneImage(
    		@ApiParam(required = true, name = "_imageId", value = "Image's Id") @PathVariable String _imageId) {
    	log.debug("Going to retrieve the image {}.", _imageId);
    	return this.platformService.getImageById(_imageId);
    }
    
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/storage/{_storageId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个{_storageId}存储（已完成）", notes = "Get someone of the storages")
    public GeneralContentResult<PlatformStorageItem> getOneStorage(
    		@ApiParam(required = true, name = "_storageId", value = "Storage's Id") @PathVariable String _storageId) {
    	log.debug("Going to retrieve the storage {}.", _storageId);
    	return this.platformService.getStorageById(_storageId);
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/synchronizeCounts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value = "Synchronize Counts", notes = "Get total count of the synchronized platforms")
    public GeneralContentResult<SynchronizeObjectInfo> synchronizeCounts(@PathVariable("_id") String _id) {
        log.debug("Going to retrieve the counts of synchronize object within the platform id {}.", _id);
        return platformService.synchronizeCounts(_id);
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/zones/synchronize", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "同步可用区-步骤2（已完成）", notes = "Synchronize platform's Zones")
    public GeneralContentResult<SynchronizeResult> synchronizeZones(@PathVariable String _id) {
        log.debug("Synchronize the platformaccess id {} - zones.", _id);
        GeneralContentResult<SynchronizeResult> tempResult = this.platformService.synchronizeZones(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/storages/synchronize", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "同步存储-步骤2（已完成）", notes = "Synchronize platform's storage")
    public GeneralContentResult<SynchronizeResult> synchronizeStorages(@PathVariable String _id) {
        log.debug("Synchronize the platformaccess id {} - storages.", _id);
        GeneralContentResult<SynchronizeResult> tempResult = this.platformService.synchronizeStorages(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/flavors/synchronize", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "同步主机规格-步骤2（已完成）", notes = "Synchronize platform's Flaver")
    public GeneralContentResult<SynchronizeResult> synchronizeFlavors(@PathVariable String _id) {
        log.debug("Synchronize the platformaccess id {} - flavors.", _id);
        GeneralContentResult<SynchronizeResult> tempResult = this.platformService.synchronizeFlavors(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/images/synchronize", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "同步镜像-步骤2（已完成）", notes = "Synchronize platform's Image")
    public GeneralContentResult<SynchronizeResult> synchronizeImages(@PathVariable String _id) {
        log.debug("Synchronize the platformaccess id {} - images.", _id);
        GeneralContentResult<SynchronizeResult> tempResult = this.platformService.synchronizeImages(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/regions/synchronize", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "同步平台区域-步骤2（已完成）", notes = "Synchronize platform's Region")
    public GeneralContentResult<SynchronizeResult> synchronizeRegions(@PathVariable String _id) {
        log.debug("Synchronize the platformaccess id {} - regions.", _id);
        GeneralContentResult<SynchronizeResult> tempResult = this.platformService.synchronizeRegions(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/hosts/synchronize", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "同步宿主机-步骤2（已完成）", notes = "Synchronize platform's Region")
    public GeneralContentResult<SynchronizeResult> synchronizeHosts(@PathVariable String _id) {
        log.debug("Synchronize the platformaccess id {} - Host.", _id);
        GeneralContentResult<SynchronizeResult> tempResult = this.platformService.synchronizeHosts(_id);
        return tempResult;
    }

    /**
     * 获取平台的可用区配额
     * @param _id
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/zone/quota", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有可用区的Quota-步骤3/云平台详细-可用区与配额，获取平台的可用区配额(已完成)", notes = "Get all of the quota settings for some platformaccess's zones")
    public GeneralContentResult<List<PlatformZoneQuotaItem>> getZonesQuotaByPlatform(
            @ApiParam(required = true, name = "_id", value = "Platform's Id") @PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess id {} - zone's quota.", _id);
        GeneralContentResult<List<PlatformZoneQuotaItem>> tempResponse = this.platformService.getZoneQuotaByPlatformId(_id);
        return tempResponse;
    }
    
    /**
     * 更新可用区配置
     * @param _id
     * @param _profile
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/zone/quota", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "可用区配置-步骤3/云平台详细-可用区与配额，更新平台的可用区配额(已完成)", notes = "Update all of the quota settings for some platformaccess's zones")
    public GeneralResult updateZonesQuota(
            @ApiParam(required = true, name = "_id", value = "Platform's Id") @PathVariable String _id,
            @RequestBody PlatformZoneQuotaProfile[] _profile) {
        log.debug("Going to update the platformaccess id {} - zone's quota {}.", _id);
        GeneralResult tempResponse = this.platformService.updateZoneQuota(_id, _profile);
        return tempResponse;
    }

    /**
     * 获取平台的储存配额
     * @param _id
     * @param _profile
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/storage/quota", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个平台下的存储资源-步骤4/云平台详细-存储区与配额，获取平台的存储配额(已完成)", notes = "Get all of the quota settings for some platformaccess's storages")
    public GeneralContentResult<List<PlatformStorageQuotaItem>> getAllStoragesQuota(@PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess id {} - storage's quota.", _id);
        GeneralContentResult<List<PlatformStorageQuotaItem>> tempResponse = this.platformService.getStorageQuotaByPlatform(_id);
        return tempResponse;
    }

    /**
     * 更新存储区配置
     * @param _id
     * @param _profile
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/storage/quota", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "存储区配置-步骤4/云平台详细-存储区与配额，更新平台的存储配额(已完成)", notes = "Update all of the quota settings for some platformaccess's storages")
    public GeneralResult updateStoragesQuota(@PathVariable String _id,
                                             @RequestBody PlatformStorageQuotaProfile[] _profile) {
        log.debug("Going to update the platformaccess id {} - storage's quota {}.", _id);
        GeneralResult tempResponse = this.platformService.updateStorageQuota(_id, _profile);
        return tempResponse;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/flavors", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个平台的Flavor-步骤5（已完成）", notes = "Get all of the flavors of some platformaccess")
    public GeneralContentResult<List<Flavor>> getAllFlavorsInfo(@PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess id {} - falvor info.", _id);
        GeneralContentResult<List<Flavor>> tempResult = this.platformService.getFlavorsByPlatform(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/flavors", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新某个平台的Flavor-步骤5（已完成）", notes = "Update all of the flavors of some platformaccess")
    public GeneralResult updateFlavorsInfo(@PathVariable String _id, @RequestBody Flavor[] _profile) {
        log.debug("Going to update the platformaccess id {} - flavor info {}.", _id);
        GeneralResult tempResult = this.platformService.updateFlavors(_id, _profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/images", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个平台下的镜像-步骤6（已完成）", notes = "Get all of the images for some platformaccess")
    public GeneralContentResult<List<PlatformImageItem>> getPlatformImages(@PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess id {} - images.", _id);
        GeneralContentResult<List<PlatformImageItem>> tempResponse = this.platformService.getPlatformImages(_id);
        return tempResponse;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/images", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "更新某个平台下的镜像-步骤6（已完成）", notes = "Update all of the images for some platformaccess")
    public GeneralResult updatePlatformImages(@PathVariable String _id,
                                              @RequestBody PlatformImageProfile[] _profile) {
        log.debug("Going to update the platformaccess id {} - images {}.", _id);
        GeneralResult tempResponse = this.platformService.updatePlatformImages(_id, _profile);
        return tempResponse;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/service", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "Service", notes = "Update Service for some platformaccess")
    public GeneralPagingResult<GeneralContent> updateService(@PathVariable String _id, @RequestBody VMServiceProfile _profile) {
        log.debug("Going to create the service {}.", _profile);

        GeneralPagingResult<GeneralContent> tempResult = this.platformService.updateService(_profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/service", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Service", notes = "Get all of the Services for some platformaccess")
    public GeneralPagingResult<List<VMServiceProfile>> getAllServices(@PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess id {} - Services info.", _id);
        GeneralPagingResult<List<VMServiceProfile>> tempResult = this.platformService.getServicesByPlatformId(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/service/{_serviceId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Service", notes = "Get the specified Service for some platformaccess")
    public GeneralPagingResult<VMServiceProfile> getAllServicesById(@PathVariable String _id, @PathVariable String _serviceId) {
        log.debug("Going to retrieve the platformaccess id {} - service {} info.", _id, _serviceId);
        GeneralPagingResult<VMServiceProfile> tempResult = this.platformService.getServiceById(_id, _serviceId);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/regions", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get all of the Regions", notes = "Get all of the regions")
    public GeneralContentResult<List<Region>> getAllRegions() {
        log.debug("Going to retrieve all of the regions.");
        GeneralContentResult<List<Region>> tempResult = this.platformService.getAllRegion();
        return tempResult;
    }
    
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_platformId}/region", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个{_platformId}region（已完成）", notes = "Get the regions")
    public GeneralContentResult<List<Region>> getRegionByPlatformId(
    		@ApiParam(required = true, name = "_platformId", value = "Platform's Id") @PathVariable String _platformId) {
    	log.debug("Going to retrieve the region {}.", _platformId);
    	return this.platformService.getRegionByPlatformId(_platformId);
    }
    
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/regions/admin", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get all of the Admin Region", notes = "Get all of the admin regions")
    public GeneralContentResult<List<Region>> getAdminRegions() {
        log.debug("Going to retrieve all of the admin regions.");
        GeneralContentResult<List<Region>> tempResult = this.platformService.getRegionByType(CommonConstant.DIC_REGION_ADMIN);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/regions/virtual", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Get all of the Virtrual Region in Platforms", notes = "Get all of the virtual regions")
    public GeneralContentResult<List<Region>> getVirtualRegions() {
        log.debug("Going to retrieve all of the virtual regions.");
        GeneralContentResult<List<Region>> tempResult = this.platformService
                .getRegionByType(CommonConstant.DIC_REGION_VIRTAUL);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Service Item List", notes = "Get all of the Service Items")
    public GeneralPagingResult<List<ServiceItem>> getServiceItems() {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformService.getServiceItemList();
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/region/{_regionId}/services/page/{_page}/size/{_size}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Service Item List", notes = "Get all of the Service Items By Region & Paging")
    public GeneralPagingResult<List<ServiceItem>> getServiceItemsByRegionPage(
            @ApiParam(required = true, name = "_regionId", value = "Region Id") @PathVariable String _regionId,
            @ApiParam(required = true, name = "_page", value = "Page No.") @PathVariable Integer _page,
            @ApiParam(required = true, name = "_size", value = "Page Size") @PathVariable Integer _size) {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformService.getServiceItemListByRegionPaging(_regionId, _page, _size);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/region/{_regionId}/serviceitem", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Service Item List in some Region", notes = "Get all of the Service Items of some region")
    public GeneralPagingResult<List<ServiceItem>> getServiceItemByRegionId(
            @ApiParam(required = true, name = "_regionId", value = "Region Id") @PathVariable String _regionId) {
        log.debug("Going to retrieve all of the Service Items of some Region.");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformService.getServiceItemByRegion(_regionId);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/startupdisk", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Retrieve the startup disk list by Platform",
            notes = "Get all of the startup disks for some platform")
    public GeneralPagingResult<List<StartUpDisk>> getStartupDiskByPlatform(
            @ApiParam(required = true, name = "_id", value = "Platform Id") @PathVariable String _id) {
        log.debug("Going to retrieve the platformaccess id {} - Services info.", _id);
        GeneralPagingResult<List<StartUpDisk>> tempResult = this.platformService.getStartupDisk(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platforms/resouces/quotas/page/{_page}/size/{_size}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有已激活平台的资源配额（已完成）", notes = "Get all of the resource quotas")
    public GeneralPagingResult<List<PlatformResourceQuota>> getAllPlatformResourceQuota(
            @ApiParam(required = true, name = "_page", value = "Page No.") @PathVariable Integer _page,
            @ApiParam(required = true, name = "_size", value = "Page Size.") @PathVariable Integer _size) {
        log.debug("Going to retrieve the platformaccess id {} - Services info.");
        GeneralPagingResult<List<PlatformResourceQuota>> tempResult = this.platformService.getPlatformResourceQuota(_page,
                _size);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/service/{_serviceId}/sku/page/{_page}/size/{_size}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "Retrieve all of the Service SKU of some Service",
            notes = "Get the Sku list for some Service")
    public GeneralPagingResult<List<ServiceSku>> getServiceSKU(
            @ApiParam(required = true, name = "_serviceId", value = "Service Id") @PathVariable String _serviceId,
            @ApiParam(required = true, name = "_page", value = "Page No.") @PathVariable Integer _page,
            @ApiParam(required = true, name = "_size", value = "Page Size") @PathVariable Integer _size) {
        log.debug("Going to retrieve the platformaccess id {} - Services info.");
        GeneralPagingResult<List<ServiceSku>> tempResult = this.platformService.getServiceSKUByService(_serviceId, _page,
                _size);
        return tempResult;
    }

    /**
     * getZoneByPlatform:获取某个平台下的所有可用区. <br/>
     *
     * @param _id 平台Id
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/{_id}/zone", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个平台下的所有可用区（已完成）", notes = "获取某个平台下的所有可用区")
    public GeneralPagingResult<List<ZoneItem>> getZoneByPlatform(
            @ApiParam(required = true, name = "_id", value = "Platform Id") @PathVariable String _id) {
        //todo check guofeng implement
        log.debug("Going to retrieve the platformaccess id {} - Zone info.", _id);
        GeneralPagingResult<List<ZoneItem>> tempResult = this.platformService.getZoneByPlatform(_id);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform",method = RequestMethod.PUT)
    @ApiOperation(value = "更新平台基础信息(未完成)")
    public GeneralContentResult<PlatformItem> updatePlatform(@ApiParam("平台基础信息") @RequestBody PlatformItem platformItem){
        return platformAccessService.updatePlatform(platformItem);
    }

    /**
     * 云平台-管理云主机规格，启用云主机规格
     * @param flavorId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/flavor/{flavorId}/enable", method = RequestMethod.PUT)
    @ApiOperation(value = "云平台-管理云主机规格，启用云主机规格")
    public GeneralResult enableFlavor(@PathVariable("flavorId") String flavorId){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，启用云主机规格, flavorId:{}", flavorId);
        GeneralResult tempResult = this.platformAccessService.enableFlavor(flavorId);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，启用云主机规格结果：{}", tempResult);
        return tempResult;
    }

    /**
     * 云平台-管理云主机规格，禁用云主机规格
     * @param flavorId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/flavor/{flavorId}/suspend", method = RequestMethod.PUT)
    @ApiOperation(value = "云平台-管理云主机规格，禁用云主机规格")
    public GeneralResult suspendFlavor(@PathVariable("flavorId") String flavorId){
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，禁用云主机规格, flavorId:{}", flavorId);
        GeneralResult tempResult = this.platformAccessService.suspendFlavor(flavorId);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "云平台-管理云主机规格，禁用云主机规格结果：{}", tempResult);
        return tempResult;
    }
    
    /**
     * 校验并更新平台配额的使用量
     * @param platformQuotaUsedProfile
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/platform/resouce/quota/validationupdate", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "校验并更新平台的配额使用量（已完成）", notes = "Validate and update resource quota for platform.")
    public GeneralContentResult<PlatformQuotaUsedProfile> validateAndUpdatePlatformUsedQuota(@RequestBody List<PlatformQuotaUsedProfile> platformQuotaUsedProfile){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"校验并更新平台已使用配额 {}",platformQuotaUsedProfile);
        return this.platformAccessService.validateAndUpdatePlatformUsedQuota(platformQuotaUsedProfile);
    }
    
}
