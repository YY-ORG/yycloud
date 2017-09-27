/**
 * Project Name:yy-common-utils
 * File Name:AssessAspMapItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:42:19 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * ClassName:AssessAspMapItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:42:19 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class AssessAspMapItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -6720228966326718122L;
	private String id;
	private Timestamp createDate;
	private byte status;
	private Timestamp updateDate;
}
