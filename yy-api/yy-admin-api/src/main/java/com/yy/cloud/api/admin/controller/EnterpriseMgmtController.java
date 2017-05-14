/**
 * Project Name:liz-admin
 * File Name:EnterpriseMgmtController.java
 * Package Name:com.gemii.lizcloud.api.admin.controller
 * Date:Sep 29, 20163:41:51 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.EnterpriseMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAuthProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseBasicProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfileReq;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseQuotaProfile;
import com.yy.cloud.common.data.dto.enterprise.ProductQueryConditionReq;
import com.yy.cloud.common.data.enterpise.*;
import com.yy.cloud.common.data.otd.enterprise.*;
import com.yy.cloud.common.data.otd.ordermgmt.ApprovalHistoryRecord;
import com.yy.cloud.common.data.usermgmt.TenantModel;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:EnterpriseMgmtController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 29, 2016 3:41:51 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class EnterpriseMgmtController {
	@Autowired
	private EnterpriseMgmtService enterpriseMgmtService;

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/ad/connection", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "测试企业AD连通性(未完成)", notes = "Create an enterpise")
	public GeneralResult testEnterprise(@ApiParam(name = "_profile", value = "Enterprise's Profile",
			required = true) @RequestBody EnterpriseProfile _profile) {
		log.debug("Going to create the Enterprise {}.", _profile);

//		GeneralResult tempResult = this.enterpriseMgmtService.testEnterpriseADConnection(_profile);
		GeneralResult tempResult = new GeneralResult();
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "创建企业（已完成）", notes = "Create an enterpise")
	public GeneralContentResult<String> createEnterprise(@ApiParam(name = "_profile", value = "Enterprise's Profile",
			required = true) @RequestBody EnterpriseProfileReq _profile) {
		log.debug("Going to create the Enterprise {}.", _profile);

		GeneralContentResult<String> tempResult = this.enterpriseMgmtService.createEnterprise(_profile);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/status/{_status}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "更新企业状态（已完成）", notes = "Update an enterpise's status")
	public GeneralResult updateEnterpriseStatus(
			@ApiParam(name = "_enterpriseId", value = "Enterprise's Id",
					required = true) @PathVariable("_enterpriseId") String _enterpriseId,
			@ApiParam(name = "_status", value = "0: Initial; 1: Active; 2: Suspend; 3: Cancelled, 4: Deleted.",
					required = true) @PathVariable("_status") Byte _status) {
		log.debug("Going to update the Enterprise {}'s status to {}.", _enterpriseId, _status);

		GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpriseStatus(_enterpriseId, _status);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/basic", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "更新企业基本信息（已完成）", notes = "Update an enterpise's profile")
	public GeneralResult updateEnterpriseBasicProfile(
			@ApiParam(name = "_enterpriseId", value = "Enterprise's Id",
					required = true) @PathVariable("_enterpriseId") String _enterpriseId,
			@ApiParam(name = "_profile", value = "Basic Info",
					required = true) @RequestBody EnterpriseBasicProfile _profile) {
		log.debug("Going to update the Enterprise {}'s profile to {}.", _enterpriseId, _profile);

		GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpriseBasicProfile(_enterpriseId, _profile);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/auth", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "更新企业鉴权信息(sprint3未完成)", notes = "Update an enterpise's profile")
	public GeneralResult updateEnterpriseAuthProfile(
			@ApiParam(name = "_enterpriseId", value = "Enterprise's Id",
					required = true) @PathVariable("_enterpriseId") String _enterpriseId,
			@ApiParam(name = "_profile", value = "Auth Info.",
					required = true) @RequestBody EnterpriseAuthProfile _profile) {
		log.debug("Going to update the Enterprise {}'s profile to {}.", _enterpriseId, _profile);
		//todo
//		GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpriseAuthProfile(_enterpriseId, _profile);
		GeneralResult tempResult= new GeneralResult();
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprises/status/{_status}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "批量更新企业状态（已完成）", notes = "Create an enterpise")
	public GeneralResult updateEnterpriseStatusByBatch(
			@ApiParam(name = "_list", value = "Enterprise's Id List",
					required = true) @RequestBody List<String> _list,
			@ApiParam(name = "_status", value = "0: Initial; 1: Active; 2: Suspend; 3: Cancelled, 4: Deleted.",
					required = true) @PathVariable Byte _status) {
		log.debug("Going to update the Enterprise {}'s status to {} by batch.", _list, _status);

		GeneralResult tempResult = this.enterpriseMgmtService.updateEnterpriseStatusByBatch(_list,
				_status);
		return tempResult;
	}
	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/simple", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取单个企业基本信息（已完成）",
			notes = "Get the enterpise basic info")
	public GeneralContentResult<EnterpriseGeneralItem> getEnterpriseSimpleInfo(
			@ApiParam(name = "_enterpriseId", value = "企业ID", required = true) @PathVariable String _enterpriseId) {
		log.debug("Going to retrieve all of the enterprises.");

		GeneralContentResult<EnterpriseGeneralItem> tempResult = this.enterpriseMgmtService
				.getEnterpriseGeneralItem(_enterpriseId);
		return tempResult;
	}
	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprises/simple", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有企业基本信息，主要用于下拉框（已完成）",
			notes = "Get the enterpise basic info")
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterpriseSimpleInfoList() {
		log.debug("Going to retrieve all of the enterprises.");

		GeneralContentResult<List<EnterpriseSimpleItem>> tempResult = this.enterpriseMgmtService
				.getEnterpriseSimpleItemList();
		return tempResult;
	}
	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprises/simple/page/{_page}/size/{_size}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Get all of the Enterprises with simple info",
			notes = "Get all of the enterpises with basic info")
	public GeneralPagingResult<List<EnterpriseGeneralItem>> getEnterpriseSimpleInfoByPage(
			@ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
			@ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
		log.debug("Going to retrieve all of the enterprises.");

		GeneralPagingResult<List<EnterpriseGeneralItem>> tempResult = this.enterpriseMgmtService
				.getEnterpriseGeneralItemList(_page, _size);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprises/opening/page/{_page}/size/{_size}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Get all of the Enterprises with opening info",
			notes = "Get all of the enterpises with basic info")
	public GeneralPagingResult<List<EnterpriseOpeningItem>> getEnterpriseWithPandectInfo(
			@ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
			@ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
		log.debug("Going to retrieve all of the enterprises.");

		GeneralPagingResult<List<EnterpriseOpeningItem>> tempResult = this.enterpriseMgmtService
				.getEnterpriseOpeningItem(_page, _size);
		return tempResult;
	}


	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/products/page/{_page}/size/{_size}",
			method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Get some Enterprise's Products", notes = "Create product list for enterprise.")
	public GeneralPagingResult<List<ProductItem>> getProductByEnterprise(
			@ApiParam(required = true, name = "_enterpriseId",
					value = "Enterprise's Id") @PathVariable String _enterpriseId,
			@ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
			@ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
		log.debug("Going to create the products for the Enterprise {}.", _enterpriseId);

		GeneralPagingResult<List<ProductItem>> tempResponse = this.enterpriseMgmtService
				.getProductItemByEnterpriseId(_enterpriseId, _page, _size);
		return tempResponse;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprises/region/{_regionId}/products/page/{_page}/size/{_size}",
			method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Get Enterprise's Product By Region", notes = "Create product list for enterprise.")
	public GeneralPagingResult<List<ProductItem>> getProductByRegion(
			@ApiParam(required = true, name = "_regionId", value = "Region's Id") @PathVariable String _regionId,
			@ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
			@ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
		log.debug("Going to get the products for the Enterprise {}.", _regionId);

		GeneralPagingResult<List<ProductItem>> tempResponse = this.enterpriseMgmtService
				.getProductItemByRegionId(_regionId, _page, _size);

		return tempResponse;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/region/{_regionId}/product/page/{_page}/size/{_size}",
			method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Get Product by Enterprise and Region",
			notes = "Query the products by enterprise and region.")
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProductByEntAndRegion(
			@ApiParam(required = true, name = "_enterpriseId",
					value = "Enterprise's Id") @PathVariable String _enterpriseId,
			@ApiParam(required = true, name = "_regionId", value = "Region's Id") @PathVariable String _regionId,
			@ApiParam(name = "_page", value = "_page", required = true) @PathVariable Integer _page,
			@ApiParam(name = "_size", value = "_size", required = true) @PathVariable Integer _size) {
		log.debug("Going to retrive the products by enterprise id {} and region id {}.", _enterpriseId, _regionId);

		GeneralPagingResult<List<ProductItem>> tempResponse = this.enterpriseMgmtService
				.getEnterpriseProductByEntAndRegion(_enterpriseId, _regionId, _page, _size);
		return tempResponse;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/product", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Create a Product", notes = "Create a product for enterprise.")
	public GeneralPagingResult<ProductItem> createProductForEnterprise(
			@ApiParam(required = true, name = "_enterpriseId",
					value = "Enterprise's Id") @PathVariable String _enterpriseId,
			@RequestBody ProductProfile _profile) {
		log.debug("Going to create a product for the Enterprise {}.", _enterpriseId);

		GeneralPagingResult<ProductItem> tempResult = this.enterpriseMgmtService.createProduct(_profile);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{_enterpriseId}/products", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "Create Enterprise's Products By Batch",
			notes = "Create products in the list for enterprise.")
	public GeneralPagingResult<List<ProductItem>> createProductsForEnterpriseByBatch(
			@ApiParam(required = true, name = "_enterpriseId",
					value = "Enterprise's Id") @PathVariable String _enterpriseId,
			@RequestBody List<ProductProfile> _productList) {
		log.debug("Going to create the products for the Enterprise {}.", _enterpriseId);

		GeneralPagingResult<List<ProductItem>> tempResult = this.enterpriseMgmtService.createProducts(_productList);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/product/{_id}/status/{_status}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Update some product's status", notes = "Update the product Status.")
	public GeneralPagingResult<GeneralContent> UpdateProductStatus(
			@ApiParam(name = "_id", value = "Product's ID", required = true) @PathVariable String _id,
			@ApiParam(name = "_status", value = "New Status", required = true) @PathVariable Byte _status) {
		log.debug("Going to update the product {}' status to {}.", _id, _status);

		GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtService.updateProductsStatus(_id, _status);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/products/status/{_status}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "Update products' status by batch", notes = "Update the product Status by batch.")
	public GeneralPagingResult<GeneralContent> UpdateProductStatusByBatch(
			@ApiParam(name = "_status", value = "New Status", required = true) @PathVariable Byte _status,
			@RequestBody List<ProductProfile> _list) {
		log.debug("Going to update the products {}' status to {}.", _list, _status);

		GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtService.updateProductsStatusByBatch(_list,
				_status);
		return tempResult;
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "运营中心-订单管理，根据当前用户的角色获取企业（未完成）", notes = "运营中心-订单管理，根据当前用户的角色获取企业")
	public GeneralContentResult<List<TenantModel>> getEnterpriseByUserRole(@PathVariable("userId") String userId){
		log.debug(CommonConstant.LOG_DEBUG_TAG+"根据当前用户的角色获取企业,参数为",userId);
		return enterpriseMgmtService.getEnterpriseByUserRole(userId);
	}

	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/{enterpriseId}/department/", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "运营中心-订单管理，获取企业部门（未完成）", notes = "运营中心-订单管理，获取企业部门")
	public GeneralContentResult<List<DepartmentItem>> getDepartmentByEnterpriseId(@PathVariable("enterpriseId") String enterpriseId){
		log.debug(CommonConstant.LOG_DEBUG_TAG+"获取企业部门,参数为",enterpriseId);
		return enterpriseMgmtService.getDepartmentByEnterpriseId(enterpriseId);
	}

	/**
	 * BOE 对接接口，获取某个平台关联的所有企业
	 * @param platformId
	 * @return
	 */
	@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
	@RequestMapping(value = "/authsec/enterprise/platform/{platformId}/enterprises", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "BOE 对接接口，获取某个平台关联的所有企业")
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterprisesByPlatformId(@PathVariable("platformId") String platformId){
		return enterpriseMgmtService.getEnterprisesByPlatformId(platformId);
	}
}
