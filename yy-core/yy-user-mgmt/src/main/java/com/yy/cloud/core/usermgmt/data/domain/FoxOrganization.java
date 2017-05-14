package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FOX_ORGANIZATION")
@NamedQuery(name = "FoxOrganization.findAll", query = "SELECT f FROM FoxOrganization f")
@DynamicInsert
@DynamicUpdate
@Data
public class FoxOrganization implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private Byte status;

	@Column(name = "AREA_ID")
	private String areaId;

	@Column(name = "LEADER_ID")
	private String leaderId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TENANT_ID")
	private String tenantId;

	@Column(name = "PARENT_ID")
	private String parentId;

	@Column(name = "GRADE")
	private Byte grade;

	@Column(name = "CREATE_BY")
	private String createBy;

	@Column(name = "CREATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "UPDATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name = "CODE")
	private String code;

	@Column(name = "TYPE")
	private Byte type;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "FEE_TYPE")
	private Byte feeType;

	@Column(name = "FEE_PERIOD")
	private Byte feePeriod;

	@Column(name = "BILL_GEN_DATE")
	private Byte billGenDate;

	@Column(name = "BILL_SEND_DATE")
	private Byte billSendDate;

	@Column(name = "EXIT_FEE_POLICY")
	private Byte exitFeePolicy;

	@Column(name = "SPECIAL_FEE_POLICY")
	private Byte specialFeePolicy;

	@Column(name = "FEE_START_POLICY")
	private Byte feeStartPolicy;

	@Column(name = "FISCAL_MONTH")
	private Byte fiscalMonth;

	@Column(name = "FISCAL_DATE")
	private Byte fiscalDate;

}