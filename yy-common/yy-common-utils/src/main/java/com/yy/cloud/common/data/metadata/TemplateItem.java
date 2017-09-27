/**
 * Project Name:yy-common-utils
 * File Name:TemplateItem.java
 * Package Name:com.yy.cloud.common.data.metadata
 * Date:Sep 27, 20178:24:55 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.metadata;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * ClassName:TemplateItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:24:55 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class TemplateItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 4835675123937585370L;

	private String id;
	private String name;
	private String code;
	private byte status;
	private byte type;
	private Timestamp createDate;
	private Timestamp updateDate;
	
	private List<TemplateItemItem> templateItemItemList;
}
