package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
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

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="SCORING_TYPE")
	private Byte scoringType;

	@Column(name="SCORING_RATIO")
	private BigDecimal scoringRatio = BigDecimal.ONE;

	@Column(name="SCORING_THRESHOLD")
	private BigDecimal scoringThreshold = BigDecimal.valueOf(100);

	@Column(name="ITEM_THRESHOLD")
	private BigDecimal itemThreshold = BigDecimal.ZERO;

	@Column(name="SEQ_NO")
	private Integer seqNo;

	@Column(name="EX_EDITABLE")
	private Boolean exEditable;

	@Column(name="SC_EDITABLE")
	private Boolean scEditable;

	@Column(name="AU_EDITABLE")
	private Boolean auEditable;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	@Column(name="ASSESS_ID")
	private String assessId;

	@Column(name="ASSESS_CATEGORY_ID")
	private String assessCategoryId;

	@Column(name="ASSESS_PAPER_ID", insertable = false, updatable = false)
	private String assessPaperId;

	//bi-directional many-to-one association to PerAssess
	@ManyToOne
	@JoinColumn(name="ASSESS_ID", insertable = false, updatable = false)
	private PerAssess perAssess;

	//bi-directional many-to-one association to PerAssessPaper
	@ManyToOne
	@JoinColumn(name="ASSESS_PAPER_ID")
	private PerAssessPaper perAssessPaper;

	@ManyToOne
	@JoinColumn(name="ASSESS_CATEGORY_ID", insertable = false, updatable = false)
	private PerAssessCategory perAssessCategory;

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

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getScoringType() {
		return scoringType;
	}

	public void setScoringType(Byte scoringType) {
		this.scoringType = scoringType;
	}

	public BigDecimal getScoringRatio() {
		return scoringRatio;
	}

	public void setScoringRatio(BigDecimal scoringRatio) {
		this.scoringRatio = scoringRatio;
	}

	public BigDecimal getScoringThreshold() {
		return scoringThreshold;
	}

	public void setScoringThreshold(BigDecimal scoringThreshold) {
		this.scoringThreshold = scoringThreshold;
	}

	public BigDecimal getItemThreshold() {
		return itemThreshold;
	}

	public void setItemThreshold(BigDecimal itemThreshold) {
		this.itemThreshold = itemThreshold;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Boolean getExEditable() {
		return exEditable;
	}

	public void setExEditable(Boolean exEditable) {
		this.exEditable = exEditable;
	}

	public Boolean getScEditable() {
		return scEditable;
	}

	public void setScEditable(Boolean scEditable) {
		this.scEditable = scEditable;
	}

	public Boolean getAuEditable() {
		return auEditable;
	}

	public void setAuEditable(Boolean auEditable) {
		this.auEditable = auEditable;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getAssessId() {
		return assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
	}

	public String getAssessPaperId() {
		return assessPaperId;
	}

	public void setAssessPaperId(String assessPaperId) {
		this.assessPaperId = assessPaperId;
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

	public String getAssessCategoryId() {
		return assessCategoryId;
	}

	public void setAssessCategoryId(String assessCategoryId) {
		this.assessCategoryId = assessCategoryId;
	}

	public PerAssessCategory getPerAssessCategory() {
		return perAssessCategory;
	}

	public void setPerAssessCategory(PerAssessCategory perAssessCategory) {
		this.perAssessCategory = perAssessCategory;
	}
}