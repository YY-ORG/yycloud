package com.yy.cloud.core.assess.data.domain;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the per_apac_examinee_map database table.
 * 
 */
@Entity
@Table(name="per_apac_examinee_map")
@NamedQuery(name="PerApacExamineeMap.findAll", query="SELECT p FROM PerApacExamineeMap p")
public class PerApacExamineeMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="AP_AC_MAP_ID")
	private String apAcMapId;

	@Column(name="AUDIT_SCORE")
	private BigDecimal auditScore = BigDecimal.ZERO;

	private String auditor;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="MARKED_SCORE")
	private BigDecimal markedScore = BigDecimal.ZERO;

	@Column(name="R_MARKED_SCORE")
	private BigDecimal rMarkedScore;

	@Column(name="R_AUDIT_SCORE")
	private BigDecimal rAuditScore;

	private String marker;

	private Byte status;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	@Column(name="APEM_ID", insertable = false, updatable = false)
	private String apemId;

	//bi-directional many-to-one association to PerAssessPaperExamineeMap
	@ManyToOne
	@JoinColumn(name="APEM_ID")
	private PerAssessPaperExamineeMap perAssessPaperExamineeMap;

	public PerApacExamineeMap() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApAcMapId() {
		return this.apAcMapId;
	}

	public void setApAcMapId(String apAcMapId) {
		this.apAcMapId = apAcMapId;
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

	public BigDecimal getMarkedScore() {
		return this.markedScore;
	}

	public void setMarkedScore(BigDecimal markedScore) {
		this.markedScore = markedScore;
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

	public String getApemId() {
		return apemId;
	}

	public void setApemId(String apemId) {
		this.apemId = apemId;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public PerAssessPaperExamineeMap getPerAssessPaperExamineeMap() {
		return this.perAssessPaperExamineeMap;
	}

	public void setPerAssessPaperExamineeMap(PerAssessPaperExamineeMap perAssessPaperExamineeMap) {
		this.perAssessPaperExamineeMap = perAssessPaperExamineeMap;
	}

}