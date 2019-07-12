package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerApAcExamineeDetailItem;
import com.yy.cloud.core.assess.data.domain.PerApacExamineeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/12/18 9:03 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perApacEM", path = "perApacEM")
public interface PerApacExamineeMapRepository  extends JpaRepository<PerApacExamineeMap, String> {
    @Query("select p from PerApacExamineeMap p, PerApAcMap pa where p.apAcMapId = pa.id and pa.assessPaperId=:assessPaperId and p.creatorId=:userId")
    List<PerApacExamineeMap> getApacExamineeMapItems(@Param("assessPaperId") String _assessPaperId, @Param("userId") String _userId);
    List<PerApacExamineeMap> findByApemId(String _apemId);
    @Query("select pc.id as apAcId, pc.name as apAcName, pa.scoringRatio as scoringRatio, pa.scoringThreshold as scoringThreshold, " +
            "p.markedScore as markedScore, p.auditScore as auditScore, p.rMarkedScore as rMarkedScore, p.rAuditScore as rAuditScore, p.marker as marker, p.auditor as auditor" +
            " from PerApacExamineeMap p, PerApAcMap pa, PerAssessCategory pc where p.apAcMapId = pa.id and pa.assessCategoryId = pc.id and p.apemId=:apemId")
    List<PerApAcExamineeDetailItem> getApAcExamineeDetailItemList(@Param("apemId") String _apemId);
}
