/**
 * Project Name:liz-admin
 * File Name:PlatformMgmtClient.java
 * Package Name:com.gemii.lizcloud.api.admin.clients
 * Date:Sep 18, 20164:41:45 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.clients;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.submgmt.SubQueryCondition;
import com.yy.cloud.common.data.otd.enterprise.CommonKeyValue;
import com.yy.cloud.common.data.otd.subinstance.SubInstanceDetailResp;
import com.yy.cloud.common.data.otd.submgmt.SubInstanceResp;

import java.util.List;

/**
 * ClassName:SubInstanceMgmtClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: Oct 23, 2016 4:41:45 PM <br/>
 *
 * @author Changqing, Liu
 * @see
 * @since JDK 1.8
 */
@FeignClient("subinstancemgmt")
//@FeignClient(url = "http://submgmt:9205", name = "submgmt")
public interface SubInstanceMgmtClient {

    @RequestMapping(path = "/authsec/platforms/enterprise/vms", method = RequestMethod.POST)
    @ApiOperation("Get enterprised compute instance number")
    @ResponseBody
    public GeneralContentResult<List<CommonKeyValue>> getEnterprisedComputeInstance(@RequestBody List<String> enterpriseIds);


    /**
     * 根据条件查询订单实例
     * @param orderSearchCondtion
     * @return
     */
    @RequestMapping(value = "/authsec/subinstance/subscription/search/paging",method = RequestMethod.POST)
    @ApiOperation(value = "运营中心->订单管理，查询订单")
    @ResponseBody
    public GeneralPagingResult<List<SubInstanceResp>> searchSubInstance(@RequestBody SubQueryCondition orderSearchCondtion);

    /**
     * 运营中心->已购服务管理，已购服务详情
     *
     * @param subinstanceCode
     * @return
     */
    @RequestMapping(value = "/authsec/subinstance/{subinstanceCode}/detail", method = RequestMethod.POST)
    @ApiOperation(value = "运营中心->已购服务管理，已购服务详情(开发中)")
    public GeneralContentResult<SubInstanceDetailResp> getSubInstanceDetail(
            @ApiParam("订购实例编号") @PathVariable("subinstanceCode") String subinstanceCode);



}
