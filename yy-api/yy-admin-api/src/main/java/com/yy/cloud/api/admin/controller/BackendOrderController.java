package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.BackendOrderService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.ordermgmt.OrderSearchCondtion;
import com.yy.cloud.common.data.dto.submgmt.SubQueryCondition;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalOrderItem;
import com.yy.cloud.common.data.otd.ordermgmt.OrderDetailItem;
import com.yy.cloud.common.data.otd.ordermgmt.OrderItem;
import com.yy.cloud.common.data.otd.productmgmt.ProductBillingItem;
import com.yy.cloud.common.data.otd.submgmt.SubInstanceResp;
import com.yy.cloud.common.data.platformmgmt.ServiceResAttributePair;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 10/28/2016.
 */
@RestController
@Slf4j
public class BackendOrderController {

	@Autowired
	private BackendOrderService backendOrderService;


	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/backend/order/{orderNo}/detail", method = RequestMethod.GET)
	@ApiOperation(value = "运营中心->订单管理，根据订单ID，获取订单详情(开发中）")
	public GeneralContentResult<OrderDetailItem> getOrderDetail(
			@ApiParam("订单编号") @PathVariable("orderNo") String orderNo) {
		return backendOrderService.getOrderDetail(orderNo);
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/order/{subInstanceId}/renew", method = RequestMethod.POST)
	@ApiOperation(value = "运营中心->订单管理,续订订单(已完成）")
	public GeneralContentResult<String> renewOrder(
			@ApiParam("订单ID") @PathVariable("subInstanceId") String subInstanceId, @ApiParam(name = "_list",
					value = "续订中所调整的属性", required = false) @RequestBody List<ServiceResAttributePair> _list) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "续订订单ID = {}", subInstanceId);
		return backendOrderService.renewOrder(subInstanceId, _list);
	}


	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/shopping/subinstance/{_subId}/cancel/{cascade}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "运营中心->订单管理,退订订单(已完成）", notes = "Cancel some subscription.")
	public GeneralContentResult<List<String>> cancelOrder(
			@ApiParam(name = "_subId", value = "订购实例的ID", required = true) @PathVariable("_subId") String _subId, @ApiParam(name = "cascade", value = "是否级联删除子订单0不级联删除，1级联删除", required = true)  @PathVariable("cascade") int cascade) {
		log.debug(CommonConstant.LOG_DEBUG_TAG + "退订订单ID = {}", _subId);
		return backendOrderService.cancelOrder(_subId,cascade);
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/order/search/paging", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "运营中心->订单管理,查询订单(未完成)", notes = "Cancel some subscription.")
	public GeneralPagingResult<List<ApprovalOrderItem>> searchOrder(@ApiParam("查询条件") @RequestBody OrderSearchCondtion orderSearchCondtion) {
		return backendOrderService.searchOrder(orderSearchCondtion);
	}





}
