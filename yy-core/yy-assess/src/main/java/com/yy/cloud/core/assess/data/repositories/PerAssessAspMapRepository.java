/**
 * Project Name:yy-assess
 * File Name:PerAssessAspMapRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:01:04 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.common.data.otd.assess.ApAssessScoringItem;
import com.yy.cloud.core.assess.data.domain.PerAPACCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAssessAspMap;
import org.springframework.transaction.annotation.Transactional;

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
    List<PerAssessAspMap> findByAssessPaperIdAndStatusOrderByCreateDateAsc(String _assessPaperId, Byte _status);
    List<PerAssessAspMap> findByAssessPaperIdAndAndAssessCategoryIdAndStatusOrderByCreateDateAsc(String _assessPaperId, String _groupId, Byte _status);

    PerAssessAspMap findByAssessPaperIdAndAssessIdAndStatus(String _assessPaperId, String _assessId, Byte _status);
    int countByAssessPaperIdAndAssessCategoryId(String _assessPaperId, String _groupId);
    @Transactional
    void deletePerAssessAspMapsByAssessPaperId(String _assessPaperId);

    @Query(value = "SELECT p.assessCategoryId as groupId, count(p) as totalCount FROM PerAssessAspMap p where p.assessPaperId = :assessPaperId group by p.assessCategoryId")
    List<PerAPACCount> getGroupCountByAssessPaper(@Param("assessPaperId") String _assessPaperId);

    @Query(value="select pa.id as id, p.id as apAssessId, pa.code as code, pa.name as name, pa.type as type, p.seqNo as seqNo, " +
            " p.scoringRatio as ratio, p.itemThreshold as itemThreshold,  p.scoringThreshold as threshold from PerAssessAspMap p, PerAssess pa where p.status=1 and p.assessId = pa.id and p.assessPaperId = :assessPaperId and p.assessCategoryId = :categoryId ",
    countQuery = "select count(p) from PerAssessAspMap p where p.status = 1 and p.assessPaperId = :assessPaperId and p.assessCategoryId = :categoryId")
    Page<ApAssessScoringItem> getAssessScoringForAssessPaper(@Param("assessPaperId") String _assessPaperId, @Param("categoryId") String _categoryId, Pageable _page);
}

