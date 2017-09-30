/**
 * Project Name:yy-common-utils
 * File Name:AssessItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:28:45 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import com.yy.cloud.common.data.metadata.TemplateItem;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * ClassName:AssessItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:28:45 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class AssessItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 5958184459296350706L;

	private String id;
	private String name;
	private String code;
	private Byte status;
	private Byte type;
	private List<TemplateItem> templateItemList;
	private Timestamp createDate;
	private Timestamp updateDate;
}
