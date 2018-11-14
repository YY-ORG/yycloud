package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the per_assess_period database table.
 * 
 */
@Entity
@Table(name="per_assess_period")
@NamedQuery(name="PerAssessPeriod.findAll", query="SELECT p FROM PerAssessPeriod p")
public class PerAssessPeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="ASSESS_PAPER_ID")
	private String assessPaperId;

	@Column(name="AU_END")
	private Timestamp auEnd;

	@Column(name="AU_START")
	private Timestamp auStart;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="DOING_END")
	private Timestamp doingEnd;

	@Column(name="DOING_START")
	private Timestamp doingStart;

	@Column(name="SC_END")
	private Timestamp scEnd;

	@Column(name="SC_START")
	private Timestamp scStart;

	private byte type;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	public PerAssessPeriod() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssessPaperId() {
		return this.assessPaperId;
	}

	public void setAssessPaperId(String assessPaperId) {
		this.assessPaperId = assessPaperId;
	}

	public Timestamp getAuEnd() {
		return this.auEnd;
	}

	public void setAuEnd(Timestamp auEnd) {
		this.auEnd = auEnd;
	}

	public Timestamp getAuStart() {
		return this.auStart;
	}

	public void setAuStart(Timestamp auStart) {
		this.auStart = auStart;
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

	public Timestamp getDoingEnd() {
		return this.doingEnd;
	}

	public void setDoingEnd(Timestamp doingEnd) {
		this.doingEnd = doingEnd;
	}

	public Timestamp getDoingStart() {
		return this.doingStart;
	}

	public void setDoingStart(Timestamp doingStart) {
		this.doingStart = doingStart;
	}

	public Timestamp getScEnd() {
		return this.scEnd;
	}

	public void setScEnd(Timestamp scEnd) {
		this.scEnd = scEnd;
	}

	public Timestamp getScStart() {
		return this.scStart;
	}

	public void setScStart(Timestamp scStart) {
		this.scStart = scStart;
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