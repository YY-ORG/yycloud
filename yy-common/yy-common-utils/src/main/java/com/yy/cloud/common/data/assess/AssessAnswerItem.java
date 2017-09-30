/**
 * Project Name:yy-common-utils
 * File Name:AssessAnswerItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:37:13 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * ClassName:AssessAnswerItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:37:13 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class AssessAnswerItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -2544396187850379651L;

	private String id;
	private Timestamp createDate;
	private String creatorId;
	private String displayValue;
	private Byte seqNo;
	private Byte status;
	private String templateCode;
	private Timestamp updateDate;
	private String value;
	private Byte valueType;
}
