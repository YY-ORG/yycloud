package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the PER_TEMPLATE_TI_MAP database table.
 * 
 */
@Entity
@Table(name="PER_TEMPLATE_TI_MAP")
@NamedQuery(name="PerTemplateTiMap.findAll", query="SELECT p FROM PerTemplateTiMap p")
public class PerTemplateTiMap implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="RELIED_ID")
	private String reliedId;

	@Column(name="RWM_TYPE")
	private Byte rwmType;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="ORDER_SEQ")
	private Integer seqNo;

	@Column(name="MANDATORY")
	private Boolean mandatory;

	@Column(name="EDITABLE")
	private Boolean editable;

	@Column(name="VISIBLE")
	private Boolean visible;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to PerTemplate
	@ManyToOne
	@JoinColumn(name="TEMPLATE_ID")
	private PerTemplate perTemplate;

	//bi-directional many-to-one association to PerTemplateItem
	@ManyToOne
	@JoinColumn(name="TEMPLATE_ITEM_ID")
	private PerTemplateItem perTemplateItem;

	public PerTemplateTiMap() {
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

	public String getReliedId() {
		return this.reliedId;
	}

	public void setReliedId(String reliedId) {
		this.reliedId = reliedId;
	}

	public Byte getRwmType() {
		return this.rwmType;
	}

	public void setRwmType(Byte rwmType) {
		this.rwmType = rwmType;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean isEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public PerTemplate getPerTemplate() {
		return this.perTemplate;
	}

	public void setPerTemplate(PerTemplate perTemplate) {
		this.perTemplate = perTemplate;
	}

	public PerTemplateItem getPerTemplateItem() {
		return this.perTemplateItem;
	}

	public void setPerTemplateItem(PerTemplateItem perTemplateItem) {
		this.perTemplateItem = perTemplateItem;
	}

}