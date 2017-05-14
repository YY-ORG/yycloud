package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaDetailResp;

/**
 * Created by chenluo on 2016/11/25.
 */
public interface ResourceMgmtService {

    /**
     * 获取所有企业配额详情
     * @return
     */
    GeneralContentResult<List<EnterpriseQuotaDetailResp>> getAllEnterpriseQuotaDetail();
    
    GeneralContentResult<ResourceQuotaUsageDetailProfile> validateAndUpdateEnterpriseUsedQuota(List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles);
}
