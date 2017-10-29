package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the PER_ASSESS_ANSWER_DETAIL database table.
 * 
 */
@Entity
@Table(name="PER_ASSESS_ANSWER_DETAIL")
@NamedQuery(name="PerAssessAnswerDetail.findAll", query="SELECT p FROM PerAssessAnswerDetail p")
public class PerAssessAnswerDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="ITEM_CODE")
	private String itemCode;

	@Column(name="ITEM_ID")
	private String itemId;

	@Column(name="ITEM_VALUE")
	private String itemValue;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerAssessAnswerItem
	@ManyToOne
	@JoinColumn(name="ASSESS_ANSWER_ITEM_ID")
	private PerAssessAnswerItem perAssessAnswerItem;

	public PerAssessAnswerDetail() {
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

	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public PerAssessAnswerItem getPerAssessAnswerItem() {
		return this.perAssessAnswerItem;
	}

	public void setPerAssessAnswerItem(PerAssessAnswerItem perAssessAnswerItem) {
		this.perAssessAnswerItem = perAssessAnswerItem;
	}

}