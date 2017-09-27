/**
 * Project Name:yy-common-utils
 * File Name:TemplateItemItem.java
 * Package Name:com.yy.cloud.common.data.metadata
 * Date:Sep 27, 20176:19:29 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.metadata;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * ClassName:TemplateItemItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 27, 2017 6:19:29 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class TemplateItemItem implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 7182247237411515320L;
	
	private String id;
	private String name;
	private String code;
	private String label;
	private String defaultValue;
	private String placeholderTip;
	private String regExp;
	private String regExpExc;
	private byte optionType;
	private byte status;
	private byte type;
	private String tip;
	private String valueSource;

	private Timestamp createDate;
	private Timestamp updateDate;
}

