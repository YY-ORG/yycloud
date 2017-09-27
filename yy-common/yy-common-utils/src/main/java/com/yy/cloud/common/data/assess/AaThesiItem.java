/**
 * Project Name:yy-common-utils
 * File Name:AaThesiItem.java
 * Package Name:com.yy.cloud.common.data.assess
 * Date:Sep 27, 20178:57:29 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.assess;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * ClassName:AaThesiItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:57:29 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class AaThesiItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 4853011600321077949L;
	private String id;
	private Timestamp createDate;
	private String creatorId;
	private String issue;
	private int issueYear;
	private String journalId;
	private String name;
	private String reprintJournalId;
	private byte reprintType;
	private Timestamp updateDate;
}
