package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the per_assess_paper_examinee_map database table.
 * 
 */
@Entity
@Table(name="per_assess_paper_examinee_map")
@NamedQuery(name="PerAssessPaperExamineeMap.findAll", query="SELECT p FROM PerAssessPaperExamineeMap p")
public class PerAssessPaperExamineeMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="ASSESS_PAPER_ID")
	private String assessPaperId;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="CREATOR_ID")
	private String creatorId;

	private Byte status;

	private Byte title;

	@Column(name="MARKED_SCORE")
	private BigDecimal markedScore;

	@Column(name="AUDIT_SCORE")
	private BigDecimal auditScore;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	public PerAssessPaperExamineeMap() {
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

	public Byte getStatus() {
		return this.status;
	}

	public BigDecimal getMarkedScore() {
		return markedScore;
	}

	public void setMarkedScore(BigDecimal markedScore) {
		this.markedScore = markedScore;
	}

	public BigDecimal getAuditScore() {
		return auditScore;
	}

	public void setAuditScore(BigDecimal auditScore) {
		this.auditScore = auditScore;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Byte getTitle() {
		return title;
	}

	public void setTitle(Byte title) {
		this.title = title;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}