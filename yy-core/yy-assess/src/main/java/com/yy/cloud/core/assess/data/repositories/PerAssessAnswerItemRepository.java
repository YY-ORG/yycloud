package com.yy.cloud.core.assess.data.repositories;

import com.yy.cloud.core.assess.data.domain.PerAssessAnswerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/29/17 8:43 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@RepositoryRestResource(collectionResourceRel = "perAssessAnswerItem", path = "perAssessAnswerItem")
public interface PerAssessAnswerItemRepository extends JpaRepository<PerAssessAnswerItem, String> {
    void deleteByTemplateId(String _templateId);
    void deleteByIdIn(List<String> _idList);
    void deleteByTypeAndTemplateIdIn(Byte _type, List<String> _templateIdList);
}
