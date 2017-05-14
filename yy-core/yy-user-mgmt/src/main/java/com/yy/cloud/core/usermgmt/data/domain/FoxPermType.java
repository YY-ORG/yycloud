package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FOX_PERM_TYPE database table.
 * 
 */
@Entity
@Table(name="FOX_PERM_TYPE")
@NamedQuery(name="FoxPermType.findAll", query="SELECT f FROM FoxPermType f")
public class FoxPermType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="NAME")
	private String name;

	@Column(name="PRIORITY")
	private int priority;

	@Column(name="TENANT_ID")
	private String tenantId;

	@Column(name="TYPE")
	private int type;

	//bi-directional many-to-one association to FoxPerm
	@OneToMany(mappedBy="foxPermType")
	private List<FoxPerm> foxPerms;

	public FoxPermType() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<FoxPerm> getFoxPerms() {
		return this.foxPerms;
	}

	public void setFoxPerms(List<FoxPerm> foxPerms) {
		this.foxPerms = foxPerms;
	}

	public FoxPerm addFoxPerm(FoxPerm foxPerm) {
		getFoxPerms().add(foxPerm);
		foxPerm.setFoxPermType(this);

		return foxPerm;
	}

	public FoxPerm removeFoxPerm(FoxPerm foxPerm) {
		getFoxPerms().remove(foxPerm);
		foxPerm.setFoxPermType(null);

		return foxPerm;
	}

}