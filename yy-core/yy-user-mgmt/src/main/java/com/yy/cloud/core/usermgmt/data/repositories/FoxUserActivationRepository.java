/**
 * Project Name:liz-auth-server
 * File Name:YyUserActivationRepository.java
 * Package Name:com.yy.cloud.baseplatform.authserver.data.repository
 * Date:May 17, 20169:32:12 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxUserActivation;

/**
 * ClassName:YyUserActivationRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: May 17, 2016 9:32:12 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Repository
public interface FoxUserActivationRepository extends JpaRepository<FoxUserActivation, Long> {
	FoxUserActivation findOneByActivationCode(@Param("_code") String _code);
}
