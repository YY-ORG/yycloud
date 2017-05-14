package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxTenantPaltformRelation;

@Repository
public interface FoxTenantPaltformRelationRepository extends JpaRepository<FoxTenantPaltformRelation, String> {

	//根据platformId查找包含的tenantId
	List<FoxTenantPaltformRelation> findByPlatformId(String platformId);
}
