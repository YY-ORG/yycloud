package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYUserRole;

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
public interface YYUserRoleRepository extends JpaRepository<YYUserRole, String> {

    List<YYUserRole> findByUserId(String userId);

}