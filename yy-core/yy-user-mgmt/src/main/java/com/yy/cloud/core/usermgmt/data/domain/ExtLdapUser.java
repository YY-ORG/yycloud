package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

/**
 * The persistent class for the ext_ldap_user database table.
 * 
 */
@Entity
@Table(name = "EXT_LDAP_USER")
@NamedQuery(name = "ExtLdapUser.findAll", query = "SELECT e FROM ExtLdapUser e")
public class ExtLdapUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	private String login;

	private String password;

	private Byte status;

	@Column(name = "UPDATE_DATE", insertable = false)
	private Timestamp updateDate;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "LDAP_SOURCE_ID")
	private String ldapSourceId;

	public ExtLdapUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLdapSourceId() {
		return ldapSourceId;
	}

	public void setLdapSourceId(String ldapSourceId) {
		this.ldapSourceId = ldapSourceId;
	}

}