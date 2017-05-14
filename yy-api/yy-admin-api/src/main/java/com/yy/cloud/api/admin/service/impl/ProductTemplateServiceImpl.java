package com.yy.cloud.api.admin.service.impl;

import com.yy.cloud.api.admin.clients.ServiceMgmtClient;
import com.yy.cloud.api.admin.service.ProductTemplateService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.ServicePageQueryCondition;
import com.yy.cloud.common.data.dto.enterprise.ServiceQueryCondition;
import com.yy.cloud.common.data.dto.platformmgmt.AdsServiceTemplateReq;
import com.yy.cloud.common.data.dto.platformmgmt.DiskServiceProfile;
import com.yy.cloud.common.data.dto.platformmgmt.DiskServiceProfileRep;
import com.yy.cloud.common.data.dto.platformmgmt.VMServiceProfile;
import com.yy.cloud.common.data.otd.platformmgmt.DiskPlatformDetailInfoResp;
import com.yy.cloud.common.data.otd.platformmgmt.ServiceItem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by chenluo on 11/12/2016.
 */
@Service
@Slf4j
public class ProductTemplateServiceImpl implements ProductTemplateService {

    @Autowired
    private ServiceMgmtClient serviceMgmtClient;

    @Override
    public GeneralContentResult<List<DiskPlatformDetailInfoResp>> getDiskServicePlatformInfo() {
        GeneralContentResult<List<DiskPlatformDetailInfoResp>> result= serviceMgmtClient.getDiskServicePlatformInfo();
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取云硬盘产品目录创建可用区返回结果 {}",result);
        return result;
    }

    @Override
    public GeneralContentResult<DiskServiceProfileRep> getProductPlatformInfoForCreateDisk(String serviceId) {
        GeneralContentResult<DiskServiceProfileRep> result= serviceMgmtClient.getProductPlatformInfoForCreateDisk(serviceId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取云硬盘产品创建可用区返回结果 {}",result);
        return result;
    }

    @Override
    public GeneralResult createDiskService(DiskServiceProfile _profile) {
        GeneralResult result= serviceMgmtClient.createDiskService(_profile);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"创建云硬盘返回结果 {}",result);
        return result;
    }

    @Override
    public GeneralContentResult<List<AdsServiceTemplateReq>> getTemplates(@RequestParam("status") Byte status) {
        return serviceMgmtClient.getTemplates(status);
    }

    @Override
    public GeneralPagingResult<List<ServiceItem>> getServiceItemsByCondition(ServicePageQueryCondition _conn) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"查询产品目录参数为: {}",_conn);
        return serviceMgmtClient.getServiceByConPaging(_conn);
    }
    
    @Override
    public GeneralResult createVMService(VMServiceProfile _profile) {
        log.debug("Going to create service...");
        // TODO Auto-generated method stub -- wutao
        GeneralResult tempResult = this.serviceMgmtClient.createService(_profile);
        return tempResult;
    }
    
    
}
