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

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="NAME")
	private String name;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAspMap
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy="perAssessPaper")
	private List<PerAssessAspMap> perAssessAspMaps;
	
	//bi-directional many-to-one association to PerAssessOrgMap
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy="perAssessPaper")
	private List<PerAssessOrgMap> perAssessOrgMaps;

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

	public List<PerAssessOrgMap> getPerAssessOrgMaps() {
		return this.perAssessOrgMaps;
	}

	public void setPerAssessOrgMaps(List<PerAssessOrgMap> perAssessOrgMaps) {
		this.perAssessOrgMaps = perAssessOrgMaps;
	}

	public PerAssessOrgMap addPerAssessOrgMap(PerAssessOrgMap perAssessOrgMap) {
		getPerAssessOrgMaps().add(perAssessOrgMap);
		perAssessOrgMap.setPerAssessPaper(this);

		return perAssessOrgMap;
	}

	public PerAssessOrgMap removePerAssessOrgMap(PerAssessOrgMap perAssessOrgMap) {
		getPerAssessOrgMaps().remove(perAssessOrgMap);
		perAssessOrgMap.setPerAssessPaper(null);

		return perAssessOrgMap;
	}
}