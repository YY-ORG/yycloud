package com.yy.cloud.core.usermgmt.ldap.dao;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AuthenticationSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import com.yy.cloud.core.usermgmt.data.domain.ExtLdapSource;
import com.yy.cloud.core.usermgmt.data.domain.ExtLdapUser;
import com.yy.cloud.core.usermgmt.ldap.domain.Person;

import lombok.extern.slf4j.Slf4j;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * ClassName: PersonDaoImpl <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月8日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
@Service
@Slf4j
public class PersonDaoImpl implements PersonDao {

	@Override
	public List<String> getAllPersonNames(ExtLdapSource ldap) {
		LdapTemplate ldapTemplate = retrieveLdapTemplate(ldap);
		return ldapTemplate.search(query().attributes("cn").where("objectclass").is("person"),
				new AttributesMapper<String>() {
					public String mapFromAttributes(Attributes attrs) throws NamingException {
						return attrs.get("cn").get().toString();
					}
				});
	}

	@Override
	public List<Person> findAll(ExtLdapSource ldap) {
		LdapTemplate ldapTemplate = retrieveLdapTemplate(ldap);
		return ldapTemplate.search(query().where("objectclass").is("person"), new PersonContextMapper(ldap));
	}

	@Override
	public List<Person> findByFilter(ExtLdapSource ldap, String filter) {
		LdapTemplate ldapTemplate = retrieveLdapTemplate(ldap);
		return ldapTemplate.search(LdapUtils.emptyLdapName(), filter, SearchControls.SUBTREE_SCOPE,
				new PersonContextMapper(ldap));
	}

	private static LdapTemplate retrieveLdapTemplate(ExtLdapSource ldap) {
		LdapContextSource cs = new LdapContextSource();
		cs.setCacheEnvironmentProperties(false);
		cs.setUrl(ldap.getUrl());
		cs.setBase(ldap.getBasedn());
		cs.setAuthenticationSource(new AuthenticationSource() {
			@Override
			public String getCredentials() {
				return ldap.getPassword();
			}

			@Override
			public String getPrincipal() {
				return ldap.getUsername();
			}
		});
		LdapTemplate ldapTemplate = new LdapTemplate(cs);
		ldapTemplate.setIgnoreNameNotFoundException(true);
		ldapTemplate.setIgnorePartialResultException(true);
		return ldapTemplate;

	}

	private final static class PersonContextMapper extends AbstractContextMapper<Person> {

		private ExtLdapSource ldap;

		public PersonContextMapper(ExtLdapSource ldap) {
			this.ldap = ldap;
		}

		@Override
		protected Person doMapFromContext(DirContextOperations context) {
			Person person = new Person();
			person.setFullName(context.getStringAttribute("cn"));
			person.setLastName(context.getStringAttribute("sn"));
			person.setDescription(context.getStringAttribute("description"));
			person.setPhone(context.getStringAttribute("telephoneNumber"));
			person.setEmail(context.getStringAttribute("mail"));
			person.setLogin(context.getStringAttribute(ldap.getLoginProp()));
			person.setType(context.getStringAttribute("sAMAccountType"));
			person.setDn(context.getNameInNamespace());
			return person;
		}
	}

	@Override
	public Person login(ExtLdapSource ldap, ExtLdapUser user, String password) {
		Person login = null;
		try {
			LdapTemplate ldapTemplate = retrieveLdapTemplate(ldap);
			String filter = ldap.getLoginProp() + "=" + user.getLogin();
			List<Person> people = ldapTemplate.search(LdapUtils.emptyLdapName(), filter, SearchControls.SUBTREE_SCOPE,
					new PersonContextMapper(ldap));
			if (people != null && people.size() == 1) {
				Person person = people.get(0);
				log.debug("going to auth dn = " + person.getDn());
				ldapTemplate.getContextSource().getContext(person.getDn(), password);
				login = person;
			} else {
				log.info("login failed, ad user id=" + user.getId() + " found error in ldap");
			}
		} catch (Exception e) {
			log.info("login failed, ad user id =" + user.getId() + "; exception=" + e.toString());
		}
		return login;
	}

}
