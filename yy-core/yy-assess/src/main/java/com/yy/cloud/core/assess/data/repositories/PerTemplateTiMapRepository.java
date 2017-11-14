/**
 * Project Name:yy-assess
 * File Name:PerTemplateTiMapRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20178:05:11 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerTemplateTiMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName:PerTemplateTiMapRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 8:05:11 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perTemplateTiMap", path = "perTemplateTiMap")
public interface PerTemplateTiMapRepository extends JpaRepository<PerTemplateTiMap, String> {
    List<PerTemplateTiMap> findByTemplateId(String _templateId);

    void deleteByTemplateItemId(String _templateItemId);
    @Transactional
    void deleteByTemplateId(String _templateId);
}

