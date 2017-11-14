/**
 * Project Name:yy-assess
 * File Name:PerTemplateItemRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:04:37 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerTemplateItem;

import java.util.List;

/**
 * ClassName:PerTemplateItemRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:04:37 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perTemplateItem", path = "perTemplateItem")
public interface PerTemplateItemRepository extends JpaRepository<PerTemplateItem, String> {
    @Query(value = "SELECT pti FROM PerTemplateItem pti, PerTemplateTiMap pttm where pti.id = pttm.templateItemId and pttm.templateId = :templateId")
    List<PerTemplateItem> getTemplateItemByTemplate(@Param("templateId") String _templateId);
}

