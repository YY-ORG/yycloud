package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

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

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="DISPLAY_VALUE")
	private String displayValue;

	@Column(name="SEQ_NO")
	private Byte seqNo;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="TEMPLATE_CODE")
	private String templateCode;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name="VALUE")
	private String value;

	@Column(name="VALUE_TYPE")
	private Byte valueType;

	//bi-directional many-to-one association to PerAssess
	@ManyToOne
	@JoinColumn(name="ASSESS_ID")
	private PerAssess perAssess;

	//bi-directional many-to-one association to PerAssessAnswerGroup
	@OneToMany(mappedBy="perAssessAnswer")
	private List<PerAssessAnswerGroup> perAssessAnswerGroups;

	public PerAssessAnswer() {
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

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getDisplayValue() {
		return this.displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public Byte getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Byte seqNo) {
		this.seqNo = seqNo;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTemplateCode() {
		return this.templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Byte getValueType() {
		return this.valueType;
	}

	public void setValueType(Byte valueType) {
		this.valueType = valueType;
	}

	public PerAssess getPerAssess() {
		return this.perAssess;
	}

	public void setPerAssess(PerAssess perAssess) {
		this.perAssess = perAssess;
	}

	public List<PerAssessAnswerGroup> getPerAssessAnswerGroups() {
		return this.perAssessAnswerGroups;
	}

	public void setPerAssessAnswerGroups(List<PerAssessAnswerGroup> perAssessAnswerGroups) {
		this.perAssessAnswerGroups = perAssessAnswerGroups;
	}

	public PerAssessAnswerGroup addPerAssessAnswerGroup(PerAssessAnswerGroup perAssessAnswerGroup) {
		getPerAssessAnswerGroups().add(perAssessAnswerGroup);
		perAssessAnswerGroup.setPerAssessAnswer(this);

		return perAssessAnswerGroup;
	}

	public PerAssessAnswerGroup removePerAssessAnswerGroup(PerAssessAnswerGroup perAssessAnswerGroup) {
		getPerAssessAnswerGroups().remove(perAssessAnswerGroup);
		perAssessAnswerGroup.setPerAssessAnswer(null);

		return perAssessAnswerGroup;
	}

}