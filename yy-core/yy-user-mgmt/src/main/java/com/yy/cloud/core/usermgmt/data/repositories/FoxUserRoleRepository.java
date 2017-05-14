package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxRole;
import com.yy.cloud.core.usermgmt.data.domain.FoxUserRole;

import java.util.List;

/**
 * ClassName: FoxRoleRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Sep 6, 2016 3:30:20 PM <br/>
 *
 * @author chenxj
 * @since JDK 1.8
 */
@Repository
public interface FoxUserRoleRepository extends JpaRepository<FoxUserRole, String> {

    List<FoxUserRole> findByUserId(String userId);

}