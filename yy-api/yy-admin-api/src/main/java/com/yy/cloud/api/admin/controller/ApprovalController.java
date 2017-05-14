package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.BackendOrderService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.ordermgmt.OrderSearchCondtion;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalHistoryRecord;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalOrderItem;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 11/1/2016.
 */
@RestController
@Slf4j
public class ApprovalController {

    @Autowired
    private BackendOrderService backendOrderService;
    /**
     * 审批中心->待审批列表，查询订单
     * @param orderSearchCondtion
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/backend/approval/orders/search/paging",method = RequestMethod.POST)
    @ApiOperation(value = "审批中心->待审批列表/已审批列表，查询订单(开发中）")
    public GeneralPagingResult<List<ApprovalOrderItem>> searchOrderForApproval(@ApiParam("查询条件") @RequestBody OrderSearchCondtion orderSearchCondtion){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"查询订单条件参数为 {}",orderSearchCondtion);
        return backendOrderService.searchOrderForApproval(orderSearchCondtion);
    }

    /**
     * 审批中心->待审批列表，同意/拒绝
     * @param orderId
     * @param operation
     * @param reason
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/backend/approval/order/{orderId}/operation/{operation}",method = RequestMethod.POST)
    @ApiOperation(value = "审批中心->待审批列表，同意/拒绝 (开发中）")
    public GeneralContentResult<String> operateOrder(@ApiParam(value="订单ID") @PathVariable("orderId") String orderId, @ApiParam(value = "操作，0拒绝，1同意") @PathVariable("operation") Byte operation,@ApiParam(value = "同意或者拒绝的原因") @RequestParam("reason") String reason){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"订单id 为 {}, 操作类型为 {} , 原因为 {}",orderId, operation, reason);
        return backendOrderService.operateOrder(orderId,operation,reason);
    }

    /**
     * 审批中心->待审批列表/已审批列表 获取审批历史详情(开发中）
     * @param orderId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/backend/approval/history/order/{orderId}",method = RequestMethod.GET)
    @ApiOperation(value = "审批中心->待审批列表/已审批列表 获取审批历史详情(开发中）")
    public GeneralContentResult<List<ApprovalHistoryRecord>> getApprovalDetail(@ApiParam(value = "订单ID") @PathVariable("orderId") String orderId){
        log.debug(CommonConstant.LOG_DEBUG_TAG+"订单id 为 {}",orderId);
        return backendOrderService.getApprovalDetail(orderId);
    }


}
