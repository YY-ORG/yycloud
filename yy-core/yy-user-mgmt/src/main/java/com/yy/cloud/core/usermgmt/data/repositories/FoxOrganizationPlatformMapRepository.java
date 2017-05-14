package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxOrganizationPlatformMap;

import java.util.List;

/**
 * Created by guof on 2016/10/28.
 */
@Repository
public interface FoxOrganizationPlatformMapRepository extends JpaRepository<FoxOrganizationPlatformMap, String> {

    /**
     * 根据组织ID查找
     *
     * @param organizationId
     * @return
     */
    List<FoxOrganizationPlatformMap> findByOrganizationId(String organizationId);


}
