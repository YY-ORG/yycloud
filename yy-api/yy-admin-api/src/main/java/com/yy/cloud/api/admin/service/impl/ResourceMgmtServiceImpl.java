package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.ResourceMgmtClient;
import com.yy.cloud.api.admin.service.ResourceMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaDetailResp;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenluo on 2016/11/25.
 */
@Service
@Slf4j
public class ResourceMgmtServiceImpl implements ResourceMgmtService {

    @Autowired
    private ResourceMgmtClient resourceMgmtClient;

    @Override
    public GeneralContentResult<List<EnterpriseQuotaDetailResp>> getAllEnterpriseQuotaDetail() {
        GeneralContentResult<List<EnterpriseQuotaDetailResp>> result= resourceMgmtClient.getAllEnterpriseQuotaDetail();
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取所有企业的配额详情 {}",result);
        return result;
    }

    @Override
    public GeneralContentResult<ResourceQuotaUsageDetailProfile> validateAndUpdateEnterpriseUsedQuota(List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles){
        return resourceMgmtClient.validateAndUpdateEnterpriseUsedQuota(resourceQuotaUsageDetailProfiles);
    }
}
