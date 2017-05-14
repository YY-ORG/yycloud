package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.PlatformMgmtService;
import com.yy.cloud.api.admin.service.ProductTemplateService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.ServicePageQueryCondition;
import com.yy.cloud.common.data.dto.enterprise.ServiceQueryCondition;
import com.yy.cloud.common.data.dto.enterprise.VMServiceQueryCondition;
import com.yy.cloud.common.data.dto.platformmgmt.AdsServiceTemplateReq;
import com.yy.cloud.common.data.dto.platformmgmt.DiskServiceProfile;
import com.yy.cloud.common.data.dto.platformmgmt.DiskServiceProfileRep;
import com.yy.cloud.common.data.dto.platformmgmt.VMServiceProfile;
import com.yy.cloud.common.data.otd.enterprise.VMServiceDetailInfoItem;
import com.yy.cloud.common.data.otd.platformmgmt.DiskPlatformDetailInfoResp;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceItem;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceSimpleItem;
import com.yy.cloud.common.data.otd.platformmgmt.VmPlatformDetailInfoResp;
import com.yy.cloud.common.data.platformmgmt.ServiceTemplateItem;
import com.yy.cloud.common.data.platformmgmt.ZoneItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 10/31/2016.
 */
@RestController
@Slf4j
public class ProductTemplateController {

    @Autowired
    private PlatformMgmtService platformService;

    @Autowired
    private ProductTemplateService productTemplateService;

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/type/vm", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建VM产品目录（带联调）", notes = "Create VM Services")
    public GeneralResult createVMService(@RequestBody VMServiceProfile _profile) {
        log.debug("Going to create VM services {}.", _profile);
        GeneralResult tempResult = this.productTemplateService.createVMService(_profile);
        return tempResult;
    }



    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/service/{_serviceId}/type/vm/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个VM产品目录详细信息（已完成）", notes = "Get all of the Service Items")
    public GeneralContentResult<VMServiceDetailInfoItem> getVMServiceDetailItems(
            @ApiParam(required = true, name = "_serviceId", value = "产品目录Id.") @PathVariable String _serviceId) {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralContentResult<VMServiceDetailInfoItem> tempResult = this.platformService.getVMServiceDetailInfoItemList(_serviceId);
        return tempResult;
    }



    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/simple", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有产品目录，为下拉框准备（已完成）", notes = "Get all of the Service Items")
    public GeneralContentResult<List<ServiceSimpleItem>> getServiceSimpleItems() {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralContentResult<List<ServiceSimpleItem>> tempResult = this.platformService.getServiceSimpleItemList();
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/page/{_page}/size/{_size}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取所有产品目录（已完成）", notes = "Get all of the Service Items By Paging")
    public GeneralPagingResult<List<ServiceItem>> getServiceItemsByPage(
            @ApiParam(required = true, name = "_page", value = "Page No.") @PathVariable Integer _page,
            @ApiParam(required = true, name = "_size", value = "Page Size") @PathVariable Integer _size) {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformService.getServiceItemListByPage(_page, _size);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/pagging", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "条件检索产品目录（已完成）", notes = "Get all of the Service Items By Paging")
    public GeneralPagingResult<List<ServiceItem>> getServiceItemsByCondition(
            @ApiParam(required = true, name = "_conn", value = "Condition") @RequestBody ServicePageQueryCondition _conn) {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.productTemplateService.getServiceItemsByCondition( _conn);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/service/{_serviceId}/status/activation", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "发布产品目录（已完成）", notes = "Publish Service Item")
    public GeneralResult publishServiceItem(
            @ApiParam(required = true, name = "_serviceId", value = "Service Id") @PathVariable String _serviceId) {
        log.debug("Going to retrieve all of the Service Items.");
        GeneralResult tempResult = this.platformService.updateServiceStatus(_serviceId, CommonConstant.DIC_SERVICE_STATUS_ACTIVE);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/service/{_serviceId}/status/cancelled", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "取消发布产品目录（已完成）", notes = "Cancel Service Item")
    public GeneralResult cancelServiceItem(
            @ApiParam(required = true, name = "_serviceId", value = "Service Id") @PathVariable String _serviceId) {
        log.debug("Going to cancel Service Item {}.", _serviceId);
        GeneralResult tempResult = this.platformService.updateServiceStatus(_serviceId, CommonConstant.DIC_SERVICE_STATUS_CANCELLED);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/service/{_serviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除产品目录（已完成）", notes = "Delete Service Item")
    public GeneralResult deleteServiceItem(
            @ApiParam(required = true, name = "_serviceId", value = "Service Id") @PathVariable String _serviceId) {
        log.debug("Going to cancel Service Item {}.", _serviceId);
        GeneralResult tempResult = this.platformService.updateServiceStatus(_serviceId, CommonConstant.DIC_SERVICE_STATUS_DELETED);
        return tempResult;
    }


    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/platforms/vm/detailinfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取VM产品服务（目录）平台，可用区，储存详情列表（已完成）", notes = "Get Platform Service detail info.")
    public GeneralContentResult<List<VmPlatformDetailInfoResp>> getVMServicePlatformInfo(
            @ApiParam(required = true, name = "_conn", value = "Service Query Conndition.") @RequestBody VMServiceQueryCondition _conn) {
        log.debug("Going to retrieve Service Item {}.", _conn);
        GeneralContentResult<List<VmPlatformDetailInfoResp>> tempResult = this.platformService.getVmServicePlatformDetailInfo(_conn);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/type/disk", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建Disk产品目录（完成）", notes = "Create Disk Services")
    public GeneralResult createDiskService(@RequestBody DiskServiceProfile _profile) {
        log.debug("Going to create Disk services {}.", _profile);
        //todo
        GeneralResult tempResult = this.productTemplateService.createDiskService(_profile);
        return tempResult;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/{serviceId}/disk/", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取产品目录详情包括平台信息，可用区，储存（已完成）", notes = "Get Platform Service detail info.")
    public GeneralContentResult<DiskServiceProfileRep> getProductPlatformInfoForCreateDisk(@PathVariable("serviceId") String serviceId) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取创建产品时，选中的产品目录详情 serviceId= {}",serviceId);
        GeneralContentResult<DiskServiceProfileRep> result=productTemplateService.getProductPlatformInfoForCreateDisk(serviceId);
        return result;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/services/platforms/disk/detailinfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "新建disk产品目录时，获取相关产品目录的平台信息。可用区，储存详情列表（已完成）")
    public GeneralContentResult<List<DiskPlatformDetailInfoResp>> getDiskServicePlatformInfo(){
        log.debug("获取云硬盘产品目录详情");
        return productTemplateService.getDiskServicePlatformInfo();
    }


    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/servicetemplate", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取产品目录类别（已完成）", notes = "Get all of the service Template")
    public GeneralContentResult<List<AdsServiceTemplateReq>> getAllServiceTemplate(@RequestParam(value = "status", required = false) Byte status) {
        log.debug("Going to retrieve all of the Service Template.");
        GeneralContentResult<List<AdsServiceTemplateReq>> tempResult = this.productTemplateService.getTemplates(status);
        return tempResult;
    }




    

}
