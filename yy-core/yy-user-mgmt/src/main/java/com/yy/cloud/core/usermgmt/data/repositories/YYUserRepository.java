package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.domain.YYUserInfo;

/**
 * ClassName: AcmeUser UserRepository <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON -- Optional. <br/>
 * date: Apr 23, 2016 4:35:37 PM <br/>
 *
 * @author chenxj
 * @since JDK 1.8
 */
@Repository
public interface YYUserRepository extends JpaRepository<YYUser, String> {




	YYUser findByLoginName(String _loginName);


	Page<YYUser> findByIdInAndStatusLessThan(List<String> ids, Byte status, Pageable pageable);

	@Query(value = "SELECT U.* FROM YY_USER_ROLE UR LEFT JOIN YY_USER U ON UR.USER_ID = U.ID LEFT JOIN YY_ROLE R ON UR.ROLE_ID = R.ID WHERE R.ROLE_NAME IN ?1  AND U.STATUS < 4", nativeQuery = true)
	List<YYUser> findAdmUserByRoleList(List<String> roleNames);

	YYUser findByLoginNameOrId(String _loginName, String _id);
	
	Page<YYUser> findByStatusLessThan( Byte status, Pageable pageable);
	
	Page<YYUser> findByStatusLessThanAndUserInfoUserNameLike( Byte status, String username,
			Pageable pageable);
	
	
	@Modifying
	@Query("update YYUser f set f.status=?1 where f.id=?2")
	public int setStatusFor(Byte status, String id);
	
	Page<YYUser> findByStatusLessThanAndUserInfoDeptId(Byte status,String orgId, Pageable pageable);
	
	
}