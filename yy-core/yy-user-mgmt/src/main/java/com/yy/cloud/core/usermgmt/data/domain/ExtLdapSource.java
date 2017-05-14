package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

/**
 * The persistent class for the ext_ldap_source database table.
 * 
 */
@Entity
@Table(name = "EXT_LDAP_SOURCE")
@NamedQuery(name = "ExtLdapSource.findAll", query = "SELECT e FROM ExtLdapSource e")
public class ExtLdapSource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	private String basedn;

	@Column(name = "CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	private String description;

	@Column(name = "LOGIN_PROP")
	private String loginProp;

	private String name;

	private String password;

	private Byte status;

	@Column(name = "TENANT_ID")
	private String tenantId;

	@Column(name = "UPDATE_DATE", insertable = false)
	private Timestamp updateDate;

	private String url;

	private String username;

	public ExtLdapSource() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBasedn() {
		return basedn;
	}

	public void setBasedn(String basedn) {
		this.basedn = basedn;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLoginProp() {
		return this.loginProp;
	}

	public void setLoginProp(String loginProp) {
		this.loginProp = loginProp;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}