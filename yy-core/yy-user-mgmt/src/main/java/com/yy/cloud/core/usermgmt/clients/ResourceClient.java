package com.yy.cloud.core.usermgmt.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.enterprise.ResourceQuotaUsageDetailProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaDetailResp;

import java.util.List;

/**
 * Created by wanxiaod on 12/7/2016.
 */
@FeignClient("resourcemgmt")
public interface ResourceClient {

    @RequestMapping(value = "/noauth/enterprise/{enterpriseId}/resource/quota/detail", method = RequestMethod.GET)
    GeneralContentResult<List<EnterpriseQuotaDetailResp>> getEnterpriseQuotaDetail(@PathVariable("enterpriseId") String enterpriseId);

    @RequestMapping(value = "/noauth/enterprise/resouce/quota/validationupdate", method = RequestMethod.PUT)
    public GeneralContentResult<ResourceQuotaUsageDetailProfile> validateAndUpdateEnterpriseUsedQuota(@RequestBody List<ResourceQuotaUsageDetailProfile> list);

}
