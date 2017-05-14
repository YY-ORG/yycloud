/**
 * Project Name:fox-sysbase
 * File Name:OrgnizationRepository.java
 * Package Name:com.yy.cloud.core.sysbase.data.repositories
 * Date:Jul 7, 20165:02:46 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxOrganization;

@Repository
public interface FoxOrganizationRepository extends JpaRepository<FoxOrganization, String> {

	Page<FoxOrganization> findByStatus(Byte status, Pageable pageable);

	Page<FoxOrganization> findByTenantIdAndStatus(String tenantId, Byte status, Pageable pageable);

	List<FoxOrganization> findByTenantId(String tenantId);

	/**
	 * 过滤已删除部门
	 * 
	 * @param tenantId
	 * @param status
	 * @return
	 */
	List<FoxOrganization> findByTenantIdAndStatusLessThan(String tenantId, Byte status);

	Page<FoxOrganization> findByTenantIdAndStatusLessThan(String tenantId, Byte status, Pageable pageable);

	FoxOrganization findByTenantIdAndStatusAndCode(String tenantId, Byte status, String code);

	/**
	 * 查询后台机构
	 * 
	 * @param type
	 * @param status
	 * @return
	 */
	Page<FoxOrganization> findByTypeAndStatusLessThan(Byte type, Byte status, Pageable pageable);

	List<FoxOrganization> findByTypeAndStatusLessThan(Byte type, Byte status);

	Page<FoxOrganization> findByTypeAndStatus(Byte type, Byte status, Pageable pageable);

	@Query(value = "SELECT O.* FROM FOX_USER_ORGANIZATION UO LEFT JOIN FOX_ORGANIZATION O ON UO.ORGANIZATION_ID=O.ID WHERE UO.USER_ID = ?1", nativeQuery = true)
	List<FoxOrganization> findOrganizationByUserId(String userId);

	@Query("SELECT f FROM FoxOrganization f WHERE f.id IN (SELECT u.organizationId FROM FoxUserOrganization u WHERE u.userId=?1)")
	Page<FoxOrganization> findByUserId(String userId, Pageable pageable);

	@Query("SELECT f FROM FoxOrganization f WHERE f.id IN (?1)")
	List<FoxOrganization> findByIdIn(List<String> ids);

	@Modifying
	@Query("update FoxOrganization t set t.currency=?1,t.feeType=?2,t.feePeriod=?3,t.billGenDate=?4,t.billSendDate=?5,t.exitFeePolicy=?6,"
			+ "t.specialFeePolicy=?7,t.feeStartPolicy=?8,t.fiscalMonth=?9,t.fiscalDate=?10 where t.id=?11")
	public int setBillSettingFor(String currency, Byte feeType, Byte feePeriod, Byte billGenDate, Byte billSendDate,
			Byte exitFeePolicy, Byte specialFeePolicy, Byte feeStartPolicy, Byte fiscalMonth, Byte fiscalDate,
			String id);

	@Query("SELECT f FROM FoxOrganization f WHERE f.id IN (SELECT fot.organizationId FROM FoxOrganizationTenant fot WHERE fot.tenantId=?1)")
	public List<FoxOrganization> findByChildTenantId(String tenantId);

}
