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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYOrganization;
import java.lang.String;

/**
 * @author chenxj
 */
@Repository
public interface YYOrganizationRepository extends JpaRepository<YYOrganization, String> {
	
	@Query(value = "SELECT O.* FROM yy_user_organization UO LEFT JOIN yy_organization O ON UO.ORGANIZATION_ID=O.ID WHERE UO.USER_ID = ?1", nativeQuery = true)
	List<YYOrganization> findOrganizationByUserId(String userId);

	/**
	 * To fetch the organizations with status.
	 *
	 * @param status
	 * @param pageable
	 * @return
	 */
	Page<YYOrganization> findByStatusLessThan( Byte status, Pageable pageable);

	/**
	 * To fetch the organizations with status And name.
	 *
	 * @param status
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<YYOrganization> findByStatusLessThanAndNameLike(Byte status, String name, Pageable pageable);

	List<YYOrganization> findByStatusLessThan( Byte status);

	/**
	 * To fetch the organizations with status.
	 *
	 * @param status
	 * @param pageable
	 * @return
	 */
	Page<YYOrganization> findByStatus( Byte status, Pageable pageable);

	/**
	 * To fetch the organizations with status And name.
	 *
	 * @param status
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<YYOrganization> findByStatusAndNameLike( Byte status, String name, Pageable pageable);

	
	List<YYOrganization> findByNameAndStatusLessThan(String name, Byte status);
}
