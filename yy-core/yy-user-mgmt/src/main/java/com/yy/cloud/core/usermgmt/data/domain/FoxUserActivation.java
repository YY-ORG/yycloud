package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the FOX_USER_ACTIVATION database table.
 * 
 */
@Entity
@Table(name="FOX_USER_ACTIVATION")
@NamedQuery(name="FoxUserActivation.findAll", query="SELECT f FROM FoxUserActivation f")
public class FoxUserActivation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="ACTIVATION_CODE")
	private String activationCode;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="STATUS")
	private short status;

	@Column(name="TYPE")
	private short type;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to FoxUser
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private FoxUser foxUser;

	public FoxUserActivation() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivationCode() {
		return this.activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public short getType() {
		return this.type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public FoxUser getFoxUser() {
		return this.foxUser;
	}

	public void setFoxUser(FoxUser foxUser) {
		this.foxUser = foxUser;
	}

}