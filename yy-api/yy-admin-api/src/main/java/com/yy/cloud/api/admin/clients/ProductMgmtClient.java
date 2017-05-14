/**
 * Project Name:liz-enterprisemgmt
 * File Name:ProductMgmtClient.java
 * Package Name:com.gemii.lizcloud.composite.enterprisemgmt.clients
 * Date:Oct 6, 20169:55:54 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.api.admin.clients;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.ProductNewProfile;
import com.yy.cloud.common.data.dto.enterprise.ProductPageQueryConditionReq;
import com.yy.cloud.common.data.dto.enterprise.ProductStatusReq;
import com.yy.cloud.common.data.enterpise.ProductProfile;
import com.yy.cloud.common.data.otd.enterprise.CommonKeyValue;
import com.yy.cloud.common.data.otd.enterprise.ProductItem;
import com.yy.cloud.common.data.otd.enterprise.ProductSumItem;

import java.util.List;

/**
 * ClassName:ProductMgmtClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Oct 6, 2016 9:55:54 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@FeignClient("productmgmt")
public interface ProductMgmtClient {
	/**
	 * getAllEnterpriseProduct:获取所有企业的所有产品列表. <br/>
	 *
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/products/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getAllEnterpriseProduct(@RequestBody PageParameter _request);

	/**
	 * getEnterpriseProduct:获取某个企业下所有产品列表. <br/>
	 *
	 * @param _enterpriseId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/{_enterpriseId}/products/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProduct(
			@PathVariable("_enterpriseId") String _enterpriseId, @RequestBody PageParameter _request);

	/**
	 * getEnterpriseProductSum: 依据企业ID找到企业下面产品的个数. <br/>
	 * 返回的数据集，请按照入参企业id的顺序返回. <br/>
	 *
	 * @param _enterpriseIds
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/products/sum", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<List<ProductSumItem>> getEnterpriseProductSum(@RequestBody List<String> _enterpriseIds);

	/**
	 * getEnterpriseProductByRegion:获取某个Region（映射到Platform）下所有产品. <br/>
	 *
	 * @param _regionId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/region/{_regionId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProductByRegion(
			@PathVariable("_regionId") String _regionId, @RequestBody PageParameter _request);

	/**
	 * getEnterpriseProductByEntAndRegion: 依据企业和Region来检索产品. <br/>
	 *
	 * @param _enterpriseId
	 * @param _regionId
	 * @param _request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/{_enterpriseId}/region/{_regionId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProductByEntAndRegion(
			@PathVariable("_enterpriseId") String _enterpriseId, @PathVariable("_regionId") String _regionId,
			@RequestBody PageParameter _request);

	/**
	 * createProduct: 创建产品. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	// URL 定义与方法签名不匹配。将_enterpriseId 替换成固定值0
	// @RequestMapping(method = RequestMethod.POST, value =
	// "/authsec/enterprise/{_enterpriseId}/product",produces =
	// MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/0/product", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<ProductItem> createProduct(@RequestBody ProductProfile _profile);

	/**
	 * createProductsByBatch:批量创建产品. <br/>
	 *
	 * @param _list
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprise/0/products/batch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> createProductsByBatch(@RequestBody List<ProductProfile> _list);

	/**
	 * updateProductStatus:更新某个产品状态. <br/>
	 *
	 * @param _productId
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/product/{_productId}/status/{_status}")
	@ResponseBody
	public GeneralPagingResult<GeneralContent> updateProductStatus(@PathVariable("_productId") String _productId,
			@PathVariable("_status") Byte _status);

	/**
	 * updateProductStatusByBatch:批量更新产品状态. <br/>
	 *
	 * @param _list
	 * @param _status
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/authsec/enterprise/products/status/{_status}/batch")
	@ResponseBody
	public GeneralPagingResult<GeneralContent> updateProductStatusByBatch(@RequestBody List<String> _list,
			@PathVariable("_status") Byte _status);

	/**
	 * getEnterpriseProductSum: 依据企业ID找到企业下面已开通平台的区域总数. <br/>
	 * 返回的数据集，请按照入参企业id的顺序返回. <br/>
	 *
	 * @param _enterpriseIds
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/regions/sum")
	@ResponseBody
	public GeneralPagingResult<List<Integer>> getEnterpriseRegionSum(@RequestBody List<String> _enterpriseIds);

	/**
	 * getProductItem:获取所有企业下的所有产品. <br/>
	 *
	 * @param _conn:
	 *            检索条件，涵盖了分页参数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/products/search/paging")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getAllEnterpriseProduct(
			@RequestBody ProductPageQueryConditionReq _conn);

	/**
	 * 新建产品
	 * 
	 * @param productProfile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/product")
	@ResponseBody
	GeneralContentResult<String> createProduct(@RequestBody ProductNewProfile productProfile);

	/**
	 * 获取产品
	 * 
	 * @param _productId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/authsec/enterprises/product/{_productId}")
	@ResponseBody
	GeneralContentResult<ProductNewProfile> getProduct(@PathVariable("_productId") String _productId);

	/**
	 *
	 * @param enterpriseId
	 *            企业id
	 * @param pageParameter
	 *            分页信息
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/{enterpriseId}/products/search/paging")
	@ResponseBody
	public GeneralPagingResult<List<ProductItem>> getAllUnselectedEnterpriseProduct(
			@PathVariable("enterpriseId") String enterpriseId, @RequestBody PageParameter pageParameter);

	/**
	 *
	 * @param binditemList
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/enterprises/{enterpriseId}/products/bind")
	@ResponseBody
	@ApiOperation(value = "绑定企业与产品关系")
	GeneralContentResult<List<String>> bindEnterpriseProduct(@PathVariable("enterpriseId") String enterpriseId,
			@RequestBody List<String> productIds);

	/**
	 *
	 * @param binditemList
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/authsec/products/{productId}/enterprises/bind")
	@ResponseBody
	@ApiOperation(value = "绑定产品与企业关系")
	GeneralContentResult<List<String>> bindProductEnterprise(@PathVariable("productId") String productId,
			@RequestBody List<String> enterpriseIds);

}
