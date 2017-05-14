/**
 * Project Name:liz-admin
 * File Name:EnterpriseMgmtServiceImpl.java
 * Package Name:com.gemii.lizcloud.api.admin.service.impl
 * Date:Sep 30, 20166:09:04 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.*;
import com.yy.cloud.api.admin.service.EnterpriseMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.PageParameter;
import com.yy.cloud.common.data.dto.enterprise.*;
import com.yy.cloud.common.data.enterpise.ProductProfile;
import com.yy.cloud.common.data.otd.enterprise.*;
import com.yy.cloud.common.data.usermgmt.TenantModel;
import com.yy.cloud.common.utils.AssertHelper;
import com.yy.cloud.common.utils.reflect.ReflectUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:EnterpriseMgmtServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 30, 2016 6:09:04 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class EnterpriseMgmtServiceImpl implements EnterpriseMgmtService {
	@Autowired
	private EnterpriseMgmtClient enterpriseMgmtClient;

	@Autowired
	private UserMgmtClient userMgmtClient;

	@Autowired
	private ResourceMgmtClient resourceMgmtClient;

	@Autowired
	private ProductMgmtClient productMgmtClient;
	
	@Autowired
	private SubInstanceMgmtClient subInstanceMgmtClient;

	@Autowired
	private EnterrpiseCoreMgmtClient enterrpiseCoreMgmtClient;

	@Autowired
	private PlatformAccessClient platformAccessClient;

	@Override
	public GeneralPagingResult<List<EnterpriseOpeningItem>> getEnterpriseItemList(Integer _page, Integer _size) {
		log.debug("Going to retrieve the enterprise opening info from downstream service...");
		PageParameter tempRequest = new PageParameter(this.getDBPageNo(_page), _size);
		GeneralPagingResult<List<EnterpriseOpeningItem>> tempResult = this.enterpriseMgmtClient
				.getEnterpriseOpeningItem(tempRequest);
		// tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@Override
	public GeneralContentResult<String> createEnterprise(EnterpriseProfileReq _profile) {

		log.debug(CommonConstant.LOG_DEBUG_TAG+"创建企业 {}",_profile);
		GeneralContentResult<String> result= new GeneralContentResult<>();
		EnterpriseQuotaProfile enterpriseQuotaProfile = _profile.getQuotaList();

		List<PlatformQuotaUsedProfile> platformQuotaUsedProfiles = new ArrayList<PlatformQuotaUsedProfile>();

		//CPU
		PlatformQuotaUsedProfile platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("0"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getVCpuQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//MEM
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("1"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getMemQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//DISK
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("2"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getDiskQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//STORAGE
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("3"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getStorageQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//VM
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("4"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getVmQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//NETWORK
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("5"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getNetworkQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//SNAPSHOT
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("6"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getSnapShotQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//IMAGE
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("7"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getImageQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//PHYSICAL_MACHINE
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("8"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getPhysicalMachineQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		//DYNAMIC IP
		platformQuotaUsedProfile = new PlatformQuotaUsedProfile();
		platformQuotaUsedProfile.setPlatformId(enterpriseQuotaProfile.getId());
		platformQuotaUsedProfile.setActionType(new Byte("1"));
		platformQuotaUsedProfile.setType(new Byte("9"));
		platformQuotaUsedProfile.setUsedValue(enterpriseQuotaProfile.getFloatIpQuota());
		platformQuotaUsedProfiles.add(platformQuotaUsedProfile);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"判断平台是否有足够资源 {}",platformQuotaUsedProfiles);
		//判断平台是否有足够的配额
		GeneralContentResult<PlatformQuotaUsedProfile> createEnterprisePermit = platformAccessClient.validateAndUpdatePlatformUsedQuota(platformQuotaUsedProfiles);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"判断平台是否有足够资源返回结果",createEnterprisePermit);


		if(createEnterprisePermit.getResultCode().equals(ResultCode.ENTERPRISE_QUOTA_VALIDATE_FAILED)){
			result.setResultCode(ResultCode.ENTERPRISE_QUOTA_VALIDATE_FAILED);
			result.setDetailDescription("platform can't provide enough quota!");
			return result;
		}
		log.debug(CommonConstant.LOG_DEBUG_TAG+"平台资源判断结果 {}",createEnterprisePermit);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"开始创建企业 {}",_profile);
		GeneralPagingResult<EnterpriseGeneralItem> item = userMgmtClient.createEnterprise(_profile.getProfile());
		List<EnterpriseQuotaProfile> quotas = new ArrayList<>();
		_profile.getQuotaList().setEnterpriseId(item.getResultContent().getId());
		quotas.add(_profile.getQuotaList());
		log.debug(CommonConstant.LOG_DEBUG_TAG+"创建企业配额",quotas);
		resourceMgmtClient.createResourceQuotaByBatch(quotas);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"创建企业配额 完毕");
		if(item.getResultContent().getId()!=null && !item.getResultContent().getId().isEmpty())
			result.setResultContent(item.getResultContent().getId());
		else
			result.setResultContent("success");
		return result;
	}

	@Override
	public GeneralResult updateEnterpriseStatusByBatch(List<String> _list, Byte _status) {
		GeneralResult tempResult = this.enterpriseMgmtClient.updateEnterpriseStatusByBatch(_list, _status);
		return tempResult;
	}

	@Override
	public GeneralResult updateEnterpriseStatus(String _id, Byte _status) {
		this.userMgmtClient.updateEnterpriseStatus(_id, _status);
		GeneralResult tempResult = new GeneralResult();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
//		GeneralResult tempResult = this.enterpriseMgmtClient.updateEnterpriseStatus(_id, _status);
		return tempResult;
	}

	@Override
	public GeneralContentResult<String> createEnterpriseAdmin(String _enterpriseId, EnterpriseAdminProfile _profile) {
//		GeneralContentResult<String> tempResult = this.enterpriseMgmtClient.createEnterpriseAdmin(_profile);
		GeneralPagingResult<EnterpriseAdminItem> temp=this.userMgmtClient.createEnterpriseAdmin(_enterpriseId, _profile);
		GeneralContentResult<String> tempResult = new GeneralContentResult<>();
		if(null != temp){
			tempResult.setResultCode(temp.getResultCode());
			if(null != temp.getResultContent() && ResultCode.OPERATION_SUCCESS.equals(temp.getResultCode())){
				tempResult.setResultContent(temp.getResultContent().getId());
			}
		}
		return tempResult;
	}

	@Override
	public GeneralPagingResult<EnterpriseAdminItem> getEnterpriseAdmin(String _adminId) {
//		GeneralPagingResult<EnterpriseAdminItem> tempResult = this.enterpriseMgmtClient.getEnterpriseAdmin(_adminId);
		GeneralPagingResult<EnterpriseAdminItem> tempResult = this.userMgmtClient.getEnterpriseAdminById(_adminId);
		return tempResult;
	}

	@Override
	public GeneralResult updateEnterpiseAdmin(EnterpriseAdminProfile _profile) {
//		GeneralResult tempResult = this.enterpriseMgmtClient.updateEnterpriseAdmin(_profile);
		GeneralPagingResult<GeneralContent> tempResult = this.userMgmtClient.updateEnterpriseAdmin(_profile);
		GeneralResult result= new GeneralResult();
		result.setResultCode(ResultCode.OPERATION_SUCCESS);
		return result;
	}

	@Override
	public GeneralResult updateEnterpriseAdminStatus(String _id, Byte _status) {
		GeneralPagingResult<GeneralContent> tempResult = this.userMgmtClient.updateEnterpriseAdminStatus(_id, _status);
		GeneralResult result= new GeneralResult();
		result.setResultCode(ResultCode.OPERATION_SUCCESS);
		return result;
	}

	@Override
	public GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatusByBatch(List<String> _list, Byte _status) {
		List<EnterpriseAdminProfile> list= new ArrayList<>();
		_list.forEach(x->{
			EnterpriseAdminProfile enterpriseAdminProfile = new EnterpriseAdminProfile();
			enterpriseAdminProfile.setId(x);
			list.add(enterpriseAdminProfile);
		});
		GeneralPagingResult<GeneralContent> tempResult = this.userMgmtClient
				.updateEnterpriseAdminStatusByBatch(list, _status);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdmin(String _id) {
//		GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtClient.deleteEnterpriseAdmin(_id);
		GeneralPagingResult<GeneralContent> tempResult = this.userMgmtClient.deleteEnterpriseAdmin(_id);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<GeneralContent> deleteEnterpriseAdminByBatch(List<EnterpriseAdminProfile> _list) {
		log.debug("Going to delete the admins by batch..");
		GeneralPagingResult<GeneralContent> tempResult = this.userMgmtClient.deleteEnterpriseAdminByBatch(_list);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminList(Integer _page, Integer _size) {
		GeneralPagingResult<List<EnterpriseAdminItem>> tempResult = this.userMgmtClient
				.getEnterpriseAdminList(this.getDBPageNo(_page), _size);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminListByEnt(String _enterpriseId,
			Integer _page, Integer _size) {
		GeneralPagingResult<List<EnterpriseAdminItem>> tempResult = this.userMgmtClient
				.getEnterpriseAdminListByEnt(_enterpriseId, this.getDBPageNo(_page), _size);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpriseResourceQuota(Integer _page, Integer _size) {

		//获取企业基本信息
		log.debug(CommonConstant.LOG_DEBUG_TAG+"查询所有企业的基本信息");
		PageParameter pageParameter = new PageParameter(_page-1,_size);
		LinkedHashMap<String,String> sorts= new LinkedHashMap<>();
		sorts.put("createDate","desc");
		pageParameter.setSort(sorts);
		List<EnterpriseQuotaItem> tempList = new ArrayList<>();

		GeneralPagingResult<List<EnterpriseGeneralItem>> entResult = userMgmtClient.getEnterpriseGeneralItemList(pageParameter);
		PageInfo pageInfo = null;
		
		if(AssertHelper.notEmpty(entResult)){
			List<EnterpriseGeneralItem> enterpriseGeneralItems = entResult.getResultContent();
			if(AssertHelper.notEmpty(enterpriseGeneralItems) && enterpriseGeneralItems.size()>0){
				List<String> enterpriseIds = new ArrayList<>();
				enterpriseGeneralItems.forEach(x->{
					enterpriseIds.add(x.getId());
				});
				
				//获取配额
				log.debug(CommonConstant.LOG_DEBUG_TAG+"查询所有企业"+enterpriseIds.toString()+"的配额");
				GeneralPagingResult<List<EnterpriseQuotaItem>> quotaResult=resourceMgmtClient.getResourceQuotaByEnts(enterpriseIds);
				
//				//获取主机数量
//				log.debug(CommonConstant.LOG_DEBUG_TAG+"查询所有企业"+enterpriseIds.toString()+"的主机数量");
//				GeneralContentResult<List<CommonKeyValue>> instances =subInstanceMgmtClient.getEnterprisedComputeInstance(enterpriseIds);
			
				//获取企业产品数量
				log.debug(CommonConstant.LOG_DEBUG_TAG+"查询企业"+enterpriseIds.toString()+"的产品数量");
				GeneralPagingResult<List<ProductSumItem>> proResult = productMgmtClient.getEnterpriseProductSum(enterpriseIds);

				for (EnterpriseGeneralItem item : enterpriseGeneralItems) {
					String id = item.getId();
					EnterpriseQuotaItem finalResult= new EnterpriseQuotaItem();

					if(AssertHelper.notEmpty(quotaResult)){
						List<EnterpriseQuotaItem> enterpriseQuotaItems = quotaResult.getResultContent();
						log.debug(CommonConstant.LOG_DEBUG_TAG+" 获取企业配额数据返回的条数和数据内容"+enterpriseGeneralItems.size(),enterpriseGeneralItems);
						if(AssertHelper.notEmpty(enterpriseQuotaItems) && enterpriseQuotaItems.size()>0){
							for (EnterpriseQuotaItem enterpriseQuotaItem : enterpriseQuotaItems) {
								if(id.equals(enterpriseQuotaItem.getEnterpriseId())){
									ReflectUtils.copyProperties(enterpriseQuotaItem, finalResult);
									finalResult.setAuthMode(enterpriseQuotaItem.getAuthMode());
									finalResult.setStatus(enterpriseQuotaItem.getStatus());
									break;
								}
							}			
						}
					}
					if(AssertHelper.notEmpty(proResult)){
						List<ProductSumItem> productSumItem = proResult.getResultContent();
						
						if(AssertHelper.notEmpty(productSumItem) && productSumItem.size()>0){
							for (ProductSumItem pro : productSumItem) {
								if(pro.getId().equals(id)){
									finalResult.setProductNumber(pro.getNumber());
									break;
								}
							}					
						}
					}
//					if(AssertHelper.notEmpty(instances)){
//						List<CommonKeyValue> instanceItems = instances.getResultContent();
//
//						if(AssertHelper.notEmpty(instanceItems) && instanceItems.size()>0){
//							for (CommonKeyValue ins : instanceItems) {
//								if(ins.getKey().equals(id)){
//									finalResult.setUsedVMNumber(ins.getNum());
//
//									int usedVMRate = 0;
//									int vmQuota = finalResult.getVmQuota();
//									if(AssertHelper.notEmpty(vmQuota) && vmQuota != 0){
//										usedVMRate = Math.round(ins.getNum()/vmQuota)*100;
//										finalResult.setUsedVMRate(usedVMRate);
//									}
//									break;
//								}
//							}
//						}
//					}
					finalResult.setEnterpriseId(item.getId());
					finalResult.setEnterpriseName(item.getName());
					finalResult.setAuthMode(item.getAuthMode());
					finalResult.setStatus(item.getStatus());
					tempList.add(finalResult);
				};		
			}
			pageInfo = entResult.getPageInfo();
		}
		
		GeneralPagingResult<List<EnterpriseQuotaItem>> tempResult = new GeneralPagingResult<>();
		tempResult.setPageInfo(pageInfo);
		tempResult.setResultContent(tempList);
		
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<EnterpriseQuotaItem>> getEnterpriseResourceQuotaByEnterpriseId(String _enterpriseId,
			Integer _page, Integer _size) {
		log.debug(CommonConstant.LOG_DEBUG_TAG+"查询企业"+_enterpriseId+"的配额 ");
		List<String> list = new ArrayList<>();
		list.add(_enterpriseId);
		PageParameter pageParameter = new PageParameter(_page-1,_size);

		List<EnterpriseQuotaItem> tempList= new ArrayList<>();
	
		//获取配额
		GeneralPagingResult<List<EnterpriseQuotaItem>> quotaResult=resourceMgmtClient.getResourceQuotaByEnts(list);
		
		//获取企业产品数量
		log.debug(CommonConstant.LOG_DEBUG_TAG+"查询企业"+_enterpriseId+"的产品数量");
		GeneralPagingResult<List<ProductSumItem>> proResult = productMgmtClient.getEnterpriseProductSum(list);
		
		//获取企业基本信息
		log.debug(CommonConstant.LOG_DEBUG_TAG+"查询企业"+_enterpriseId+"的基本信息");
		GeneralContentResult<EnterpriseGeneralItem> entResult = userMgmtClient.getEnterpriseInfo(_enterpriseId);
	
//		//获取主机数量
//		log.debug(CommonConstant.LOG_DEBUG_TAG+"查询所有企业"+_enterpriseId.toString()+"的主机数量");
//		GeneralContentResult<List<CommonKeyValue>> instances =subInstanceMgmtClient.getEnterprisedComputeInstance(list);

		EnterpriseQuotaItem finalResult= new EnterpriseQuotaItem();
		finalResult.setEnterpriseId(_enterpriseId);

		if(AssertHelper.notEmpty(quotaResult)){
			List<EnterpriseQuotaItem> enterpriseQuotaItems = quotaResult.getResultContent();
			
			if(AssertHelper.notEmpty(enterpriseQuotaItems) && enterpriseQuotaItems.size()>0){
				ReflectUtils.copyProperties(enterpriseQuotaItems.get(0), finalResult);
				finalResult.setAuthMode(enterpriseQuotaItems.get(0).getAuthMode());
				finalResult.setStatus(enterpriseQuotaItems.get(0).getStatus());
			}
		}
		
		if(AssertHelper.notEmpty(proResult)){
			List<ProductSumItem> productSumItem = proResult.getResultContent();
			
			if(AssertHelper.notEmpty(productSumItem) && productSumItem.size()>0){
				finalResult.setProductNumber(productSumItem.get(0).getNumber());
			}

		}
	
		if(AssertHelper.notEmpty(entResult)){
			EnterpriseGeneralItem enterpriseGeneralItem = entResult.getResultContent();
			
			if(AssertHelper.notEmpty(enterpriseGeneralItem)){
				finalResult.setAuthMode(enterpriseGeneralItem.getAuthMode());
				finalResult.setStatus(enterpriseGeneralItem.getStatus());
			}
		}
		
//		if(AssertHelper.notEmpty(instances)){
//			List<CommonKeyValue> instanceItems = instances.getResultContent();
//
//			if(AssertHelper.notEmpty(instanceItems) && instanceItems.size()>0){
//				CommonKeyValue ins = instanceItems.get(0);
//
//				finalResult.setUsedVMNumber(ins.getNum());
//
//				int usedVMRate = 0;
//				int vmQuota = finalResult.getVmQuota();
//				if(AssertHelper.notEmpty(vmQuota) && vmQuota != 0){
//					usedVMRate = Math.round(ins.getNum()/vmQuota)*100;
//					finalResult.setUsedVMRate(usedVMRate);
//				}
//			}
//		}

		//todo 百分比 订单数量 vm 已用数量在sprint3
//		finalResult.setUsedStorageNumber(0);
		tempList.add(finalResult);
	
		GeneralPagingResult<List<EnterpriseQuotaItem>> tempResult = new GeneralPagingResult<>();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		tempResult.setResultContent(tempList);
		
		return tempResult;
	}

	@Override
	public GeneralResult updateEnterpriseQuota(String _enterpriseId, EnterpriseQuotaProfile _quota) {
//		GeneralResult tempResult = this.enterpriseMgmtClient.updateEnterpriseQuota(_enterpriseId, _quota);
		List<EnterpriseQuotaProfile> updateItems = new ArrayList<>();
		updateItems.add(_quota);
		resourceMgmtClient.updateResourceQuota(updateItems);
		GeneralResult tempResult = new GeneralResult();
		tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<ProductItem> createProduct(ProductProfile _profile) {
		GeneralPagingResult<ProductItem> tempResult = this.enterpriseMgmtClient.createProduct(_profile);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<ProductItem>> createProducts(List<ProductProfile> _product) {
		GeneralPagingResult<List<ProductItem>> tempResult = this.enterpriseMgmtClient.createProductByBatch(_product);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<ProductItem>> getProductItem(Integer _page, Integer _size,
			ProductQueryConditionReq _conn) {
		ProductPageQueryConditionReq tempConn = new ProductPageQueryConditionReq();
		tempConn.setEnterpriseId(_conn.getEnterpriseId());
		tempConn.setServiceId(_conn.getServiceId());
		tempConn.setPlatformId(_conn.getPlatformId());
		tempConn.setPage(this.getDBPageNo(_page));
		tempConn.setSize(_size);

		GeneralPagingResult<List<ProductItem>> tempResult = this.enterpriseMgmtClient.getProductItem(tempConn);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<GeneralContent> updateProductsStatusByBatch(List<ProductProfile> _product,
			Byte _status) {
		GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtClient.updateProductsStatusByBatch(_product,
				_status);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<GeneralContent> updateProductsStatus(String _id, Byte _status) {
		GeneralPagingResult<GeneralContent> tempResult = this.enterpriseMgmtClient.updateProductsStatus(_id, _status);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<ProductItem>> getProductItemByEnterpriseId(String _enterpriseId, Integer _page,
			Integer _size) {
		GeneralPagingResult<List<ProductItem>> tempResult = this.enterpriseMgmtClient.getProductItemByEnt(_enterpriseId,
				new PageParameter(this.getDBPageNo(_page), _size));
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<ProductItem>> getProductItemByRegionId(String _regionId, Integer _page,
			Integer _size) {
		GeneralPagingResult<List<ProductItem>> tempResult = this.enterpriseMgmtClient.getProductItemByRegion(_regionId,
				new PageParameter(this.getDBPageNo(_page), _size));
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<ProductItem>> getEnterpriseProductByEntAndRegion(String _enterpriseId,
			String _regionId, Integer _page, Integer _size) {
		GeneralPagingResult<List<ProductItem>> tempResult = this.enterpriseMgmtClient
				.getEnterpriseProductByEntAndRegion(_enterpriseId, _regionId,
						new PageParameter(this.getDBPageNo(_page), _size));
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<EnterpriseGeneralItem>> getEnterpriseGeneralItemList(Integer _page, Integer _size) {
		GeneralPagingResult<List<EnterpriseGeneralItem>> tempResult = this.enterpriseMgmtClient
				.getEnterpriseGeneralItemList(new PageParameter(this.getDBPageNo(_page), _size));
		// tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	@Override
	public GeneralPagingResult<List<EnterpriseOpeningItem>> getEnterpriseOpeningItem(Integer _page, Integer _size) {
		GeneralPagingResult<List<EnterpriseOpeningItem>> tempResult = this.enterpriseMgmtClient
				.getEnterpriseOpeningItem(new PageParameter(this.getDBPageNo(_page), _size));
		// tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		return tempResult;
	}

	private Integer getDBPageNo(Integer _page) {
		Integer tempPage = _page - 1;
		return tempPage >= 0 ? tempPage : 0;
	}

	@Override
	public GeneralResult testEnterpriseADConnection(EnterpriseProfile _profile) {

		return this.enterpriseMgmtClient.testEnterpriseADConnection(_profile);
	}

	@Override
	public GeneralResult updateEnterpriseBasicProfile(String _enterpriseId, EnterpriseBasicProfile _profile) {

		TenantModel tenantModel= new TenantModel();
		tenantModel.setName(_profile.getName());
		tenantModel.setDescription(_profile.getDescription());
		tenantModel.setId(_enterpriseId);
		return this.userMgmtClient.updateEnterprise(tenantModel);
	}

	@Override
	public GeneralResult updateEnterpriseAuthProfile(String _enterpriseId, EnterpriseAuthProfile _profile) {
		EnterpriseProfile tempProfile = new EnterpriseProfile();
		tempProfile.setAuthenticationMode(_profile.getAuthMode());
		tempProfile.setUrl(_profile.getUrl());
		tempProfile.setUserName(_profile.getUserName());
		tempProfile.setPassword(_profile.getPassword());
		return this.enterpriseMgmtClient.updateEnterprise(_enterpriseId, tempProfile);
	}

	@Override
	public GeneralPagingResult<List<EnterpriseAdAdminItem>> getEnterpriseAdAdminListByEnt(String _enterpriseId,
			Integer _page, Integer _size) {

		return this.enterpriseMgmtClient.getEnterpriseAdAdminListByEnt(_enterpriseId,
				new PageParameter(this.getDBPageNo(_page), _size));
	}

	@Override
	public GeneralContentResult<EnterpriseGeneralItem> getEnterpriseGeneralItem(String _enterpriseId) {

		return this.userMgmtClient.getEnterpriseInfo(_enterpriseId);
	}

	@Override
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterpriseSimpleItem() {
		
		return this.enterpriseMgmtClient.getEnterpriseSimpleItemList();
	}

	@Override
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterpriseSimpleItemList() {
		PageParameter pageParameter = new PageParameter(0, Integer.MAX_VALUE);
		GeneralPagingResult<List<EnterpriseGeneralItem>> result= this.userMgmtClient.getEnterpriseGeneralItemList(pageParameter);
		List<EnterpriseSimpleItem> finalList= new ArrayList<>();
		if(result!=null && result.getResultContent()!=null && result.getResultContent().size()>0){
			result.getResultContent().forEach(x->{
				EnterpriseSimpleItem item = new EnterpriseSimpleItem();
				item.setId(x.getId());
				item.setCode(x.getCode());
				item.setName(x.getName());
				finalList.add(item);
			});
		}
		GeneralContentResult<List<EnterpriseSimpleItem>> finalRes= new GeneralContentResult<>();
		finalRes.setResultContent(finalList);
		return finalRes;
	}

	@Override
	public GeneralResult updateEnterpiseAdminCredential(String _adminId) {
		GeneralResult generalResult = new GeneralResult();
		generalResult.setResultCode(ResultCode.OPERATION_SUCCESS);
		// Reset the credential and send the mail to the user.
		return generalResult;
	}

	@Override
	public GeneralContentResult<List<TenantModel>> getEnterpriseByUserRole(@PathVariable("userId") String userId) {
		GeneralContentResult<List<TenantModel>> result = enterrpiseCoreMgmtClient.getEnterpriseByUserRole(userId);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"获取基于用户角色，所能查看的企业列表:",result);
		return result;
	}

	@Override
	public GeneralContentResult<List<DepartmentItem>> getDepartmentByEnterpriseId(String enterpriseId) {
		GeneralContentResult<List<DepartmentItem>> result=enterrpiseCoreMgmtClient.getDepartmentByEnterpriseId(enterpriseId);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"获取企业的部门信息：",result);
		return result;
	}

	@Override
	public GeneralContentResult<List<String>> enterpriseUsageQuotaCaculate(List<ResourceQuotaUsageDetailProfile> resourceQuotaUsageDetailProfiles) {
		return resourceMgmtClient.enterpriseUsageQuotaCaculate(resourceQuotaUsageDetailProfiles);
	}

	@Override
	public GeneralContentResult<List<EnterpriseQuotaDetailResp>> getEnterpriseQuotaDetail(String enterpriseId) {
		GeneralContentResult<List<EnterpriseQuotaDetailResp>> result= resourceMgmtClient.getEnterpriseQuotaDetail(enterpriseId);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"获取企业配额详情，返回数据",result);
		return result;
	}

	@Override
	public GeneralContentResult<List<EnterpriseSimpleItem>> getEnterprisesByPlatformId(String platformId) {
		GeneralContentResult<List<EnterpriseSimpleItem>> result=enterrpiseCoreMgmtClient.getEnterprisesByPlatformId(platformId);
		log.debug(CommonConstant.LOG_DEBUG_TAG+"返回与平台 {} 关联的企业信息为 {}",platformId,result);
		return result;
	}
}
