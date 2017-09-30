package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PER_ASSESS_PAPER database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_PAPER")
@NamedQuery(name="PerAssessPaper.findAll", query="SELECT p FROM PerAssessPaper p")
public class PerAssessPaper implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CODE")
	private String code;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="NAME")
	private String name;

	@Column(name="ORG_ID")
	private String orgId;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAnswerGroup
	@OneToMany(mappedBy="perAssessPaper")
	private List<PerAssessAnswerGroup> perAssessAnswerGroups;

	//bi-directional many-to-one association to PerAssessAspMap
	@OneToMany(mappedBy="perAssessPaper")
	private List<PerAssessAspMap> perAssessAspMaps;

	public PerAssessPaper() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public List<PerAssessAnswerGroup> getPerAssessAnswerGroups() {
		return this.perAssessAnswerGroups;
	}

	public void setPerAssessAnswerGroups(List<PerAssessAnswerGroup> perAssessAnswerGroups) {
		this.perAssessAnswerGroups = perAssessAnswerGroups;
	}

	public PerAssessAnswerGroup addPerAssessAnswerGroup(PerAssessAnswerGroup perAssessAnswerGroup) {
		getPerAssessAnswerGroups().add(perAssessAnswerGroup);
		perAssessAnswerGroup.setPerAssessPaper(this);

		return perAssessAnswerGroup;
	}

	public PerAssessAnswerGroup removePerAssessAnswerGroup(PerAssessAnswerGroup perAssessAnswerGroup) {
		getPerAssessAnswerGroups().remove(perAssessAnswerGroup);
		perAssessAnswerGroup.setPerAssessPaper(null);

		return perAssessAnswerGroup;
	}

	public List<PerAssessAspMap> getPerAssessAspMaps() {
		return this.perAssessAspMaps;
	}

	public void setPerAssessAspMaps(List<PerAssessAspMap> perAssessAspMaps) {
		this.perAssessAspMaps = perAssessAspMaps;
	}

	public PerAssessAspMap addPerAssessAspMap(PerAssessAspMap perAssessAspMap) {
		getPerAssessAspMaps().add(perAssessAspMap);
		perAssessAspMap.setPerAssessPaper(this);

		return perAssessAspMap;
	}

	public PerAssessAspMap removePerAssessAspMap(PerAssessAspMap perAssessAspMap) {
		getPerAssessAspMaps().remove(perAssessAspMap);
		perAssessAspMap.setPerAssessPaper(null);

		return perAssessAspMap;
	}

}