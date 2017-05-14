package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the FOX_TENANT_PALTFORM_RELATION database table.
 * 
 */
@Entity
@Table(name="FOX_TENANT_PALTFORM_RELATION")
@NamedQuery(name="FoxTenantPaltformRelation.findAll", query="SELECT f FROM FoxTenantPaltformRelation f")
public class FoxTenantPaltformRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE", updatable = false)
	private Date createDate;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="PLATFORM_ID")
	private String platformId;

	@Column(name="TENANT_ID")
	private String tenantId;

	public FoxTenantPaltformRelation() {
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}