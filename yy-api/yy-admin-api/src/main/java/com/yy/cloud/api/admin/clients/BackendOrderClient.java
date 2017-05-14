package com.yy.cloud.api.admin.clients;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.ordermgmt.OrderSearchCondtion;
import com.yy.cloud.common.data.otd.ordermgmt.*;
import com.yy.cloud.common.data.platformmgmt.ServiceResAttributePair;

import java.util.List;

/**
 * Created by chenluo on 10/28/2016.
 */
@FeignClient("ordermgmt")
public interface BackendOrderClient {

	/**
	 * getEnterpriseGeneralItem: 查看企业-订单与审批统计. <br/>
	 *
	 * @param _enterpriseId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/order/enterprise/statistics/{enterpriseId}")
	@ResponseBody
	public GeneralContentResult<OrdStatisticsItem> getEnterrpiseOrderStatistics(
			@PathVariable("enterpriseId") String _enterpriseId);

	/**
	 * getUserForOrderStatistics: 查看企业-账户统计. <br/>
	 *
	 * @param _enterpriseId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/order/enterprise/statistics/user/{enterpriseId}")
	@ResponseBody
	public GeneralContentResult<OrdStatisticsItem> getUserForOrderStatistics(
			@PathVariable("enterpriseId") String _enterpriseId);

	/**
	 * 运营中心->订单管理，订单详情
	 * 
	 * @param orderNo 订单编号
	 * @return
	 */
	@RequestMapping(value = "/authsec/backend/order/{orderNo}/detail", method = RequestMethod.GET)
	public GeneralContentResult<OrderDetailItem> getOrderDetail(
			@PathVariable("orderNo") String orderNo);

	/**
	 * 订单ID 续订订单
	 * 
	 * @param subInstanceId
	 * @param _list: 续订过程中，可能会产生变更的属性集
	 * @return
	 */
	@RequestMapping(value = "/authsec/order/subinstance/{subInstanceId}/renew", method = RequestMethod.POST)
	@ApiOperation(value = "运营中心->订单管理,续订订单")
	public GeneralContentResult<String> renewOrder(
			@ApiParam("订单实例ID") @PathVariable("subInstanceId") String subInstanceId, @ApiParam(name = "_list",
					value = "续订中所调整的属性", required = false) @RequestBody List<ServiceResAttributePair> _list);

	/**
	 * 退订某个订购实例
	 * 
	 * @param _subId
	 * @return
	 */
	@RequestMapping(value = "/authsec/shopping/subinstance/{_subId}/cancel/{cascade}", method = RequestMethod.GET)
	@ResponseBody
	GeneralContentResult<List<String>> cancelOrder(@PathVariable("_subId") String _subId,@PathVariable("cascade") int cascade);

	/**
	 * 审批中心->待审批列表，同意/拒绝
	 * @param orderId 订单ID
	 * @param operation 操作，0拒绝，1同意
	 * @param reason 同意或者拒绝的原因
	 * @return
	 */
	@RequestMapping(value = "/authsec/backend/approval/order/{orderId}/operation/{operation}",method = RequestMethod.POST)
	public GeneralContentResult<String> operateOrder(@PathVariable("orderId") String orderId, @PathVariable("operation") Byte operation,@RequestParam("reason") String reason);

	/**
	 *审批中心 审批列表 查询审批/未审批的订单
	 * @param orderSearchCondtion
	 * @return
	 */
	@RequestMapping(value = "/authsec/backend/approval/orders/search/paging",method = RequestMethod.POST)
	public GeneralPagingResult<List<ApprovalOrderItem>> searchOrderForApproval(@RequestBody OrderSearchCondtion orderSearchCondtion);


	/**
	 * 审批中心->待审批列表/已审批列表 获取审批历史详情(开发中）
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/authsec/backend/approval/history/order/{orderId}",method = RequestMethod.GET)
	public GeneralContentResult<List<ApprovalHistoryRecord>> getApprovalDetail(@PathVariable("orderId") String orderId);

	/**
	 * 运营中心->订单管理,查询订单(未完成)
	 * @param orderSearchCondtion
	 * @return
	 */
	@RequestMapping(value = "/authsec/order/search/paging", method = RequestMethod.POST)
	public GeneralPagingResult<List<ApprovalOrderItem>> searchOrder(@RequestBody OrderSearchCondtion orderSearchCondtion);

	@RequestMapping(value = "/authsec/order/{orderNo}/detail", method = RequestMethod.GET)
	public GeneralContentResult<OrderDetailItem> searchOrder( @PathVariable("orderNo") String orderNo);
}
