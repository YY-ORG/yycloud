package com.yy.cloud.api.admin.service;

import java.util.List;

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

/**
 * Created by chenluo on 11/12/2016.
 */
public interface ProductTemplateService {

    /**
     * 获取平台Disk服务（目录）平台，可用区，储存详情列表（未完成）
     * @return
     */
    public GeneralContentResult<List<DiskPlatformDetailInfoResp>> getDiskServicePlatformInfo();


    /**
     * 新建disk产品时，获取相关产品目录的平台信息。可用区，储存详情列表（未完成）
     * @param serviceId
     * @return
     */
    public GeneralContentResult<DiskServiceProfileRep> getProductPlatformInfoForCreateDisk(String serviceId) ;


    /**
     * 创建Disk产品目录（开发中）
     * @param _profile
     * @return
     */
    public GeneralResult createDiskService( DiskServiceProfile _profile) ;

    /**
     * 根据状态获取产品目录模版
     * @param status
     * @return
     */
    public GeneralContentResult<List<AdsServiceTemplateReq>> getTemplates( Byte status);


    /**
     * 条件检索产品目录（已完成）
     * @param _conn
     * @return
     */
    GeneralPagingResult<List<ServiceItem>> getServiceItemsByCondition(ServicePageQueryCondition _conn);
    
    /**
     * 创建VM产品目录
     * @param _profile
     * @return
     */
    public GeneralResult createVMService(VMServiceProfile _profile);
}
