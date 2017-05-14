package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxRole;

import java.util.List;

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
public interface FoxRoleRepository extends JpaRepository<FoxRole, String> {

	FoxRole findOneByName(@Param("name") String name);
	//根据角色名查询
	FoxRole findOneByRoleName(@Param("roleName") String roleName);

	List<FoxRole> findByType(Byte type);

	@Query(value = "SELECT R.* FROM FOX_USER_ROLE UR LEFT JOIN FOX_ROLE R ON UR.ROLE_ID=R.ID WHERE UR.USER_ID = ?1", nativeQuery = true)
	List<FoxRole> findRolesByUserId(String userId);
}