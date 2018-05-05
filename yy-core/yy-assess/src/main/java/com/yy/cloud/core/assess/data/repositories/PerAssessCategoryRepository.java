package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssessCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 5:20 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perAssessCategory", path = "perAssessCategory")
public interface PerAssessCategoryRepository extends JpaRepository<PerAssessCategory, String> {
    @Query(value = "SELECT p FROM PerAssessCategory p, PerApAcMap paam where p.id = paam.assessCategoryId and paam.assessPaperId = :assessPaperId order by p.createDate asc") // order by p.createDate asc
    List<PerAssessCategory> findByAssessPaper(@Param("assessPaperId") String _assessPaperId);
    Page<PerAssessCategory> findAllByOrderByCreateDateAsc(Pageable _page);
}
