package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PER_ASSESS_ANSWER_ITEM database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_ANSWER_ITEM")
@NamedQuery(name="PerAssessAnswerItem.findAll", query="SELECT p FROM PerAssessAnswerItem p")
public class PerAssessAnswerItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="SEQ_NO")
	private Short seqNo;

	@Column(name="TYPE")
	private Byte type;

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

	@Column(name="AUDIT_COMMENT")
	private String auditComment;

	@Column(name="TEMPLATE_ID")
	private String templateId;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	@Column(name="ASSESS_ANSWER_ID", insertable = false, updatable = false)
	private String assessAnswerId;

	//bi-directional many-to-one association to PerAssessAnswerDetail
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, mappedBy="perAssessAnswerItem")
	private List<PerAssessAnswerDetail> perAssessAnswerDetails;

	//bi-directional many-to-one association to PerAssessAnswer
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="ASSESS_ANSWER_ID")
	private PerAssessAnswer perAssessAnswer;

	public PerAssessAnswerItem() {
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

	public Short getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Short seqNo) {
		this.seqNo = seqNo;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public List<PerAssessAnswerDetail> getPerAssessAnswerDetails() {
		return this.perAssessAnswerDetails;
	}

	public void setPerAssessAnswerDetails(List<PerAssessAnswerDetail> perAssessAnswerDetails) {
		this.perAssessAnswerDetails = perAssessAnswerDetails;
	}

	public PerAssessAnswerDetail addPerAssessAnswerDetail(PerAssessAnswerDetail perAssessAnswerDetail) {
		getPerAssessAnswerDetails().add(perAssessAnswerDetail);
		perAssessAnswerDetail.setPerAssessAnswerItem(this);

		return perAssessAnswerDetail;
	}

	public PerAssessAnswerDetail removePerAssessAnswerDetail(PerAssessAnswerDetail perAssessAnswerDetail) {
		getPerAssessAnswerDetails().remove(perAssessAnswerDetail);
		perAssessAnswerDetail.setPerAssessAnswerItem(null);

		return perAssessAnswerDetail;
	}

	public PerAssessAnswer getPerAssessAnswer() {
		return this.perAssessAnswer;
	}

	public void setPerAssessAnswer(PerAssessAnswer perAssessAnswer) {
		this.perAssessAnswer = perAssessAnswer;
	}

}