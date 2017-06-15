package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYRole;

import java.util.List;
import java.lang.String;

/**
 * ClassName: FoxRoleRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Sep 6, 2016 3:30:20 PM <br/>
 *
 * @author chenxj
 * @version 
 * @since JDK 1.8
 */
@Repository
public interface YYRoleRepository extends JpaRepository<YYRole, String> {

	YYRole findOneByName(@Param("name") String name);
	YYRole findOneByRoleName(@Param("roleName") String roleName);
	

	List<YYRole> findByType(Byte type);

	@Query(value = "SELECT R.* FROM YY_USER_ROLE UR LEFT JOIN YY_ROLE R ON UR.ROLE_ID=R.ID WHERE UR.USER_ID = ?1", nativeQuery = true)
	List<YYRole> findRolesByUserId(String userId);
}