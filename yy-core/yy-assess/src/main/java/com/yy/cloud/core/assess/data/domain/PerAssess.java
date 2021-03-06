package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PER_ASSESS database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS")
@NamedQuery(name="PerAssess.findAll", query="SELECT p FROM PerAssess p")
public class PerAssess implements Serializable {
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

	@Column(name="NAME")
	private String name;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="TYPE")
	private Byte type;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

    //bi-directional many-to-one association to PerAssessAnswer
	@OneToMany(mappedBy="perAssess")
	private List<PerAssessAnswer> PerAssessAnswers;

	//bi-directional many-to-one association to PerAssessAspMap
	@OneToMany(mappedBy="perAssess")
	private List<PerAssessAspMap> perAssessAspMaps;

	//bi-directional many-to-one association to PerAssessTemplateMap
	@OneToMany(cascade = { CascadeType.PERSIST , CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy="perAssess")
	private List<PerAssessTemplateMap> perAssessTemplateMaps;

	public PerAssess() {
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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public List<PerAssessAnswer> getPerAssessAnswers() {
		return this.PerAssessAnswers;
	}

	public void setPerAssessAnswers(List<PerAssessAnswer> PerAssessAnswers) {
		this.PerAssessAnswers = PerAssessAnswers;
	}

	public PerAssessAnswer addPerAssessAnswer(PerAssessAnswer PerAssessAnswer) {
		getPerAssessAnswers().add(PerAssessAnswer);
		PerAssessAnswer.setPerAssess(this);

		return PerAssessAnswer;
	}

	public PerAssessAnswer removePerAssessAnswer(PerAssessAnswer PerAssessAnswer) {
		getPerAssessAnswers().remove(PerAssessAnswer);
		PerAssessAnswer.setPerAssess(null);

		return PerAssessAnswer;
	}

	public List<PerAssessAspMap> getPerAssessAspMaps() {
		return this.perAssessAspMaps;
	}

	public void setPerAssessAspMaps(List<PerAssessAspMap> perAssessAspMaps) {
		this.perAssessAspMaps = perAssessAspMaps;
	}

	public PerAssessAspMap addPerAssessAspMap(PerAssessAspMap perAssessAspMap) {
		getPerAssessAspMaps().add(perAssessAspMap);
		perAssessAspMap.setPerAssess(this);

		return perAssessAspMap;
	}

	public PerAssessAspMap removePerAssessAspMap(PerAssessAspMap perAssessAspMap) {
		getPerAssessAspMaps().remove(perAssessAspMap);
		perAssessAspMap.setPerAssess(null);

		return perAssessAspMap;
	}

	public List<PerAssessTemplateMap> getPerAssessTemplateMaps() {
		return this.perAssessTemplateMaps;
	}

	public void setPerAssessTemplateMaps(List<PerAssessTemplateMap> perAssessTemplateMaps) {
		this.perAssessTemplateMaps = perAssessTemplateMaps;
	}

	public PerAssessTemplateMap addPerAssessTemplateMap(PerAssessTemplateMap perAssessTemplateMap) {
		getPerAssessTemplateMaps().add(perAssessTemplateMap);
		perAssessTemplateMap.setPerAssess(this);

		return perAssessTemplateMap;
	}

	public PerAssessTemplateMap removePerAssessTemplateMap(PerAssessTemplateMap perAssessTemplateMap) {
		getPerAssessTemplateMaps().remove(perAssessTemplateMap);
		perAssessTemplateMap.setPerAssess(null);

		return perAssessTemplateMap;
	}

}