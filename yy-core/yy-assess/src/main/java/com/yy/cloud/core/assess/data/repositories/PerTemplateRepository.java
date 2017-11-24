/**
 * Project Name:yy-assess
 * File Name:PerTemplateRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:04:00 PM
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

import com.yy.cloud.core.assess.data.domain.PerTemplate;

import java.util.List;

/**
 * ClassName:PerTemplateRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:04:00 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perTemplate", path = "perTemplate")
public interface PerTemplateRepository extends JpaRepository<PerTemplate, String> {
    @Query(value = "SELECT p FROM PerTemplate p, PerAssessTemplateMap ptm where p.id = ptm.templateId and ptm.assessId = :assessId")
    List<PerTemplate> getListByAssess(@Param("assessId") String _assess);

    Page<PerTemplate> findByTypeAndStatusOrderByNameAsc(Byte _type, Byte _status, Pageable _page);

    List<PerTemplate> findByTypeAndStatusOrderByNameAsc(Byte _type, Byte _status);
}

