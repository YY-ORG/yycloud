/**
 * Project Name:liz-common-utils
 * File Name:ResultCodeConstant.java
 * Package Name:com.yy.cloud.common
 * Date:Apr 24, 20169:42:12 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common;

import java.io.Serializable;

/**
 * ClassName:ResultCodeConstant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Apr 24, 2016 9:42:12 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ResultCodeConstant implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 5367287407540275094L;
	
	//the result code of mail mgmt starts with 10000
	public static final int MAILSENDER_SUCCESS = 10000;
	public static final int MAILSENDER_MAILSERVER_EXCEPTION = 10001;
	public static final int MAILSENDER_MAILRENDER_EXCEPTION = 10002;
	public static final int MAILSENDER_TEMPLATE_MISSING_EXCEPTION = 10003;
	public static final int MAILSENDER_UNKNOWN_EXCEPTION = 10004;

}

