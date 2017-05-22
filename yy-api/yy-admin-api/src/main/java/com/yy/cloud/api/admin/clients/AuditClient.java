package com.yy.cloud.api.admin.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.cloud.common.data.GeneralContentResult;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@FeignClient("audit")
public interface AuditClient {

    /**
     * 审批中心->待审批列表，同意/拒绝
     * @param orderId
     * @param operation
     * @return
     */
    @RequestMapping(value = "/authsec/backend/approval/order/{orderId}/operation/{operation}",method = RequestMethod.POST)
    @ApiOperation(value = "审批中心->待审批列表，同意/拒绝")
    public GeneralContentResult<String> approveOrder(@ApiParam(value="订单ID") @PathVariable("status") String orderId, @ApiParam(value = "操作，0拒绝，1同意") @PathVariable("operation") Byte operation);
}
