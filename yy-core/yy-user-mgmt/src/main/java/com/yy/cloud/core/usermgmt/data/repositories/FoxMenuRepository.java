package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxMenu;
import com.yy.cloud.core.usermgmt.data.domain.FoxOrganizationPlatformMap;

import java.util.Collection;
import java.util.List;

@Repository
public interface FoxMenuRepository extends JpaRepository<FoxMenu, String> {

    List<FoxMenu> findByIdIn(Collection<String> menuIds);

}
