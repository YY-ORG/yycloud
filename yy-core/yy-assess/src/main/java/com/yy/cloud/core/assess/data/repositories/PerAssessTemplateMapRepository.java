/**
 * Project Name:yy-assess
 * File Name:PerAssessTemplateMapRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:02:27 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAssessTemplateMap;

import java.util.List;

/**
 * ClassName:PerAssessTemplateMapRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:02:27 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAssessTemplateMap", path = "perAssessTemplateMap")
public interface PerAssessTemplateMapRepository extends JpaRepository<PerAssessTemplateMap, String> {
    List<PerAssessTemplateMap> findByTemplateId(String _templateId);
    void deleteByTemplateId(String _templateId);
    void deleteByPerAssess(PerAssess _assess);
}

