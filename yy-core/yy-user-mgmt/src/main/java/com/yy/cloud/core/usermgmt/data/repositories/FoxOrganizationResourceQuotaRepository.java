package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxMenu;
import com.yy.cloud.core.usermgmt.data.domain.FoxOrganizationResourceQuota;

import java.util.Collection;
import java.util.List;

@Repository
public interface FoxOrganizationResourceQuotaRepository extends JpaRepository<FoxOrganizationResourceQuota, String> {

    FoxOrganizationResourceQuota findByOrganizationId(String organizationId);

}
