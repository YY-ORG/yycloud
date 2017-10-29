package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PER_ASSESS_ANSWER_ITEM database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_ANSWER_ITEM")
@NamedQuery(name="PerAssessAnswerItem.findAll", query="SELECT p FROM PerAssessAnswerItem p")
public class PerAssessAnswerItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="SEQ_NO")
	private Short seqNo;

	@Column(name="TEMPLATE_ID")
	private String templateId;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAnswerDetail
	@OneToMany(mappedBy="perAssessAnswerItem")
	private List<PerAssessAnswerDetail> perAssessAnswerDetails;

	//bi-directional many-to-one association to PerAssessAnswer
	@ManyToOne
	@JoinColumn(name="ASSESS_ANSWER_ID")
	private PerAssessAnswer perAssessAnswer;

	public PerAssessAnswerItem() {
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

	public Short getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Short seqNo) {
		this.seqNo = seqNo;
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

	public List<PerAssessAnswerDetail> getPerAssessAnswerDetails() {
		return this.perAssessAnswerDetails;
	}

	public void setPerAssessAnswerDetails(List<PerAssessAnswerDetail> perAssessAnswerDetails) {
		this.perAssessAnswerDetails = perAssessAnswerDetails;
	}

	public PerAssessAnswerDetail addPerAssessAnswerDetail(PerAssessAnswerDetail perAssessAnswerDetail) {
		getPerAssessAnswerDetails().add(perAssessAnswerDetail);
		perAssessAnswerDetail.setPerAssessAnswerItem(this);

		return perAssessAnswerDetail;
	}

	public PerAssessAnswerDetail removePerAssessAnswerDetail(PerAssessAnswerDetail perAssessAnswerDetail) {
		getPerAssessAnswerDetails().remove(perAssessAnswerDetail);
		perAssessAnswerDetail.setPerAssessAnswerItem(null);

		return perAssessAnswerDetail;
	}

	public PerAssessAnswer getPerAssessAnswer() {
		return this.perAssessAnswer;
	}

	public void setPerAssessAnswer(PerAssessAnswer perAssessAnswer) {
		this.perAssessAnswer = perAssessAnswer;
	}

}