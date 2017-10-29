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

	@Column(name="ASSESS_ID")
	private String assessId;

	@Column(name="ASSESS_PAPER_ID")
	private String assessPaperId;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAnswerItem
	@OneToMany(mappedBy="perAssessAnswer")
	private List<PerAssessAnswerItem> perAssessAnswerItems;

	public PerAssessAnswer() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssessId() {
		return this.assessId;
	}

	public void setAssessId(String assessId) {
		this.assessId = assessId;
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

	public List<PerAssessAnswerItem> getPerAssessAnswerItems() {
		return this.perAssessAnswerItems;
	}

	public void setPerAssessAnswerItems(List<PerAssessAnswerItem> perAssessAnswerItems) {
		this.perAssessAnswerItems = perAssessAnswerItems;
	}

	public PerAssessAnswerItem addPerAssessAnswerItem(PerAssessAnswerItem perAssessAnswerItem) {
		getPerAssessAnswerItems().add(perAssessAnswerItem);
		perAssessAnswerItem.setPerAssessAnswer(this);

		return perAssessAnswerItem;
	}

	public PerAssessAnswerItem removePerAssessAnswerItem(PerAssessAnswerItem perAssessAnswerItem) {
		getPerAssessAnswerItems().remove(perAssessAnswerItem);
		perAssessAnswerItem.setPerAssessAnswer(null);

		return perAssessAnswerItem;
	}

}