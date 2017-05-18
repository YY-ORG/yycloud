package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.constant.UserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.YYUserOrganization;

/**
 * Created by guof on 2016/11/8.
 */
@Repository
public interface YYUserOrganizationRepository extends JpaRepository<YYUserOrganization, String> {

	@Query("SELECT f FROM YYUserOrganization f WHERE f.organizationId=?1 and f.userId in (SELECT u.id FROM YYUser u WHERE u.status<"
			+ UserMgmtConstants.STATUS_GLOBAL_DELETED + ")")
	List<YYUserOrganization> findByOrganizationId(String organizationId);

	@Query("SELECT f FROM YYUserOrganization f WHERE f.organizationId in (SELECT o.id FROM YYOrganization o WHERE o.tenantId is null and o.status<"
			+ UserMgmtConstants.STATUS_GLOBAL_DELETED + ")")
	List<YYUserOrganization> findByOrganizationTenantIdIsNull();

	List<YYUserOrganization> findByUserId(String userId);

	@Query("select user.id from YYUser user where user.id not in (select org.userId from YYUserOrganization org) and user.status < 4")
	Page<String> findNonOrganizationUserIds(Pageable pageable);

}
