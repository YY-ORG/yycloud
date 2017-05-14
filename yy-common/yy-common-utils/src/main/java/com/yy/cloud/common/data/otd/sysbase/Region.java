package com.yy.cloud.common.data.otd.sysbase;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Region implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String code;

	private Timestamp createDate;

	private String description;

	private String name;

	private String parentId;

	private byte status;

	private Timestamp updateDate;
}
