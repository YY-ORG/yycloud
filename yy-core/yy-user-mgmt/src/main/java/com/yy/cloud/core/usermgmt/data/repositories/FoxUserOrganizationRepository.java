package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxOrganizationPlatformMap;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;
import com.yy.cloud.core.usermgmt.data.domain.FoxUserOrganization;
import com.yy.cloud.core.usermgmt.data.domain.FoxUserRole;

import java.util.List;

/**
 * Created by guof on 2016/11/8.
 */
@Repository
public interface FoxUserOrganizationRepository extends JpaRepository<FoxUserOrganization, String> {

    List<FoxUserOrganization> findByOrganizationId(String organizationId);

    List<FoxUserOrganization> findByUserId(String userId);

    @Query("select user.id from FoxUser user where user.id not in (select org.userId from FoxUserOrganization org) and user.status < 4")
    Page<String> findNonOrganizationUserIds(Pageable pageable);

}
