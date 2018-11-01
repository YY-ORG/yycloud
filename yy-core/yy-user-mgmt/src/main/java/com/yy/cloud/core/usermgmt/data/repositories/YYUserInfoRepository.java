package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.domain.YYUserInfo;

/**
 * 
 * @author luckey
 *
 */
@Repository
public interface YYUserInfoRepository extends JpaRepository<YYUserInfo, String> {
	YYUserInfo findByUser(YYUser user);
}