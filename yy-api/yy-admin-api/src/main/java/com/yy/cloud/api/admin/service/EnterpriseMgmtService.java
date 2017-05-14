/**
 * Project Name:liz-admin
 * File Name:EnterpriseMgmtService.java
 * Package Name:com.gemii.lizcloud.api.admin.service
 * Date:Sep 30, 20163:44:27 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.api.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.enterprise.*;
import com.yy.cloud.common.data.enterpise.ProductProfile;
import com.yy.cloud.common.data.otd.enterprise.*;
import com.yy.cloud.common.data.usermgmt.TenantModel;

/**
 * ClassName:EnterpriseMgmtService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 30, 2016 3:44:27 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
public interface EnterpriseMgmtService {

	/**
	 * getEnterpriseItemList:获取企业列表. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseOpeningItem>> getEnterpriseItemList(Integer _page, Integer _size);

	/**
	 * createEnterprise: 创建企业. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralContentResult<String> createEnterprise(EnterpriseProfileReq _profile);

	/**
	 * testEnterpriseADConnection:测试企业AD连通性. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralResult testEnterpriseADConnection(EnterpriseProfile _profile);

	/**
	 * updateEnterpriseStatus:批量更新企业状态. <br/>
	 *
	 * @param _list
	 *            : 企业ID
	 * @param _status
	 * @return
	 */
	public GeneralResult updateEnterpriseStatusByBatch(List<String> _list, Byte _status);

	/**
	 * updateEnterpriseStatus:更新某个企业状态. <br/>
	 *
	 * @param _id
	 * @param _status
	 * @return
	 */
	public GeneralResult updateEnterpriseStatus(String _enterpriseId, Byte _status);

	/**
	 * updateEnterpriseBasicProfile:更新某个企业的基本信息. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralResult updateEnterpriseBasicProfile(String _enterpriseId, EnterpriseBasicProfile _profile);

	/**
	 * updateEnterpriseAuthProfile:更新企业Auth信息. <br/>
	 *
	 * @param _enterpriseId
	 * @param _profile
	 * @return
	 */
	public GeneralResult updateEnterpriseAuthProfile(String _enterpriseId, EnterpriseAuthProfile _profile);

	/**
	 * createEnterpiseAdmin:创建企业管理员. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralContentResult<String> createEnterpriseAdmin(String _enterpriseId, EnterpriseAdminProfile _profile);

	/**
	 * getEnterpriseAdmin: 获取某个企业管理员. <br/>
	 *
	 * @param _adminId
	 * @return
	 */
	public GeneralPagingResult<EnterpriseAdminItem> getEnterpriseAdmin(String _adminId);

	/**
	 * updateEnterpiseAdmin:更新企业管理员. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralResult updateEnterpiseAdmin(EnterpriseAdminProfile _profile);

	/**
	 * updateEnterpiseAdminCredential:(Description). <br/>
	 *
	 * @param _adminId
	 * @return
	 */
	public GeneralResult updateEnterpiseAdminCredential(String _adminId);

	/**
	 * updateEnterpiseAdmin:更新企业管理员状态. <br/>
	 *
	 * @param _id
	 * @param _status
	 * @return
	 */
	public GeneralResult updateEnterpriseAdminStatus(String _id, Byte _status);

	/**
	 * updateEnterpiseAdminStatusByBatch:批量更新管理状态. <br/>
	 *
	 * @param _id
	 * @param _status
	 * @return
	 */
	public GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatusByBatch(List<String> _list, Byte _status);

	/**
	 * deleteEnterpiseAdmin:删除企业管理员. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdmin(String _id);

	/**
	 * deleteEnterpriseAdminByBatch:批量删除企业管理员. <br/>
	 *
	 * @param _list
	 * @return
	 */
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdminByBatch(List<EnterpriseAdminProfile> _list);

	/**
	 * getEnterpriseAdminList: 获取所有企业管理员. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminList(Integer _page, Integer _size);

	/**
	 * getEnterpriseAdminListByEnt:依据企业来检索企业管理员. <br/>
	 *
	 * @param _enterpriseId
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminListByEnt(String _enterpriseId,
			Integer _page, Integer _size);

	/**
	 * getEnterpriseAdAdminListByEnt:依据企业检索企业Ad列表. <br/>
	 *
	 * @param _enterpriseId
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseAdAdminItem>> getEnterpriseAdAdminListByEnt(String _enterpriseId,
			Integer _page, Integer _size);

	/**
	 * getEnterpiseResourceQuota:检索所有企业的ResourceQuota. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpriseResourceQuota(Integer _page, Integer _size);

	/**
	 * getEnterpiseResourceQuotaList:依据企业来检索ResourceQuota. <br/>
	 *
	 * @param _enterpriseId
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpriseResourceQuotaByEnterpriseId(String _enterpriseId,
			Integer _page, Integer _size);

	/**
	 * updateEnterpriseQuota:更新某个企业的Resouce Quota. <br/>
	 *
	 * @param _enterpriseId
	 * @param _quota
	 * @return
	 */
	public GeneralResult updateEnterpriseQuota(String _enterpriseId, EnterpriseQuotaProfile _quota);

	/**
	 * createProduct:创建单个产品. <br/>
	 *
	 * @param _profile
	 * @return
	 */
	public GeneralPagingResult<ProductItem> createProduct(ProductProfile _profile);

	/**
	 * createProducts: 批量创建产品. <br/>
	 *
	 * @param _product
	 * @return
	 */
	public GeneralPagingResult<List<ProductItem>> createProducts(List<ProductProfile> _product);

	/**
	 * getProductItem:获取所有产品列表. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<ProductItem>> getProductItem(Integer _page, Integer _size,
			ProductQueryConditionReq _conn);

	/**
	 * updateProductsStatus: 批量更新产品状态. <br/>
	 *
	 * @param _product
	 * @param _status
	 * @return
	 */
	public GeneralPagingResult<GeneralContent> updateProductsStatusByBatch(List<ProductProfile> _product, Byte _status);

	/**
	 * updateProductsStatus:更新某个产品的状态. <br/>
	 *
	 * @param _id
	 * @param _status
	 * @return
	 */
	public GeneralPagingResult<GeneralContent> updateProductsStatus(String _id, Byte _status);

	/**
	 * getProductItemByEnterpriseId:依据企业来检索产品. <br/>
	 *
	 * @param _enterpriseId
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<ProductItem>> getProductItemByEnterpriseId(String _enterpriseId, Integer _page,
			Integer _size);

	/**
	 * getProductItemByRegionId:依据区域来检索产品. <br/>
	 *
	 * @param _regionId
	 * @return
	 */
	public GeneralPagingResult<List<ProductItem>> getProductItemByRegionId(String _regionId, Integer _page,
			Integer _size);

	/**
	 * getEnterpriseProductByEntAndRegion:依据企业和区域来检索产品列表. <br/>
	 *
	 * @param _enterpriseId
	 * @param _regionId
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProductByEntAndRegion(String _enterpriseId,
			String _regionId, Integer _page, Integer _size);

	/**
	 * getEnterpriseSimpleItem: 获取单个企业的基本信息. <br/>
	 *
	 * @param _enterpriseId
	 * @return
	 */
	public GeneralContentResult<EnterpriseGeneralItem> getEnterpriseGeneralItem(String _enterpriseId);

	/**
	 * getEnterpriseGeneralItem: 获取企业基本信息的列表，主要用于UI下拉框. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterpriseSimpleItemList();

	/**
	 * getEnterprisePandectItem:获取企业总览信息的列表，主要用于概览页面. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseOpeningItem>> getEnterpriseOpeningItem(Integer _page, Integer _size);

	/**
	 * getEnterpriseGeneralItemList:获取所有企业的基本信息. <br/>
	 *
	 * @param _page
	 * @param _size
	 * @return
	 */
	public GeneralPagingResult<List<EnterpriseGeneralItem>> getEnterpriseGeneralItemList(Integer _page, Integer _size);

	/**
	 * getEnterpriseSimpleItem:获取所有企业的基本信息，主要用于下拉框. <br/>
	 *
	 * @return
	 */
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterpriseSimpleItem();

	/**
	 * 根据当前用户的角色获取企业
	 * @param userId
	 * @return
	 */
	GeneralContentResult<List<TenantModel>> getEnterpriseByUserRole( String userId);

	/**
	 * 获取企业的部门
	 * @param enterpriseId
	 * @return
	 */
	GeneralContentResult<List<DepartmentItem>> getDepartmentByEnterpriseId(String enterpriseId);

	/**
	 * 更新企业配额的使用量
	 * @param resourceQuotaUsageDetailProfiles
	 * @return
	 */
	GeneralContentResult<List<String>> enterpriseUsageQuotaCaculate(List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles);


	/**
	 * 获取企业配额详情
	 * @param enterpriseId
	 * @return
	 */
	GeneralContentResult<List<EnterpriseQuotaDetailResp>> getEnterpriseQuotaDetail(String enterpriseId);

	/**
	 * BOE 对接接口，获取某个平台关联的所有企业
	 * @param platformId
	 * @return
	 */
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterprisesByPlatformId(String platformId);
}
