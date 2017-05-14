package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FOX_PERM database table.
 * 
 */
@Entity
@Table(name="FOX_PERM")
@NamedQuery(name="FoxPerm.findAll", query="SELECT f FROM FoxPerm f")
public class FoxPerm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="CODE")
	private String code;

	@Column(name="NAME")
	private String name;

	private int priority;

	@Column(name="TENANT_ID")
	private Long tenantId;

	//bi-directional many-to-one association to FoxAccess
	@OneToMany(mappedBy="foxPerm")
	private List<FoxAccess> foxAccesses;

	//bi-directional many-to-one association to FoxPermType
	@ManyToOne
	@JoinColumn(name="PERM_TYPE_ID")
	private FoxPermType foxPermType;

	//bi-directional many-to-one association to FoxPermRole
	@OneToMany(mappedBy="foxPerm")
	private List<FoxPermRole> foxPermRoles;

	public FoxPerm() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Long getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public List<FoxAccess> getFoxAccesses() {
		return this.foxAccesses;
	}

	public void setFoxAccesses(List<FoxAccess> foxAccesses) {
		this.foxAccesses = foxAccesses;
	}

	public FoxAccess addFoxAccess(FoxAccess foxAccess) {
		getFoxAccesses().add(foxAccess);
		foxAccess.setFoxPerm(this);

		return foxAccess;
	}

	public FoxAccess removeFoxAccess(FoxAccess foxAccess) {
		getFoxAccesses().remove(foxAccess);
		foxAccess.setFoxPerm(null);

		return foxAccess;
	}

	public FoxPermType getFoxPermType() {
		return this.foxPermType;
	}

	public void setFoxPermType(FoxPermType foxPermType) {
		this.foxPermType = foxPermType;
	}

	public List<FoxPermRole> getFoxPermRoles() {
		return this.foxPermRoles;
	}

	public void setFoxPermRoles(List<FoxPermRole> foxPermRoles) {
		this.foxPermRoles = foxPermRoles;
	}

	public FoxPermRole addFoxPermRole(FoxPermRole foxPermRole) {
		getFoxPermRoles().add(foxPermRole);
		foxPermRole.setFoxPerm(this);

		return foxPermRole;
	}

	public FoxPermRole removeFoxPermRole(FoxPermRole foxPermRole) {
		getFoxPermRoles().remove(foxPermRole);
		foxPermRole.setFoxPerm(null);

		return foxPermRole;
	}

}