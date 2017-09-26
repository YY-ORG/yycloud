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
	private byte rwmType;

	@Column(name="STATUS")
	private byte status;

	@Column(name="TEMPLATE_ID")
	private String templateId;

	@Column(name="TEMPLATE_ITEM_ID")
	private String templateItemId;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

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

	public byte getRwmType() {
		return this.rwmType;
	}

	public void setRwmType(byte rwmType) {
		this.rwmType = rwmType;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateItemId() {
		return this.templateItemId;
	}

	public void setTemplateItemId(String templateItemId) {
		this.templateItemId = templateItemId;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}