/**
 * Project Name:liz-sysbase
 * File Name:SystemDictionaryRepository.java
 * Package Name:com.gcbi.cloud.core.sysbase.data.repositories
 * Date:May 10, 20161:15:48 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.sysbase.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.sysbase.data.domain.SSysDictionary;

import java.util.List;

/**
 * ClassName:SystemDictionaryRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: May 10, 2016 1:15:48 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@RepositoryRestResource(collectionResourceRel = "sysdic", path = "sysdic")
public interface SystemDictionaryRepository extends JpaRepository<SSysDictionary, Long> {
//	@Cacheable(value = "sysdicownercache", keyGenerator = "wiselyKeyGenerator")
	List<SSysDictionary> findByOwner(@Param("_owner") String _owner);

//	@Cacheable(value = "sysdicownerfieldcache", keyGenerator = "wiselyKeyGenerator")
	List<SSysDictionary> findByOwnerAndField(@Param("_owner") String _owner, @Param("_field") String _field);

//	@Cacheable(value = "sysdicidcache", keyGenerator = "wiselyKeyGenerator")
	List<SSysDictionary> findByOwnerAndFieldAndCode(@Param("_owner") String _owner, @Param("_field") String _field, @Param("_code") String _code);
	
}
