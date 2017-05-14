package com.yy.cloud.api.admin.service.impl;

import com.yy.cloud.api.admin.clients.BackendOrderClient;
import com.yy.cloud.api.admin.service.BackendOrderService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.ordermgmt.OrderSearchCondtion;
import com.yy.cloud.common.data.dto.submgmt.SubQueryCondition;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalHistoryRecord;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalOrderItem;
import com.yy.cloud.common.data.otd.ordermgmt.OrderDetailItem;
import com.yy.cloud.common.data.otd.ordermgmt.OrderItem;
import com.yy.cloud.common.data.otd.productmgmt.ProductBillingItem;
import com.yy.cloud.common.data.otd.submgmt.SubInstanceResp;
import com.yy.cloud.common.data.platformmgmt.ServiceResAttributePair;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenluo on 11/7/2016.
 */
@Service
@Slf4j
public class BackendOrderServiceImpl implements BackendOrderService {

    @Autowired
    private BackendOrderClient backendOrderClient;




    @Override
    public GeneralContentResult<OrderDetailItem> getOrderDetail(String orderNo) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "根据订单编号，获取订单详情,订单编号 = {}", orderNo);
        GeneralContentResult<OrderDetailItem> result=backendOrderClient.getOrderDetail(orderNo);
        log.debug("获取订单详情返回结果: {}"+result);
        return result;
    }

    @Override
    public GeneralContentResult<String> renewOrder(String orderId, List<ServiceResAttributePair> _list) {
        GeneralContentResult<String> result= backendOrderClient.renewOrder(orderId, _list);
        log.debug("续订订单返回结果: {}"+result);
        return result;
    }

    @Override
    public GeneralContentResult<List<String>> cancelOrder(String _subId, int cascade) {
        GeneralContentResult<List<String>> result=  backendOrderClient.cancelOrder(_subId,cascade);
        log.debug("取消订单返回结果: {}"+result);
        return result;
    }

    @Override
    public GeneralPagingResult<List<ApprovalOrderItem>> searchOrderForApproval(OrderSearchCondtion orderSearchCondtion) {
        GeneralPagingResult<List<ApprovalOrderItem>> result = backendOrderClient.searchOrderForApproval(orderSearchCondtion);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"查询订单返回结果为 {}", result);
        return result;
    }

    @Override
    public GeneralContentResult<String> operateOrder(String orderId, Byte operation, String reason) {
        GeneralContentResult<String> result = backendOrderClient.operateOrder(orderId,operation, reason);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"审批订单返回结果为 {}", result);
        return result;
    }

    @Override
    public GeneralContentResult<List<ApprovalHistoryRecord>> getApprovalDetail(String orderId) {
        GeneralContentResult<List<ApprovalHistoryRecord>> result = backendOrderClient.getApprovalDetail(orderId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"审批历史返回结果为 {}", result);
        return result;
    }

    @Override
    public GeneralContentResult<ProductBillingItem> getSubinstanceNewPrice(String _subId) {
        return null;
    }

    @Override
    public GeneralPagingResult<List<ApprovalOrderItem>> searchOrder(OrderSearchCondtion orderSearchCondtion) {
        log.debug(CommonConstant.LOG_DEBUG_TAG+"查询订单条件为{}", orderSearchCondtion);
        GeneralPagingResult<List<ApprovalOrderItem>> result = backendOrderClient.searchOrder(orderSearchCondtion);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"返回订单结果为 {}", result);
        return result;
    }
}
