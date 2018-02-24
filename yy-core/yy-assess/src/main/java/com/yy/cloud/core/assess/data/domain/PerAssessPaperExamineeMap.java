package com.yy.cloud.core.assess.data.domain;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


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

	@Column(name="AUDIT_SCORE")
	private BigDecimal auditScore;

	private String auditor;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="DEPT_ID")
	private String deptId;

	@Column(name="MARKED_SCORE")
	private BigDecimal markedScore;

	private String marker;

	private Byte status;

	private Byte title;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerApacExamineeMap
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy="perAssessPaperExamineeMap")
	private List<PerApacExamineeMap> perApacExamineeMaps;

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

	public BigDecimal getAuditScore() {
		return this.auditScore;
	}

	public void setAuditScore(BigDecimal auditScore) {
		this.auditScore = auditScore;
	}

	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
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

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public BigDecimal getMarkedScore() {
		return this.markedScore;
	}

	public void setMarkedScore(BigDecimal markedScore) {
		this.markedScore = markedScore;
	}

	public String getMarker() {
		return this.marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getTitle() {
		return this.title;
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

	public List<PerApacExamineeMap> getPerApacExamineeMaps() {
		return this.perApacExamineeMaps;
	}

	public void setPerApacExamineeMaps(List<PerApacExamineeMap> perApacExamineeMaps) {
		this.perApacExamineeMaps = perApacExamineeMaps;
	}

	public PerApacExamineeMap addPerApacExamineeMap(PerApacExamineeMap perApacExamineeMap) {
		getPerApacExamineeMaps().add(perApacExamineeMap);
		perApacExamineeMap.setPerAssessPaperExamineeMap(this);

		return perApacExamineeMap;
	}

	public PerApacExamineeMap removePerApacExamineeMap(PerApacExamineeMap perApacExamineeMap) {
		getPerApacExamineeMaps().remove(perApacExamineeMap);
		perApacExamineeMap.setPerAssessPaperExamineeMap(null);

		return perApacExamineeMap;
	}

}