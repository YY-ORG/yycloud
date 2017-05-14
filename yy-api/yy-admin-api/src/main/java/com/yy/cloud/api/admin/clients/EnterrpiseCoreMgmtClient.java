package com.yy.cloud.api.admin.clients;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.enterprise.DepartmentItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseSimpleItem;
import com.yy.cloud.common.data.usermgmt.TenantModel;

import java.util.List;

/**
 * Created by chenluo on 10/31/2016.
 */

@FeignClient("usermgmt")
public interface EnterrpiseCoreMgmtClient {

    /**
     * 运营中心-订单管理，根据当前用户的角色获取企业
     * @param userId
     * @return
     */
    @RequestMapping(value = "/authsec/enterprise/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<List<TenantModel>> getEnterpriseByUserRole(@PathVariable("userId") String userId);


    /**
     * 运营中心-订单管理，获取企业部门
     * @param enterpriseId
     * @return
     */
    @RequestMapping(value = "/authsec/enterprise/{enterpriseId}/department/", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<List<DepartmentItem>> getDepartmentByEnterpriseId(@PathVariable("enterpriseId") String enterpriseId);

    /**
     * BOE 对接接口，获取某个平台关联的所有企业
     * @param platformId
     * @return
     */
    @RequestMapping(value = "/authsec/enterprise/platform/{platformId}/enterprises", method = RequestMethod.GET)
    @ResponseBody
    public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterprisesByPlatformId(@PathVariable("platformId") String platformId);
}
