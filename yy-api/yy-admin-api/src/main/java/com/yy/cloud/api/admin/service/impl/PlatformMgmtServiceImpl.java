/**
 * Project Name:liz-admin
 * File Name:PlatformMgmtServiceImpl.java
 * Package Name:com.gemii.lizcloud.api.admin.service.impl
 * Date:Sep 18, 20164:23:00 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gemii.lizcloud.cloudconnector.dto.PlatformAuth;
import com.gemii.lizcloud.cloudconnector.model.RegionItem;
import com.yy.cloud.api.admin.clients.PlatformAccessClient;
import com.yy.cloud.api.admin.clients.PlatformMgmtClient;
import com.yy.cloud.api.admin.clients.ServiceMgmtClient;
import com.yy.cloud.api.admin.clients.UserMgmtClient;
import com.yy.cloud.api.admin.service.PlatformMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.dto.enterprise.PlatformQuotaUsedProfile;
import com.yy.cloud.common.data.dto.enterprise.ServicePageQueryCondition;
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

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName:PlatformMgmtServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 18, 2016 4:23:00 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class PlatformMgmtServiceImpl implements PlatformMgmtService {

    @Resource
    PlatformMgmtClient platformMgmtClient;

    @Resource
    PlatformAccessClient platformAccessClient;

    @Resource
    ServiceMgmtClient serviceMgmtClient;
    
    @Resource
    UserMgmtClient userMgmtClient;

    @Override
    public GeneralContentResult<String> createPlatform(PlatformProfile _profile) {
        GeneralContentResult<String> tempResult = platformAccessClient.registerPlatform(_profile);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<PlatformItem>> getAllPlatformsByPage(Integer _page, Integer _size, Byte _status) {
        GeneralPagingResult<List<PlatformItem>> remoteResult = platformAccessClient.listPlatformsByPage(this.getDBPageNo(_page), _size, _status);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<PlatformItem> getPlatformById(String _id) {
        GeneralContentResult<PlatformItem> remoteResult = platformAccessClient.findPlatform(_id);
        return remoteResult;
    }
    
    @Override
    public GeneralContentResult<PlatformZoneItem> getZoneById(String _id) {
    	GeneralContentResult<PlatformZoneItem> remoteResult = platformAccessClient.getZoneById(_id);
    	return remoteResult;
    }
    
    @Override
    public GeneralContentResult<PlatformAdpImageItem> getImageById(String _id) {
    	GeneralContentResult<PlatformAdpImageItem> remoteResult = platformAccessClient.getImageById(_id);
    	return remoteResult;
    }
    
    @Override
    public GeneralContentResult<PlatformStorageItem> getStorageById(String _id) {
    	GeneralContentResult<PlatformStorageItem> remoteResult = platformAccessClient.getStorageById(_id);
    	return remoteResult;
    }

    @Override
    public GeneralContentResult<SynchronizeObjectInfo> synchronizeCounts(String platformId) {
        GeneralContentResult<SynchronizeObjectInfo> remoteResult = platformAccessClient.synchronizeCounts(platformId);
        return remoteResult;
    }
    
    @Override
    public GeneralContentResult<List<EnterpriseProfile>> getEnterprisesByPlatformId(String _platformId){
    	GeneralContentResult<List<EnterpriseProfile>> remoteResult = userMgmtClient.getEnterprisesByPlatformId(_platformId);
    	return remoteResult;
    }

    @Override
    public GeneralContentResult<SynchronizeResult> synchronizeZones(String platformId) {
        GeneralContentResult<SynchronizeResult> remoteResult = platformAccessClient.synchronizeZones(platformId);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<SynchronizeResult> synchronizeStorages(String platformId) {
        GeneralContentResult<SynchronizeResult> remoteResult = platformAccessClient.synchronizeStorages(platformId);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<SynchronizeResult> synchronizeFlavors(String platformId) {
        GeneralContentResult<SynchronizeResult> remoteResult = platformAccessClient.synchronizeFlavors(platformId);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<SynchronizeResult> synchronizeImages(String platformId) {
        GeneralContentResult<SynchronizeResult> remoteResult = platformAccessClient.synchronizeImages(platformId);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<SynchronizeResult> synchronizeRegions(String platformId) {
        GeneralContentResult<SynchronizeResult> remoteResult = platformAccessClient.synchronizeRegions(platformId);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<List<PlatformZoneQuotaItem>> getZoneQuotaByPlatformId(String _id) {
        GeneralContentResult<List<PlatformZoneQuotaItem>> remoteResult = platformAccessClient.listZoneQuotas(_id);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<List<PlatformStorageQuotaItem>> getStorageQuotaByPlatform(String _id) {
        GeneralContentResult<List<PlatformStorageQuotaItem>> remoteResult = platformAccessClient.listStorageQuotas(_id);
        return remoteResult;
    }

    @Override
    public GeneralContentResult<List<Flavor>> getFlavorsByPlatform(String _id) {
        GeneralContentResult<List<Flavor>> remoteResult = platformAccessClient.listFlavors(_id);
        return remoteResult;
    }

    @Override
    public GeneralResult updateZoneQuota(String _platformId, PlatformZoneQuotaProfile[] _profile) {
        GeneralResult tempReulst = platformAccessClient.modifyZoneQuotas(_platformId, _profile);
        return tempReulst;
    }

    @Override
    public GeneralResult updateStorageQuota(String _platformId, PlatformStorageQuotaProfile[] _profile) {
        return platformAccessClient.modifyStorageQuotas(_platformId, _profile);
    }

    @Override
    public GeneralResult updateFlavors(String _platformId, Flavor[] _profile) {
        GeneralResult tempResult = platformAccessClient.modifyFlavors(_platformId, _profile);
        return tempResult;
    }

    @Override
    public GeneralResult deletePlatform(String platformId) {
        GeneralResult tempResult = platformAccessClient.deletePlatform(platformId);
        return tempResult;
    }

    @Override
    public GeneralResult activePlatform(String platformId) {
        GeneralResult tempResult = platformAccessClient.activePlatform(platformId);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<Region>> getRegionByType(Byte _type) {
        // 获取区域：type映射数据字典 --guofeng 可直接访问platformaccess
        GeneralContentResult<List<Region>> tempResult = this.platformAccessClient.getRegionsByType(_type);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<Region>> getAllRegion() {
        // 获取所有的区域 --guofeng 访问platformmgmt
        log.debug("Going to retrieve all of the platforms' resources' quotas..");
        GeneralContentResult<List<Region>> tempResult = this.platformAccessClient.getRegions();
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<Region>> getRegionByPlatformId(String _id) {
    	GeneralContentResult<List<Region>> remoteResult = platformAccessClient.getRegionByPlatformId(_id);
    	return remoteResult;
    }

    @Override
    public GeneralPagingResult<List<PlatformResourceQuota>> getPlatformResourceQuota(Integer _page, Integer _size) {
        log.debug("Going to retrieve all of the platforms' resources' quotas..");
        GeneralPagingResult<List<PlatformResourceQuota>> tempResult = this.platformAccessClient
                .getPlatformResourceQuota(new PageParameter(this.getDBPageNo(_page), _size));
        return tempResult;
    }


    @Override
    public GeneralContentResult<List<PlatformImageItem>> getPlatformImages(String _platformId) {
        GeneralContentResult<List<PlatformImageItem>> tempResult = this.platformAccessClient
                .getPlatformImages(_platformId);
        return tempResult;
    }

    @Override
    public GeneralResult updatePlatformImages(String _platformId, PlatformImageProfile[] _profile) {
        GeneralResult tempResult = this.platformAccessClient.updatePlatformImages(_platformId, _profile);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<PlatformSimpleItem>> getPlatformSimpleList() {
        return this.platformAccessClient.getAllActivePlatforms();
    }

    @Override
    public GeneralPagingResult<List<ZoneItem>> getZoneByPlatform(String _id) {
        log.debug("Going to retrieve Service SKU..");
        GeneralPagingResult<List<ZoneItem>> tempResult = this.platformAccessClient.getZoneByPlatform(_id);
        return tempResult;
    }

    @Override
    public GeneralContentResult<SynchronizeResult> synchronizeHosts(String _platformId) {
        return this.platformAccessClient.synchronizeHosts(_platformId);
    }

    @Override
    public GeneralResult suspendPlatform(String _platformId) {
        GeneralResult tempResult = this.platformAccessClient.suspendPlatform(_platformId);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<PlatformResourceQuota>> getPlatformResourceQuota(PageParameter pageParameter) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取所有平台的可用配额", pageParameter);
        GeneralPagingResult<List<PlatformResourceQuota>> res = platformAccessClient.getPlatformResourceQuota(pageParameter);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "当前所有平台可用配额为: ", res);
        return res;
    }

    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public GeneralResult createVMService(VMServiceProfile _profile) {
        log.debug("Going to create service...");
        // TODO Auto-generated method stub -- wutao
        GeneralResult tempResult = this.platformMgmtClient.createVMService(_profile);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<GeneralContent> updateService(VMServiceProfile _profile) {
        log.debug("Going to update service...");
        // 更新服务目录，依据profile里面记录，有就更新，没有就不更新 -- wutao
        GeneralPagingResult<GeneralContent> tempResult = this.platformMgmtClient.updateService(_profile);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<VMServiceProfile>> getServicesByPlatformId(String _platformId) {
        // 获取某个平台下 -- wutao
        GeneralPagingResult<List<VMServiceProfile>> tempResult = this.platformMgmtClient
                .getServiceProfileList(_platformId);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<VMServiceProfile> getServiceById(String _platformId, String _serviceId) {
        // 服务目录编辑中的，获取服务目录所有属性 -- wutao
        GeneralPagingResult<VMServiceProfile> tempResult = this.platformMgmtClient.getServiceProfile(_platformId,
                _serviceId);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<ServiceItem>> getServiceItemList() {
        // 获取所有的服务目录 -- wutao
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformMgmtClient.getService();
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<ServiceItem>> getServiceItemByRegion(String _regionId) {
        // 依据区域来获取服务列表，区域--> platformId -- wutao
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformMgmtClient.getServiceByRegion(_regionId);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<ServiceTemplateItem>> getServiceTemplateItemList(Byte status) {
        // 获取服务模板列表 -- wutao
        GeneralContentResult<List<ServiceTemplateItem>> tempResult = this.platformMgmtClient.getServiceTemplate(status);
        return tempResult;
    }


    @Override
    public GeneralPagingResult<List<StartUpDisk>> getStartupDisk(String _platformId) {
        // 获得cinder，storage信息 --guofeng 可直接访问platformaccess
        GeneralPagingResult<List<StartUpDisk>> tempResult = this.platformMgmtClient.getStartupDisk(_platformId);
        return tempResult;
    }

    @Override
    public GeneralResult updateServiceStatus(String _serviceId, Byte _status) {
        // 单独更新服务状态，激活，非激活 -- wutao
        log.debug("Going to retrieve all of the platforms' resources' quotas..");
        GeneralResult tempResult = this.platformMgmtClient.updateServiceStatus(_serviceId, _status);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<ServiceSku>> getServiceSKUByService(String _serviceId, Integer _page,
                                                                        Integer _size) {
        log.debug("Going to retrieve Service SKU..");
        GeneralPagingResult<List<ServiceSku>> tempResult = this.platformMgmtClient.getServiceSKU(_serviceId,
                new PageParameter(_page, _size));
        return tempResult;
    }

    @Override
    public GeneralPagingResult<GeneralContent> deleteService(String _serviceId) {
        log.debug("Going to delete the service {} ..", _serviceId);
        GeneralPagingResult<GeneralContent> tempResult = this.platformMgmtClient.deleteService(_serviceId);
        return tempResult;
    }

    private Integer getDBPageNo(Integer _page) {
        Integer tempPage = _page - 1;
        return tempPage >= 0 ? tempPage : 0;
    }

    @Override
    public GeneralPagingResult<List<ServiceItem>> getServiceItemListByPage(Integer _page, Integer _size) {
        log.debug("Going to retrieve Service By paging..");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.serviceMgmtClient.getServices(Byte.parseByte("1"), _page - 1, _size);

        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<ServiceItem>> getServiceItemListByRegionPaging(String _regionId, Integer _page,
                                                                                   Integer _size) {
        log.debug("Going to retrieve Service By Platform paging..");
        GeneralPagingResult<List<ServiceItem>> tempResult = this.platformMgmtClient.getServiceByRegionPaing(_regionId,
                new PageParameter(this.getDBPageNo(_page), _size));
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<ServiceItem>> getServiceItemListByConnPage(Integer _page, Integer _size,
                                                                               ServiceQueryCondition _conn) {
        ServicePageQueryCondition tempConn = new ServicePageQueryCondition();
        tempConn.setCategoryId(_conn.getCategoryId());
        tempConn.setPlatformId(_conn.getPlatformId());
        tempConn.setPage(this.getDBPageNo(_page));
        tempConn.setSize(_size);
        return this.platformMgmtClient.getServiceByConnPaing(tempConn);
    }


    @Override
    public GeneralContentResult<List<VmPlatformDetailInfoResp>> getVmServicePlatformDetailInfo(
            VMServiceQueryCondition _conn) {

        GeneralContentResult<List<VmPlatformDetailInfoResp>> result = this.serviceMgmtClient.getVmServicePlatformDetailInfo(_conn);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取创建产品目录时的可用区，返回结果为 {}", result);
        return result;

    }


    @Override
    public GeneralContentResult<List<ServiceSimpleItem>> getServiceSimpleItemList() {

        return this.serviceMgmtClient.getServiceSimpleItemList();
    }

    @Override
    public GeneralContentResult<VMServiceDetailInfoItem> getVMServiceDetailInfoItemList(String _serviceId) {

        return this.serviceMgmtClient.getVMServiceItem(_serviceId);
    }

    @Override
    public GeneralContentResult<List<RegionItem>> listRegions(PlatformAuth platformAuth) {
        return platformAccessClient.listRegions(platformAuth);
    }

}
