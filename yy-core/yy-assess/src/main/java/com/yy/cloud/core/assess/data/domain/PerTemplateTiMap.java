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

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="RELIED_ID")
	private String reliedId;

	@Column(name="RWM_TYPE")
	private Byte rwmType;

	@Column(name="STATUS")
	private Byte status;

	@Column(name="SEQ_NO")
	private Integer seqNo;

	@Column(name="MANDATORY")
	private Boolean mandatory;

	@Column(name="EDITABLE")
	private Boolean editable;

	@Column(name="EX_EDITABLE")
	private Boolean exEditable;

	@Column(name="SC_EDITABLE")
	private Boolean scEditable;

	@Column(name="AU_EDITABLE")
	private Boolean auEditable;

	@Column(name="VISIBLE")
	private Boolean visible;

	@Column(name="EX_VISIBLE")
	private Boolean exVisible;

	@Column(name="SC_VISIBLE")
	private Boolean scVisible;

	@Column(name="AU_VISIBLE")
	private Boolean auVisible;

	@Column(name="VALUE_FROM")
	private String valueFrom;

	@Column(name="MIN_VALUE")
	private String minValue;

	@Column(name="MAX_VALUE")
	private String maxValue;

	@Column(name="FAILED_MSG")
	private String failedMsg;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	@Column(name="TEMPLATE_ITEM_ID")
	private String templateItemId;

	@Column(name="TEMPLATE_ID", insertable = false, updatable = false)
	private String templateId;

	//bi-directional many-to-one association to PerTemplate
	@ManyToOne
	@JoinColumn(name="TEMPLATE_ID")
	private PerTemplate perTemplate;

	//bi-directional many-to-one association to PerTemplateItem
	@ManyToOne
	@JoinColumn(name="TEMPLATE_ITEM_ID", insertable = false, updatable = false)
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
	public String getValueFrom() {
		return valueFrom;
	}

	public Boolean getExEditable() {
		return exEditable;
	}

	public void setExEditable(Boolean exEditable) {
		this.exEditable = exEditable;
	}

	public Boolean getScEditable() {
		return scEditable;
	}

	public void setScEditable(Boolean scEditable) {
		this.scEditable = scEditable;
	}

	public Boolean getAuEditable() {
		return auEditable;
	}

	public void setAuEditable(Boolean auEditable) {
		this.auEditable = auEditable;
	}

	public Boolean getExVisible() {
		return exVisible;
	}

	public void setExVisible(Boolean exVisible) {
		this.exVisible = exVisible;
	}

	public Boolean getScVisible() {
		return scVisible;
	}

	public void setScVisible(Boolean scVisible) {
		this.scVisible = scVisible;
	}

	public Boolean getAuVisible() {
		return auVisible;
	}

	public void setAuVisible(Boolean auVisible) {
		this.auVisible = auVisible;
	}

	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getFailedMsg() {
		return failedMsg;
	}

	public void setFailedMsg(String failedMsg) {
		this.failedMsg = failedMsg;
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


	public String getTemplateItemId() {
		return templateItemId;
	}

	public void setTemplateItemId(String templateItemId) {
		this.templateItemId = templateItemId;
	}
}