package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.yy.cloud.core.assess.data.PerAssessPaper;

import java.sql.Timestamp;


/**
 * The persistent class for the per_assess_org_map database table.
 * 
 */
@Entity
@Table(name="per_assess_org_map")
@NamedQuery(name="PerAssessOrgMap.findAll", query="SELECT p FROM PerAssessOrgMap p")
public class PerAssessOrgMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATOR_DATE")
	private Timestamp creatorDate;

	@Column(name="ORG_ID")
	private String orgId;

	private byte status;

	@Column(name="TITLE_TYPE")
	private byte titleType;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessPaper
	@ManyToOne
	@JoinColumn(name="ASSESS_PAPER_ID")
	private PerAssessPaper perAssessPaper;

	public PerAssessOrgMap() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreatorDate() {
		return this.creatorDate;
	}

	public void setCreatorDate(Timestamp creatorDate) {
		this.creatorDate = creatorDate;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getTitleType() {
		return this.titleType;
	}

	public void setTitleType(byte titleType) {
		this.titleType = titleType;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public PerAssessPaper getPerAssessPaper() {
		return this.perAssessPaper;
	}

	public void setPerAssessPaper(PerAssessPaper perAssessPaper) {
		this.perAssessPaper = perAssessPaper;
	}

}