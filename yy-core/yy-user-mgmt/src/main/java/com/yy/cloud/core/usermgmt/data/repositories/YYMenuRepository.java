package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yy.cloud.core.usermgmt.data.domain.YYMenu;

@Repository
public interface YYMenuRepository extends JpaRepository<YYMenu, String> {

    List<YYMenu> findByIdIn(Collection<String> menuIds);

}
