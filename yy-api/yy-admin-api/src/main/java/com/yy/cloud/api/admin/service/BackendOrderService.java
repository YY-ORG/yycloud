package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.ordermgmt.OrderSearchCondtion;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalHistoryRecord;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalOrderItem;
import com.yy.cloud.common.data.otd.ordermgmt.OrderDetailItem;
import com.yy.cloud.common.data.otd.ordermgmt.OrderItem;
import com.yy.cloud.common.data.otd.productmgmt.ProductBillingItem;
import com.yy.cloud.common.data.platformmgmt.ServiceResAttributePair;

/**
 * Created by chenluo on 11/7/2016.
 */
public interface BackendOrderService {



    /**
     * 运营中心->订单管理，根据订单ID，获取订单详情(开发中）
     * @param orderNo
     * @return
     */
    public GeneralContentResult<OrderDetailItem> getOrderDetail(String orderNo);

    /**
     * 订单ID 续订订单
     * @param orderId
	 * @param _list: 续订过程中，可能会产生变更的属性集
     * @return
     */
    public GeneralContentResult<String> renewOrder(String orderId, List<ServiceResAttributePair> _list);

    /**
     * 退订订单
     * @param _subId  订购实例ID
     * @return
     */
    GeneralContentResult<List<String>> cancelOrder(String _subId, int cascade);

    /**
     * 审批中心->待审批列表，查询订单
     * @param orderSearchCondtion
     * @return
     */
     GeneralPagingResult<List<ApprovalOrderItem>> searchOrderForApproval(OrderSearchCondtion orderSearchCondtion);

    /**
     * 审批中心->待审批列表，同意/拒绝
     * @param orderId
     * @param operation
     * @param reason
     * @return
     */
     GeneralContentResult<String> operateOrder(String orderId, Byte operation, String reason);

    /**
     * 审批中心->待审批列表/已审批列表 获取审批历史详情(开发中）
     * @param orderId
     * @return
     */
     GeneralContentResult<List<ApprovalHistoryRecord>> getApprovalDetail(String orderId);


    GeneralContentResult<ProductBillingItem> getSubinstanceNewPrice(String _subId);

    GeneralPagingResult<List<ApprovalOrderItem>> searchOrder(OrderSearchCondtion orderSearchCondtion);
}
