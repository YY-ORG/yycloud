/**
 * Project Name:yy-assess
 * File Name:PerAssessAspMapRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:01:04 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAssessAspMap;

import java.util.List;

/**
 * ClassName:PerAssessAspMapRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:01:04 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAssessAspMap", path = "perAssessAspMap")
public interface PerAssessAspMapRepository extends JpaRepository<PerAssessAspMap, String> {
    List<PerAssessAspMap> findByAssessPaperIdAndStatus(String _assessPaperId, Byte _status);
    void deletePerAssessAspMapsByAssessPaperId(String _assessPaperId);
}

