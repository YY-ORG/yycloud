package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssessAnswerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/29/17 8:45 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perAssessAnswerDetail", path = "perAssessAnswerDetail")
public interface PerAssessAnswerDetailRepository extends JpaRepository<PerAssessAnswerDetail, String> {
    @Modifying
    @Query(value = "DELETE FROM PerAssessAnswerDetail pd where pd.perAssessAnswerItem.id in (:itemIdList)")
    void deleteByPerAssessAnswerItemIdIn(@Param("itemIdList") List<String> _answerItemList);

}
