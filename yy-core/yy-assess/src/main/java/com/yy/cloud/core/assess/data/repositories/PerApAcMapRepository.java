package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.common.data.otd.assess.ApAcScoringItem;
import com.yy.cloud.core.assess.data.domain.PerApAcMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 5:18 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perApAcMap", path = "perApAcMap")
public interface PerApAcMapRepository extends JpaRepository<PerApAcMap, String> {
    @Transactional
    void deleteByAssessPaperId(String _assessPaperId);

    @Query(value = "SELECT pc.id as id, p.id as apAcId, pc.code as code, pc.name as name, p.scoringRatio as ratio, p.scoringThreshold as threshold FROM PerApAcMap p, PerAssessCategory  pc " +
            " where p.assessCategoryId = pc.id and p.assessPaperId = :assessPaperId")
    List<ApAcScoringItem> getApAcMapListByAssessPaperId(@Param("assessPaperId") String _assessPaperId);
}
