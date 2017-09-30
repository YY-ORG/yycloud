/**
 * Project Name:yy-common-utils
 * File Name:ContentItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:56:03 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * ClassName:ContentItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:56:03 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class ContentItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 6864571945322643202L;
	private String id;
	private String content;
	private Timestamp createDate;
	private Byte status;
	private Timestamp updateDate;
}
