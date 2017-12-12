package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssessOrgMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/24/17 8:33 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "PerAssessOrgMap", path = "PerAssessOrgMap")
public interface PerAssessOrgMapRepository extends JpaRepository<PerAssessOrgMap, String> {
    List<PerAssessOrgMap> findByOrgIdAndTitleTypeAndStatus(String _orgId, Byte _title, Byte _status);
    Page<PerAssessOrgMap> findByOrgIdAndTitleTypeAndStatus(String _orgId, Byte _title, Byte _status, Pageable _page);
    PerAssessOrgMap findByAssessPaperIdAndOrgIdAndTitleTypeAndStatus(String _assessPaperId, String _orgId, Byte _title, Byte _status);
    void deleteByAssessPaperId(String _assessPaperId);
}
