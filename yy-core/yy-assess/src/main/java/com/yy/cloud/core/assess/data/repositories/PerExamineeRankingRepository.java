package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerExamineeRanking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/4/18 8:54 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perExamineeRankingRepository", path = "perExamineeRankingRepository")
public interface PerExamineeRankingRepository extends JpaRepository<PerExamineeRanking, String> {
    Page<PerExamineeRanking> findByAssessPaperId(String _assessPaperId, Pageable _page);
    Page<PerExamineeRanking> findByAssessPaperIdAndOrgId(String _assessPaperId, String _orgId, Pageable _page);
    Page<PerExamineeRanking> findByAssessPaperIdAndTitle(String _assessPaperId, Byte _title, Pageable _page);
    Page<PerExamineeRanking> findByAssessPaperIdAndOrgIdAndTitle(String _assessPaperId, String _orgId, Byte _title, Pageable _page);
}
