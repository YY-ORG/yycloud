/**
 * Project Name:liz-common-utils
 * File Name:MailSendResult.java
 * Package Name:com.gemii.lizcloud.common.dto.mailmgmt
 * Date:Apr 24, 20169:31:17 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.mailmgmt;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:MailSendResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Apr 24, 2016 9:31:17 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class MailSendResult implements Serializable{/**
	 * serialVersionUID:TODO Description.
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 8573546674432368739L;
	
	/**
	 * resultCode:The process result of the sending mail request.
	 * @since JDK 1.8
	 */
	private int resultCode;
	/**
	 * resultCode:The detail description with the resultCode.
	 * @since JDK 1.8
	 */
	private String description;
}
