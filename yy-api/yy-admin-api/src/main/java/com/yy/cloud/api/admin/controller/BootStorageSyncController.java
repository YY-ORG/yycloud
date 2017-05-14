package com.yy.cloud.api.admin.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.platformmgmt.DiskServicePlatformZoneStorageReq;
import com.yy.cloud.common.data.otd.platformmgmt.BootStorageReq;
import com.yy.cloud.common.data.otd.platformmgmt.PlatformDiskZoneStorageItem;

/**
 * Created by chenluo on 2016/12/1.
 */
@RestController
@Slf4j
public class BootStorageSyncController {
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/{platformId}/zones/bootstorage",method = RequestMethod.GET)
    @ApiOperation(value = "云平台-管理启动盘，获取平台启动盘列表(开发中)")
    public GeneralPagingResult<DiskServicePlatformZoneStorageReq> getPlatformBooStorage(@PathVariable("platformId") String platformId){
        return null;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/zones/{zoneId}/storage",method = RequestMethod.GET)
    @ApiOperation(value = "云平台-管理启动-新建启动盘，获取可用区下的存储列表,忽略字段SKUID(开发中)")
    public GeneralPagingResult<PlatformDiskZoneStorageItem> getZoneStorage(@PathVariable("zoneId") String zoneId){
        return null;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/sync/platform/zones/storage",method = RequestMethod.POST)
    @ApiOperation(value = "云平台-管理启动-新建启动盘，保存新建启动盘(开发中)")
    public GeneralContentResult<String> createBootStorage(@ApiParam("启动盘信息") @RequestBody BootStorageReq bootStorageReq){
        return null;
    }
}
