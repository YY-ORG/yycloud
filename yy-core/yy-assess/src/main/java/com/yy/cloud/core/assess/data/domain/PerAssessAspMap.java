package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the PER_ASSESS_ASP_MAP database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_ASP_MAP")
@NamedQuery(name="PerAssessAspMap.findAll", query="SELECT p FROM PerAssessAspMap p")
public class PerAssessAspMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="STATUS")
	private byte status;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssess
	@ManyToOne
	@JoinColumn(name="ASSESS_ID")
	private PerAssess perAssess;

	//bi-directional many-to-one association to PerAssessPaper
	@ManyToOne
	@JoinColumn(name="ASSESS_PAPER_ID")
	private PerAssessPaper perAssessPaper;

	public PerAssessAspMap() {
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

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public PerAssess getPerAssess() {
		return this.perAssess;
	}

	public void setPerAssess(PerAssess perAssess) {
		this.perAssess = perAssess;
	}

	public PerAssessPaper getPerAssessPaper() {
		return this.perAssessPaper;
	}

	public void setPerAssessPaper(PerAssessPaper perAssessPaper) {
		this.perAssessPaper = perAssessPaper;
	}

}