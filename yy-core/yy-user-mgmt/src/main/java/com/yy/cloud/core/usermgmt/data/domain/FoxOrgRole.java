package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the FOX_ORG_ROLE database table.
 * 
 */
@Entity
@Table(name="FOX_ORG_ROLE")
@NamedQuery(name="FoxOrgRole.findAll", query="SELECT f FROM FoxOrgRole f")
public class FoxOrgRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="STATUS")
	private BigInteger status;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to FoxOrganization
	@ManyToOne
	@JoinColumn(name="ORG_ID")
	private FoxOrganization foxOrganization;

	//bi-directional many-to-one association to FoxRole
	@ManyToOne
	@JoinColumn(name="ROLE_ID")
	private FoxRole foxRole;

	public FoxOrgRole() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public BigInteger getStatus() {
		return this.status;
	}

	public void setStatus(BigInteger status) {
		this.status = status;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public FoxOrganization getFoxOrganization() {
		return this.foxOrganization;
	}

	public void setFoxOrganization(FoxOrganization foxOrganization) {
		this.foxOrganization = foxOrganization;
	}

	public FoxRole getFoxRole() {
		return this.foxRole;
	}

	public void setFoxRole(FoxRole foxRole) {
		this.foxRole = foxRole;
	}

}