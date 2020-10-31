package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.domain.YYUserInfo;

import java.util.List;

/**
 * 
 * @author luckey
 *
 */
@Repository
public interface YYUserInfoRepository extends JpaRepository<YYUserInfo, String> {
	YYUserInfo findByUser(YYUser user);
	@Query(value = "select t from YYUserInfo t where t.user.status <4 and (t.userName like %?1% or t.user.loginName like %?1%)")
	List<YYUserInfo> findByUserNameLike(String _userName);
}