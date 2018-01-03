package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the per_asp_process_overview database table.
 * 
 */
@Entity
@Table(name="per_asp_process_overview")
@NamedQuery(name="PerAspProcessOverview.findAll", query="SELECT p FROM PerAspProcessOverview p")
public class PerAspProcessOverview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="ASSESS_COUNT")
	private int assessCount;

	@Column(name="ASSESS_PAPER_ID")
	private String assessPaperId;

	@Column(name="CATEGORY_ID")
	private String categoryId;

	@Column(name="COMPLETED_COUNT")
	private int completedCount;

	@Column(name="MARKED_COUNT")
	private int markedCount;

	@Column(name="AUDIT_COUNT")
	private int auditCount;

	@Column(name="MARKED_SCORE")
	private BigDecimal markedScore;

	@Column(name="AUDIT_SCORE")
	private BigDecimal auditScore;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	public PerAspProcessOverview() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAssessCount() {
		return this.assessCount;
	}

	public void setAssessCount(int assessCount) {
		this.assessCount = assessCount;
	}

	public String getAssessPaperId() {
		return this.assessPaperId;
	}

	public void setAssessPaperId(String assessPaperId) {
		this.assessPaperId = assessPaperId;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getCompletedCount() {
		return this.completedCount;
	}

	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	public int getMarkedCount() {
		return markedCount;
	}

	public void setMarkedCount(int markedCount) {
		this.markedCount = markedCount;
	}

	public int getAuditCount() {
		return auditCount;
	}

	public void setAuditCount(int auditCount) {
		this.auditCount = auditCount;
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

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}