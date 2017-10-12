/**
 * Project Name:yy-assess
 * File Name:PerAssessPaperRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:01:42 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAssessPaper;

/**
 * ClassName:PerAssessPaperRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:01:42 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAssessPaper", path = "perAssessPaper")
public interface PerAssessPaperRepository extends JpaRepository<PerAssessPaper, String> {
    PerAssessPaper findByOrgId(String _orgId);
}

