/**
 * Project Name:yy-assess
 * File Name:PerAssessAnswerRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20177:58:43 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssess;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswer;
import com.yy.cloud.core.assess.data.domain.PerAssessAssessAnswerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
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
    @Query("select p.id as assessId, p.name as assessName, pa.id as assessAnswerId, pam.scoringThreshold as scoringThreshold, " +
            "pam.scoringRatio as scoringRatio, pa.auditScore as totalScore, pa.rAuditScore as realScore, pa.markedComment as comment " +
            "from PerAssess p, PerAssessAnswer pa, PerAssessAspMap pam " +
            "where p.id = pa.assessId and pa.assessPaperId = pam.assessPaperId " +
            "and pa.assessId = pam.assessId and pam.assessCategoryId=:assessCategoryId " +
            "and pam.status=1 and pam.assessPaperId=:assessPaperId and pa.creatorId=:userId")
    List<PerAssessAssessAnswerItem> getAssessAssessAnswerItemList(@Param("assessPaperId") String _assessPaperId,
                                                                  @Param("assessCategoryId") String _assessCategoryId,
                                                                  @Param("userId") String _userId);
    @Query("select count(pa.id) as completedCount from PerAssessAnswer pa, PerAssessAspMap pam " +
            " where pa.status=2 and pa.assessId = pam.assessId and pa.assessPaperId = pam.assessPaperId and pam.assessCategoryId=:assessCategoryId and pam.assessPaperId=:assessPaperId and pa.creatorId=:userId")
    PerAssessAnswerCount getCompletedAnswerCount(
            @Param("assessPaperId") String _assessPaperId,
            @Param("assessCategoryId") String _assessCategoryId,
            @Param("userId") String _userId);
}

