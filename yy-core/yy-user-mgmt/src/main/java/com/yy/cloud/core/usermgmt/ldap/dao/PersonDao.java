package com.yy.cloud.core.usermgmt.ldap.dao;

import java.util.List;

import com.yy.cloud.core.usermgmt.data.domain.ExtLdapSource;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;
import com.yy.cloud.core.usermgmt.ldap.domain.Person;

/**
 * ClassName: PersonDao <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月8日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public interface PersonDao {

	List<String> getAllPersonNames(ExtLdapSource ldap);

	List<Person> findAll(ExtLdapSource ldap);

	List<Person> findByFilter(ExtLdapSource ldap, String filter);

	Person login(ExtLdapSource ldap, ExtLdapUser user, String password);

}
