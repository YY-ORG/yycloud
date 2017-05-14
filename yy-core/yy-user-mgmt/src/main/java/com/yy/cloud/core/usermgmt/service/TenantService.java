package com.yy.cloud.core.usermgmt.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.enterpise.EnterpriseUsedQuota;
import com.yy.cloud.common.data.usermgmt.TenantModel;
import com.yy.cloud.core.usermgmt.data.domain.FoxTenant;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by guof on 2016/9/27.
 */
public interface TenantService {

    FoxTenant createTenant(EnterpriseProfile profile);

    FoxTenant updateTenant(TenantModel tenantModel);

    List<FoxTenant> findAll();

    Page<FoxTenant> findAllByPaging(Integer page, Integer size);

    FoxTenant findById(String tenantId);
    
    List<EnterpriseProfile> getEnterprisesByPlatformId(String _platformId);
    
    List<String> findEnterpriseIdByPlatformId(String platformId);
    
    /**
     * updateStatus: Update the tenant's status. --added by chenxj <br/>
     *
     * @param _tenantId
     * @param _status
     * @return
     */
    void updateStatus(String _tenantId, Byte _status);

    /**
     * updateStatusByBatch: Update the tenants' status by batch. <br/>
     *
     * @param _enterprises
     * @param _status
     * @return
     */
    void updateStatusByBatch(List<EnterpriseProfile> _enterprises, Byte _status);

    /**
     * 根据企业ID获取企业下所有部门已使用资源
     * @param tenantId
     * @return
     */
    GeneralContentResult<EnterpriseUsedQuota> getAllOrganUsedQuotaByTenantId(String tenantId);
}
