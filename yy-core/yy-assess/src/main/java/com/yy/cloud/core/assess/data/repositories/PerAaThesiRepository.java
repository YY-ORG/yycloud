/**
 * Project Name:yy-assess
 * File Name:PerAaThesi.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20176:29:43 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.yy.cloud.core.assess.data.domain.PerAaThesi;

/**
 * ClassName:PerAaThesi <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 6:29:43 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RepositoryRestResource(collectionResourceRel = "perAaThesi", path = "perAaThesi")
public interface PerAaThesiRepository extends JpaRepository<PerAaThesi, String> {

}

