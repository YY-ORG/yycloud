package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FOX_PERM_ROLE database table.
 * 
 */
@Entity
@Table(name="FOX_PERM_ROLE")
@NamedQuery(name="FoxPermRole.findAll", query="SELECT f FROM FoxPermRole f")
public class FoxPermRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	//bi-directional many-to-one association to FoxPerm
	@ManyToOne
	@JoinColumn(name="PERM_ID")
	private FoxPerm foxPerm;

	//bi-directional many-to-one association to FoxRole
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private FoxRole foxRole;

	public FoxPermRole() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FoxPerm getFoxPerm() {
		return this.foxPerm;
	}

	public void setFoxPerm(FoxPerm foxPerm) {
		this.foxPerm = foxPerm;
	}

	public FoxRole getFoxRole() {
		return this.foxRole;
	}

	public void setFoxRole(FoxRole foxRole) {
		this.foxRole = foxRole;
	}

}