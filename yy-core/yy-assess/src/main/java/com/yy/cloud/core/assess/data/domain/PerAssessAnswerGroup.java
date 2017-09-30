package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the PER_ASSESS_ANSWER_GROUP database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_ANSWER_GROUP")
@NamedQuery(name="PerAssessAnswerGroup.findAll", query="SELECT p FROM PerAssessAnswerGroup p")
public class PerAssessAnswerGroup implements Serializable {
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

	@Column(name="GROUP_NO")
	private String groupNo;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="TEMPLATE_ID")
	private String templateId;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAnswer
	@ManyToOne
	@JoinColumn(name="ASSESS_ANSWER_ID")
	private PerAssessAnswer perAssessAnswer;

	//bi-directional many-to-one association to PerAssess
	@ManyToOne
	@JoinColumn(name="ASSESS_ID")
	private PerAssess perAssess;

	//bi-directional many-to-one association to PerAssessPaper
	@ManyToOne
	@JoinColumn(name="ASSESS_PAPER_ID")
	private PerAssessPaper perAssessPaper;

	public PerAssessAnswerGroup() {
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

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public PerAssessAnswer getPerAssessAnswer() {
		return this.perAssessAnswer;
	}

	public void setPerAssessAnswer(PerAssessAnswer perAssessAnswer) {
		this.perAssessAnswer = perAssessAnswer;
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

}