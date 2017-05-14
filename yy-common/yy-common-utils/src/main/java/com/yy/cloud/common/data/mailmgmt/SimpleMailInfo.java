/**
 * Project Name:liz-common-utils
 * File Name:SimpleMailInfo.java
 * Package Name:com.gemii.lizcloud.common.dto.mailmgmt
 * Date:Apr 24, 201610:10:19 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.mailmgmt;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:SimpleMailInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Apr 24, 2016 10:10:19 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class SimpleMailInfo implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 3430090687459143331L;

	private String[] to;
	private String[] cc;
	private String[] bCc;
	private String from;
	private String subject;
	private String body;
}

