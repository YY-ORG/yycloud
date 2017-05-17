package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the YYPermRole database table.
 * 
 */
@Entity
@Table(name="YY_PERM_ROLE")
@NamedQuery(name="YYPermRole.findAll", query="SELECT f FROM YYPermRole f")
public class YYPermRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	//bi-directional many-to-one association to FoxPerm
	@ManyToOne
	@JoinColumn(name="PERM_ID")
	private YYPerm foxPerm;

	//bi-directional many-to-one association to FoxRole
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private YYRole foxRole;

	public YYPermRole() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public YYPerm getFoxPerm() {
		return this.foxPerm;
	}

	public void setFoxPerm(YYPerm foxPerm) {
		this.foxPerm = foxPerm;
	}

	public YYRole getFoxRole() {
		return this.foxRole;
	}

	public void setFoxRole(YYRole foxRole) {
		this.foxRole = foxRole;
	}

}