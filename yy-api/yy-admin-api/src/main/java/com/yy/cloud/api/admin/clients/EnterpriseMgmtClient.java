/**
 * Project Name:liz-admin
 * File Name:EnterpriseMgmtClient.java
 * Package Name:com.gemii.lizcloud.api.admin.clients
 * Date:Oct 3, 20169:19:25 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfileReq;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseQuotaProfile;
import com.yy.cloud.common.data.dto.enterprise.ProductPageQueryConditionReq;
import com.yy.cloud.common.data.enterpise.*;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdAdminItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseGeneralItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseOpeningItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseQuotaItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseSimpleItem;
import com.yy.cloud.common.data.otd.enterprise.ProductItem;

import java.util.List;

/**
 * ClassName:EnterpriseMgmtClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Oct 3, 2016 9:19:25 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@FeignClient("enterprisemgmt")
public interface EnterpriseMgmtClient {

	/**
	 * getEnterpriseOpeningItem:分页获取企业开通总览信息. <br/>
	 *
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/opening/paging")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseOpeningItem>> getEnterpriseOpeningItem(
			@RequestBody PageParameter _request);

	/**
	 * getEnterpriseGeneralItem:分页获取企业基本信息列表. <br/>
	 *
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/simple/paging")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseGeneralItem>> getEnterpriseGeneralItemList(
			@RequestBody PageParameter _request);

	/**
	 * getEnterpriseGeneralItem: 获取单个企业基本信息. <br/>
	 *
	 * @param _enterpriseId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprise/{_enterpriseId}/simple")
	@ResponseBody
	public GeneralContentResult<EnterpriseGeneralItem> getEnterpriseGeneralItem(
			@PathVariable("_enterpriseId") String _enterpriseId);

	/**
	 * getEnterpriseSimpleItemList: 获取所有企业基本信息，主要用于下拉框. <br/>
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprises/simple")
	@ResponseBody
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterpriseSimpleItemList();
	
	/**
	 * createEnterprise:创建企业. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise")
	@ResponseBody
	public GeneralContentResult<String> createEnterprise(@RequestBody EnterpriseProfileReq _profile);

	/**
	 * updateEnterprise:更新企业信息，对于profile中是空的字段，则忽略掉. <br/>
	 *
	 * @param _enterpriseId
	 * @param _profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprises/{_enterpriseId}")
	@ResponseBody
	public GeneralResult updateEnterprise(@PathVariable("_enterpriseId") String _enterpriseId,
			@RequestBody EnterpriseProfile _profile);

	/**
	 * updateEnterpriseStatusByBatch:批量更新企业状态. <br/>
	 *
	 * @param _list: ID List
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprises/status/{_status}")
	@ResponseBody
	public GeneralResult updateEnterpriseStatusByBatch(@RequestBody List<String> _list,
			@PathVariable("_status") Byte _status);

	/**
	 * getEnterpriseAdAdminListByEnt:分页获取某个企业下的所有AD Admin列表. <br/>
	 *
	 * @param _enterpriseId
	 * @param _page
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/{_enterpriseId}/adadmins/paging")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseAdAdminItem>> getEnterpriseAdAdminListByEnt(
			@PathVariable("_enterpriseId") String _enterpriseId, @RequestBody PageParameter _page);

	/**
	 * updateEnterpriseStatus:更新某个企业状态. <br/>
	 *
	 * @param _enterpriseId
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/{_enterpriseId}/status/{_status}")
	@ResponseBody
	public GeneralResult updateEnterpriseStatus(@PathVariable("_enterpriseId") String _enterpriseId,
			@PathVariable("_status") Byte _status);

	/**
	 * testEnterpriseADConnection: 测试企业AD连通性. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/adplatform/connectivity")
	@ResponseBody
	public GeneralResult testEnterpriseADConnection(EnterpriseProfile _profile);

	/**
	 * createEnterpriseAdmin:创建企业管理员. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/admin",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public GeneralContentResult<String> createEnterpriseAdmin(@RequestBody EnterpriseAdminProfile _profile);

	/**
	 * getEnterpriseAdmin: 获取某个管理员. <br/>
	 *
	 * @param _adminId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprise/admin/{_adminId}")
	@ResponseBody
	public GeneralPagingResult<EnterpriseAdminItem> getEnterpriseAdmin(@PathVariable("_adminId") String _adminId);

	/**
	 * updateEnterpriseAdmin:更新某个企业管理员. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/admin",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public GeneralResult updateEnterpriseAdmin(@RequestBody EnterpriseAdminProfile _profile);

	/**
	 * updateEnterpriseAdminStatus:更新某个企业管理员状态. <br/>
	 *
	 * @param _adminId
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/admin/{_adminId}/status/{_status}")
	@ResponseBody
	public GeneralResult updateEnterpriseAdminStatus(@PathVariable("_adminId") String _adminId,
			@PathVariable("_status") Byte _status);

	/**
	 * updateEnterpriseAdminStatusByBatch: 批量更新企业管理员状态. <br/>
	 *
	 * @param _list
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprises/admins/status/{_status}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatusByBatch(@RequestBody List<String> _list,
			@PathVariable("_status") Byte _status);

	/**
	 * deleteEnterpriseAdmin:删除某个企业管理员. <br/>
	 *
	 * @param _adminId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/authsec/enterprise/admin/{_adminId}")
	@ResponseBody
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdmin(@PathVariable("_adminId") String _adminId);

	/**
	 * deleteEnterpriseAdminByBatch:批量删除企业管理员. <br/>
	 *
	 * @param _list
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/authsec/enterprises/admins")
	@ResponseBody
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdminByBatch(
			@RequestBody List<EnterpriseAdminProfile> _list);

	/**
	 * getEnterpriseAdminList:分页获取所有企业下的所有管理员. <br/>
	 *
	 * @return
	 */
	@RequestMapping(value = "/authsec/enterprises/admins/{page}/{size}", method = RequestMethod.GET)
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminList(@PathVariable("page") Integer page,
			@PathVariable("size") Integer size);

	/**
	 * getEnterpriseAdminByEnt:分页获取某个企业下的所有管理员. <br/>
	 *
	 * @param _enterpriseId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprise/{_enterpriseId}/admins/{page}/{size}")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminByEnt(
			@PathVariable("_enterpriseId") String _enterpriseId, @PathVariable("page") Integer page,
			@PathVariable("size") Integer size);

	/**
	 * getEnterpiseResourceQuota: 分页获取所有企业下所有资源的配额. <br/>
	 *
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/resources/quotas/paging")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpiseResourceQuota(
			@RequestBody PageParameter _request);

	/**
	 * getEnterpiseResourceQuotaByEnt:分页获取某个企业下的资源配额. <br/>
	 *
	 * @param _enterpriseId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/{_enterpriseId}/resources/quotas/paging")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpiseResourceQuotaByEnt(
			@PathVariable("_enterpriseId") String _enterpriseId, @RequestBody PageParameter _request);

	/**
	 * createEnterpriseQuota:批量创建企业资源配额. <br/>
	 *
	 * @param _quota
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/resources/quotas")
	@ResponseBody
	public GeneralPagingResult<List<EnterpriseQuotaItem>> createEnterpriseQuota(
			@RequestBody List<EnterpriseQuotaProfile> _quota);

	/**
	 * updateEnterpriseQuota: 更新企业的资源配额. <br/>
	 *
	 * @param _quota
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/{_enterpriseId}/resources/quotas")
	@ResponseBody
	public GeneralResult updateEnterpriseQuota(@PathVariable("_enterpriseId") String _enterpriseId,
			@RequestBody EnterpriseQuotaProfile _quota);

	/**
	 * createProduct: 创建单个产品. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/product")
	@ResponseBody
	public GeneralPagingResult<ProductItem> createProduct(@RequestBody ProductProfile _profile);

	/**
	 * createProductByBatch:批量创建产品. <br/>
	 *
	 * @param _product
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/products")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> createProductByBatch(@RequestBody List<ProductProfile> _product);

	/**
	 * getProductItem:获取所有企业下的所有产品. <br/>
	 *
	 * @param _conn: 检索条件，涵盖了分页参数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/products/search/paging")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getProductItem(@RequestBody ProductPageQueryConditionReq _conn);

	/**
	 * updateProductsStatusByBatch:批量更新产品状态. <br/>
	 *
	 * @param _product
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/products/status/{_status}")
	@ResponseBody
	public GeneralPagingResult<GeneralContent> updateProductsStatusByBatch(@RequestBody List<ProductProfile> _product,
			@PathVariable("_status") Byte _status);

	/**
	 * updateProductsStatus:更新某个产品的状态. <br/>
	 *
	 * @param _id
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/product/{_productId}/status/{_status}")
	@ResponseBody
	public GeneralPagingResult<GeneralContent> updateProductsStatus(@PathVariable("_productId") String _id,
			@PathVariable("_status") Byte _status);

	/**
	 * getProductItemByEnterpriseId:获取某个企业下的所有产品. <br/>
	 *
	 * @param _enterpriseId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/{_enterpriseId}/products/paging")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getProductItemByEnt(
			@PathVariable("_enterpriseId") String _enterpriseId, @RequestBody PageParameter _request);

	/**
	 * getProductItemByRegionId:依据区域来检索产品. <br/>
	 *
	 * @param _regionId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/region/{_regionId}/products/paging")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getProductItemByRegion(@PathVariable("_regionId") String _regionId,
			@RequestBody PageParameter _request);

	/**
	 * getEnterpriseProductByEntAndRegion:依据企业和区域来检索产品. <br/>
	 *
	 * @param _enterpriseId
	 * @param _regionId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,
			value = "/authsec/enterprise/{_enterpriseId}/region/{_regionId}/products/paging")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProductByEntAndRegion(
			@PathVariable("_enterpriseId") String _enterpriseId, @PathVariable("_regionId") String _regionId,
			@RequestBody PageParameter _request);



}
