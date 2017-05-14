package com.yy.cloud.core.usermgmt.clients;

import com.gemii.lizcloud.cloudconnector.dto.BatchEnterpriseCreate;
import com.gemii.lizcloud.cloudconnector.dto.Enterprise;
import com.gemii.lizcloud.cloudconnector.dto.EnterpriseCreate;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by wanxiaod on 12/3/2016.
 */
@FeignClient("connector")
public interface ConnectorClient {
    @RequestMapping(value = "/api/platforms/{id}/enterprise", method = RequestMethod.POST)
    Enterprise createProject(@PathVariable("id") String id, @RequestBody EnterpriseCreate enterpriseCreate);

    @RequestMapping(value="/api/enterprises/batch", method = RequestMethod.POST)
    List<Enterprise> createEnterpriseBatch(@RequestBody BatchEnterpriseCreate batchEnterpriseCreate);
}
