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

import com.yy.cloud.core.usermgmt.data.domain.YYOrganization;

@Repository
public interface YYOrganizationRepository extends JpaRepository<YYOrganization, String> {

	Page<YYOrganization> findByStatus(Byte status, Pageable pageable);
	
	@Query(value = "SELECT O.* FROM yy_user_organization UO LEFT JOIN yy_organization O ON UO.ORGANIZATION_ID=O.ID WHERE UO.USER_ID = ?1", nativeQuery = true)
	List<YYOrganization> findOrganizationByUserId(String userId);
	
	
	
	

	@Query("SELECT f FROM YYOrganization f WHERE f.id IN (SELECT u.organizationId FROM YYUserOrganization u WHERE u.userId=?1)")
	Page<YYOrganization> findByUserId(String userId, Pageable pageable);

	@Query("SELECT f FROM YYOrganization f WHERE f.id IN (?1)")
	List<YYOrganization> findByIdIn(List<String> ids);
	
	
	
	/**
	 * 查询后台机构
	 * 
	 * @param type
	 * @param status
	 * @return
	 */
	Page<YYOrganization> findByStatusLessThan( Byte status, Pageable pageable);

	List<YYOrganization> findStatusLessThan( Byte status);

	Page<YYOrganization> findAndStatus( Byte status, Pageable pageable);




	



}
