/**
 * Project Name:yy-common-utils
 * File Name:AssessPaperItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:48:55 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * ClassName:AssessPaperItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:48:55 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class AssessPaperItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 7927627781438055513L;
	private String id;
	private String code;
	private Integer annual;
	private Timestamp createDate;
	private String creatorId;
	private String name;
//	private List<String> orgIdList;
	private Byte status;

	public String getName() {
		if(this.name.contains("年度")) {
			return this.name;
		}
		return this.annual + "年度" + this.name;
	}
}
