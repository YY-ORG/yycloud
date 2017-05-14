package com.yy.cloud.core.usermgmt.data.repositories;

import com.hpe.foxcloud.core.usermgmt.constant.UserMgmtConstants;
import com.hpe.foxcloud.core.usermgmt.data.domain.FoxUserOrganization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guof on 2016/11/8.
 */
@Repository
public interface FoxUserOrganizationRepository extends JpaRepository<FoxUserOrganization, String> {

	@Query("SELECT f FROM FoxUserOrganization f WHERE f.organizationId=?1 and f.userId in (SELECT u.id FROM FoxUser u WHERE u.status<"
			+ UserMgmtConstants.STATUS_GLOBAL_DELETED + ")")
	List<FoxUserOrganization> findByOrganizationId(String organizationId);

	@Query("SELECT f FROM FoxUserOrganization f WHERE f.organizationId in (SELECT o.id FROM FoxOrganization o WHERE o.tenantId is null and o.status<"
			+ UserMgmtConstants.STATUS_GLOBAL_DELETED + ")")
	List<FoxUserOrganization> findByOrganizationTenantIdIsNull();

	List<FoxUserOrganization> findByUserId(String userId);

	@Query("select user.id from FoxUser user where user.id not in (select org.userId from FoxUserOrganization org) and user.status < 4")
	Page<String> findNonOrganizationUserIds(Pageable pageable);

}
