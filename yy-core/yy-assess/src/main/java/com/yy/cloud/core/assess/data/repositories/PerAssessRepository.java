/**
 * Project Name:yy-assess
 * File Name:PerAssessRepository.java
 * Package Name:com.yy.cloud.core.assess.data.repositories
 * Date:Sep 26, 20176:32:55 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.assess.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yy.cloud.core.assess.data.domain.PerAssess;

/**
 * ClassName:PerAssessRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 26, 2017 6:32:55 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public interface PerAssessRepository extends JpaRepository<PerAssess, String> {

}

