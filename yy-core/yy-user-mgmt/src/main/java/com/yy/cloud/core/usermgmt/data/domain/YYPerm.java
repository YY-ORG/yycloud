package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the YYPerm database table.
 * 
 */
@Entity
@Table(name="YY_PERM")
@NamedQuery(name="YYPerm.findAll", query="SELECT f FROM YYPerm f")
public class YYPerm implements Serializable {
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


	//bi-directional many-to-one association to FoxPermType
	@ManyToOne
	@JoinColumn(name="PERM_TYPE_ID")
	private YYPermType foxPermType;

	//bi-directional many-to-one association to FoxPermRole
	@OneToMany(mappedBy="foxPerm")
	private List<YYPermRole> foxPermRoles;

	public YYPerm() {
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



	public YYPermType getFoxPermType() {
		return this.foxPermType;
	}

	public void setFoxPermType(YYPermType foxPermType) {
		this.foxPermType = foxPermType;
	}

	public List<YYPermRole> getFoxPermRoles() {
		return this.foxPermRoles;
	}

	public void setFoxPermRoles(List<YYPermRole> foxPermRoles) {
		this.foxPermRoles = foxPermRoles;
	}

	public YYPermRole addFoxPermRole(YYPermRole foxPermRole) {
		getFoxPermRoles().add(foxPermRole);
		foxPermRole.setFoxPerm(this);

		return foxPermRole;
	}

	public YYPermRole removeFoxPermRole(YYPermRole foxPermRole) {
		getFoxPermRoles().remove(foxPermRole);
		foxPermRole.setFoxPerm(null);

		return foxPermRole;
	}

}