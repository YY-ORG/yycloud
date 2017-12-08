package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAspProcessOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/28/17 7:57 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perAaThesi", path = "perAaThesi")
public interface PerAspProcessOverviewRepository extends JpaRepository<PerAspProcessOverview, String> {
    Optional<PerAspProcessOverview> findByAssessPaperIdAndCategoryIdAndCreatorId(String _assessPaperId, String _groupId, String _userId);
    List<PerAspProcessOverview> findByAssessPaperIdAndCreatorId(String _assessPaperId, String _userId);
}
