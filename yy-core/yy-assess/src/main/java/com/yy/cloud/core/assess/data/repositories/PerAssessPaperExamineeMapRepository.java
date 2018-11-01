package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssessPaperExamineeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/11/17 8:09 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perAssessPaperExamineeMap", path = "perAssessPaperExamineeMap")
public interface PerAssessPaperExamineeMapRepository extends JpaRepository<PerAssessPaperExamineeMap, String> {
    PerAssessPaperExamineeMap findByAssessPaperIdAndCreatorId(String _assessPaperId, String _userId);
    Page<PerAssessPaperExamineeMap> findByDeptIdAndStatusIn(String _deptId, List<Byte> _statusList, Pageable _page);
    Page<PerAssessPaperExamineeMap> findByStatusIn(List<Byte> _statusList, Pageable _page);
}
