/**
 * Project Name:liz-common-utils
 * File Name:SendingMailInfo.java
 * Package Name:com.gemii.lizcloud.core.mailmgmt.dto
 * Date:Apr 24, 201611:55:55 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.mailmgmt;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * ClassName:SendingMailInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 24, 2016 11:55:55 AM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class MimeMailInfo implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 * 
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = -4274317279935374127L;
	
	private String tenant;
	private String[] to;
	private String[] cc;
	private String[] bCc;
	private String from;
	private int type = -1;//default is -1
	/**
	 * mailTemplate: this field is competitive with the type & tenant.
	 * 
	 * @since JDK 1.8
	 */
	private String mailTemplate;
	private Map<String, Object> subjectMap;
	private Map<String, Object> bodyMap;
}
