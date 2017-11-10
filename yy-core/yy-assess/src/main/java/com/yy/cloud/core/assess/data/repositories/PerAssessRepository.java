/**
 * Project Name:yy-assess
 * File Name:PerAssessRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20176:32:55 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAssess;

/**
 * ClassName:PerAssessRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 6:32:55 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAssess", path = "perAssess")
public interface PerAssessRepository extends JpaRepository<PerAssess, String> {
    @Query(value = "SELECT pa.id, pa.code, pa.name, pa.status, pa.type FROM PerAssess pa, PerAssessAspMap pam where pa.id = pam.assessId and pam.assessPaperId = :assessPaperId")
    Page<PerAssess> getAssessByAsp(@Param("assessPaperId") String _assessPaperId, Pageable _page);
}

