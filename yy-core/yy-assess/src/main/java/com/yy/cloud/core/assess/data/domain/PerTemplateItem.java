package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the PER_TEMPLATE_ITEM database table.
 * 
 */
@Entity
@Table(name="PER_TEMPLATE_ITEM")
@NamedQuery(name="PerTemplateItem.findAll", query="SELECT p FROM PerTemplateItem p")
public class PerTemplateItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CODE")
	private String code;

	@Column(name="COMMENTS")
	private String comments;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="DEFAULT_VALUE")
	private String defaultValue;

	@Column(name="`LABEL`")
	private String label;

	@Column(name="NAME")
	private String name;

	@Column(name="OPTION_TYPE")
	private byte optionType;

	@Column(name="PLACEHOLDER_TIP")
	private String placeholderTip;

	@Column(name="REG_EXP")
	private String regExp;

	@Column(name="REG_EXP_EXC")
	private String regExpExc;

	@Column(name="STATUS")
	private byte status;

	@Column(name="TENANT_ID")
	private String tenantId;

	@Column(name="TIP")
	private String tip;

	@Column(name="TYPE")
	private byte type;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	@Column(name="VALUE_DOMAIN")
	private byte valueDomain;

	@Column(name="VALUE_SOURCE")
	private String valueSource;

	//bi-directional many-to-one association to PerTemplateTiMap
	@OneToMany(mappedBy="perTemplateItem")
	private List<PerTemplateTiMap> perTemplateTiMaps;

	public PerTemplateItem() {
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getOptionType() {
		return this.optionType;
	}

	public void setOptionType(byte optionType) {
		this.optionType = optionType;
	}

	public String getPlaceholderTip() {
		return this.placeholderTip;
	}

	public void setPlaceholderTip(String placeholderTip) {
		this.placeholderTip = placeholderTip;
	}

	public String getRegExp() {
		return this.regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	public String getRegExpExc() {
		return this.regExpExc;
	}

	public void setRegExpExc(String regExpExc) {
		this.regExpExc = regExpExc;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTip() {
		return this.tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public byte getValueDomain() {
		return this.valueDomain;
	}

	public void setValueDomain(byte valueDomain) {
		this.valueDomain = valueDomain;
	}

	public String getValueSource() {
		return this.valueSource;
	}

	public void setValueSource(String valueSource) {
		this.valueSource = valueSource;
	}

	public List<PerTemplateTiMap> getPerTemplateTiMaps() {
		return this.perTemplateTiMaps;
	}

	public void setPerTemplateTiMaps(List<PerTemplateTiMap> perTemplateTiMaps) {
		this.perTemplateTiMaps = perTemplateTiMaps;
	}

	public PerTemplateTiMap addPerTemplateTiMap(PerTemplateTiMap perTemplateTiMap) {
		getPerTemplateTiMaps().add(perTemplateTiMap);
		perTemplateTiMap.setPerTemplateItem(this);

		return perTemplateTiMap;
	}

	public PerTemplateTiMap removePerTemplateTiMap(PerTemplateTiMap perTemplateTiMap) {
		getPerTemplateTiMaps().remove(perTemplateTiMap);
		perTemplateTiMap.setPerTemplateItem(null);

		return perTemplateTiMap;
	}

}