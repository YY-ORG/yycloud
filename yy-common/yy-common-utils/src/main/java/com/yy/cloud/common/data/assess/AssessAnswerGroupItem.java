/**
 * Project Name:yy-common-utils
 * File Name:AssessAnswerGroupItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:40:32 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * ClassName:AssessAnswerGroupItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:40:32 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class AssessAnswerGroupItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -9029521588266943330L;
	private String id;
	private Timestamp createDate;
	private String creatorId;
	private String groupNo;
	private Byte status;
	private String templateId;
	private Timestamp updateDate;
}
