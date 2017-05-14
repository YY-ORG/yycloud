package com.yy.cloud.core.usermgmt.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yy.cloud.core.usermgmt.constant.AdUserMgmtConstants;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapSource;

/**
 * ClassName: ExtLdapSourceRepository <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月10日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface ExtLdapSourceRepository extends JpaRepository<ExtLdapSource, String> {

	@Query("SELECT e FROM ExtLdapSource e where e.tenantId is null and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_DELETED + " order by e.createDate desc")
	public Page<ExtLdapSource> findByTenantIdIsNull(Pageable pageable);

	@Query("SELECT e FROM ExtLdapSource e where e.tenantId=?1 and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_DELETED + " order by e.createDate desc")
	public Page<ExtLdapSource> findByTenantId(String tenantId, Pageable pageable);

	@Query("SELECT e FROM ExtLdapSource e where e.tenantId is null and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_DELETED + " and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_CANCELLED + " order by e.createDate desc")
	public List<ExtLdapSource> findByTenantIdIsNull();

	@Query("SELECT e FROM ExtLdapSource e where e.tenantId=?1 and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_DELETED + " and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_CANCELLED + " order by e.createDate desc")
	public List<ExtLdapSource> findByTenantId(String tenantId);

	@Query("SELECT e FROM ExtLdapSource e where e.id in (SELECT u.ldapSourceId FROM ExtLdapUser u where u.login=?1) and e.status<>"
			+ AdUserMgmtConstants.STATUS_GLOBAL_DELETED + " order by e.createDate desc")
	public List<ExtLdapSource> findByLoginName(String loginName);

	@Modifying
	@Query("update ExtLdapSource e set e.status=?1 where e.id=?2")
	public int setStatusFor(Byte status, String id);

	@Modifying
	@Query("update ExtLdapSource e set e.name=?1, e.description=?2 where e.id=?3")
	public int setNameAndDescriptionFor(String name, String description, String id);

	@Modifying
	@Query("update ExtLdapSource e set e.url=?1, e.username=?2, e.password=?3 where e.id=?4")
	public int setLdapAccountFor(String url, String username, String password, String id);
}
