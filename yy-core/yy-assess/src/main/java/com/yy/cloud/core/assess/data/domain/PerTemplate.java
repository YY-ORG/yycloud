package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the PER_TEMPLATE database table.
 * 
 */
@Entity
@Table(name="PER_TEMPLATE")
@NamedQuery(name="PerTemplate.findAll", query="SELECT p FROM PerTemplate p")
public class PerTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CODE")
	private String code;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="NAME")
	private String name;

	@Column(name="STATUS")
	private byte status;

	@Column(name="TENANT_ID")
	private String tenantId;

	@Column(name="TYPE")
	private byte type;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	public PerTemplate() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}