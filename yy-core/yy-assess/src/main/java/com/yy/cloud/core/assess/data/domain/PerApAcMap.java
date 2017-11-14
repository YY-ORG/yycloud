package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the per_ap_ac_map database table.
 * 
 */
@Entity
@Table(name="per_ap_ac_map")
@NamedQuery(name="PerApAcMap.findAll", query="SELECT p FROM PerApAcMap p")
public class PerApAcMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="ASSESS_PAPER_ID")
	private String assessPaperId;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="STATUS")
	private byte status;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	@Column(name="ASSESS_CATEGORY_ID")
	private String assessCategoryId;

	//bi-directional many-to-one association to PerAssessCategory
	@ManyToOne
	@JoinColumn(name="ASSESS_CATEGORY_ID", insertable = false, updatable = false)
	private PerAssessCategory perAssessCategory;

	public PerApAcMap() {
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

	public PerAssessCategory getPerAssessCategory() {
		return this.perAssessCategory;
	}

	public void setPerAssessCategory(PerAssessCategory perAssessCategory) {
		this.perAssessCategory = perAssessCategory;
	}

	public String getAssessCategoryId() {
		return assessCategoryId;
	}

	public void setAssessCategoryId(String assessCategoryId) {
		this.assessCategoryId = assessCategoryId;
	}
}