package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PER_ASSESS_ANSWER database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_ANSWER")
@NamedQuery(name="PerAssessAnswer.findAll", query="SELECT p FROM PerAssessAnswer p")
public class PerAssessAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="ASSESS_ID")
	private String assessId;

	@Column(name="ASSESS_PAPER_ID")
	private String assessPaperId;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="AUXILIARY_SCORE")
	private BigDecimal auxiliaryScore;

	@Column(name="MARKED_SCORE")
	private BigDecimal markedScore;

	@Column(name="AUDIT_SCORE")
	private BigDecimal auditScore;

	@Column(name="R_MARKED_SCORE")
	private BigDecimal rMarkedScore;

	@Column(name="R_AUDIT_SCORE")
	private BigDecimal rAuditScore;

	@Column(name="MARKED_COMMENT")
	private String markedComment;

	@Column(name="MARKER")
	private String marker;

	@Column(name="AUDITOR")
	private String auditor;

	@Column(name="AUDIT_COMMENT")
	private String auditComment;

	@Column(name="TYPE")
	private Byte type;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAnswerItem
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, mappedBy="perAssessAnswer")
	private List<PerAssessAnswerItem> perAssessAnswerItems;

	//bi-directional many-to-one association to PerAssessAnswer
	@ManyToOne
	@JoinColumn(name="ASSESS_ID", insertable = false, updatable = false)
	private PerAssess perAssess;

	public PerAssessAnswer() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssessId() {
		return this.assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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

	public BigDecimal getAuxiliaryScore() {
		return auxiliaryScore;
	}

	public void setAuxiliaryScore(BigDecimal auxiliaryScore) {
		this.auxiliaryScore = auxiliaryScore;
	}

	public BigDecimal getRMarkedScore() {
		return rMarkedScore;
	}

	public void setRMarkedScore(BigDecimal rMarkedScore) {
		this.rMarkedScore = rMarkedScore;
	}

	public BigDecimal getRAuditScore() {
		return rAuditScore;
	}

	public void setRAuditScore(BigDecimal rAuditScore) {
		this.rAuditScore = rAuditScore;
	}

	public String getMarkedComment() {
		return markedComment;
	}

	public void setMarkedComment(String markedComment) {
		this.markedComment = markedComment;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public BigDecimal getrMarkedScore() {
		return rMarkedScore;
	}

	public void setrMarkedScore(BigDecimal rMarkedScore) {
		this.rMarkedScore = rMarkedScore;
	}

	public BigDecimal getrAuditScore() {
		return rAuditScore;
	}

	public void setrAuditScore(BigDecimal rAuditScore) {
		this.rAuditScore = rAuditScore;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public List<PerAssessAnswerItem> getPerAssessAnswerItems() {
		return this.perAssessAnswerItems;
	}

	public void setPerAssessAnswerItems(List<PerAssessAnswerItem> perAssessAnswerItems) {
		this.perAssessAnswerItems = perAssessAnswerItems;
	}

	public PerAssessAnswerItem addPerAssessAnswerItem(PerAssessAnswerItem perAssessAnswerItem) {
		getPerAssessAnswerItems().add(perAssessAnswerItem);
		perAssessAnswerItem.setPerAssessAnswer(this);

		return perAssessAnswerItem;
	}

	public PerAssessAnswerItem removePerAssessAnswerItem(PerAssessAnswerItem perAssessAnswerItem) {
		getPerAssessAnswerItems().remove(perAssessAnswerItem);
		perAssessAnswerItem.setPerAssessAnswer(null);

		return perAssessAnswerItem;
	}

	public PerAssess getPerAssess() {
		return perAssess;
	}

	public void setPerAssess(PerAssess perAssess) {
		this.perAssess = perAssess;
	}
}