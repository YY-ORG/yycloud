package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.FoxTenant;

import java.util.List;

/**
 * Created by guof on 2016/9/27.
 */
@Repository
public interface FoxTenantRepository extends JpaRepository<FoxTenant, String> {

    Page<FoxTenant> findByCreatorIdIn(List<String> creatorIdList, Pageable pageable);
}
