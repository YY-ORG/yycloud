package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the per_assess_category database table.
 * 
 */
@Entity
@Table(name="per_assess_category")
@NamedQuery(name="PerAssessCategory.findAll", query="SELECT p FROM PerAssessCategory p")
public class PerAssessCategory implements Serializable {
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

	@Column(name="STATUS")
	private byte status;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerApAcMap
	@OneToMany(mappedBy="perAssessCategory")
	private List<PerApAcMap> perApAcMaps;

	public PerAssessCategory() {
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

	public List<PerApAcMap> getPerApAcMaps() {
		return this.perApAcMaps;
	}

	public void setPerApAcMaps(List<PerApAcMap> perApAcMaps) {
		this.perApAcMaps = perApAcMaps;
	}

	public PerApAcMap addPerApAcMap(PerApAcMap perApAcMap) {
		getPerApAcMaps().add(perApAcMap);
		perApAcMap.setPerAssessCategory(this);

		return perApAcMap;
	}

	public PerApAcMap removePerApAcMap(PerApAcMap perApAcMap) {
		getPerApAcMaps().remove(perApAcMap);
		perApAcMap.setPerAssessCategory(null);

		return perApAcMap;
	}

}