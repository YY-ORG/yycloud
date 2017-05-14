package com.yy.cloud.core.usermgmt.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;

/**
 * ClassName: ExtLdapUserRepository <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月15日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface ExtLdapUserRepository extends JpaRepository<ExtLdapUser, String> {
	public ExtLdapUser findOneByLoginAndLdapSourceId(String login, String ldapSourceId);

	public ExtLdapUser findOneByLogin(String login);
	

	public ExtLdapUser findOneByUserId(String userId);

	@Query("SELECT count(e) FROM ExtLdapUser e WHERE e.login=?1")
	public Long countByLogin(String login);
	
	@Modifying
	@Query("UPDATE ExtLdapUser e SET e.password=?1 WHERE e.id=?2")
	public int setPasswordFor(String password, String id);
}
