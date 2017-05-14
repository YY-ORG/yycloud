package com.yy.cloud.common.data.sysbase;

import java.io.Serializable;

import lombok.Data;

import java.sql.Timestamp;


/**
 * The persistent class for the YY_SYSTEMDICTIONARY database table.
 * 
 */
@Data
public class SystemDictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String code;

	private String description;

	private String field;

	private String owner;

	private String value;

	private String displayValue;
	
	private String tenantId;

	private Timestamp createDate;

	private Timestamp updateDate;

}