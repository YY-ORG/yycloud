/**
 * Project Name:yy-assess
 * File Name:PerAssessAnswerRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20177:58:43 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssessAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * ClassName:PerAssessAnswerRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 7:58:43 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAssessAnswer", path = "perAssessAnswer")
public interface PerAssessAnswerRepository extends JpaRepository<PerAssessAnswer, String> {
    PerAssessAnswer findByAssessPaperIdAndAssessIdAndCreatorId(String _assessPaperId, String _assessId, String _creatorId);

}

