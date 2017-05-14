/**
 * Project Name:liz-common-utils
 * File Name:NoRecordFoundException.java
 * Package Name:com.yy.cloud.common.exception
 * Date:Jul 12, 20165:25:02 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.exception;

/**
 * ClassName:NoRecordFoundException <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Jul 12, 2016 5:25:02 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
public class NoRecordFoundException extends RuntimeException {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -7563408365868374668L;

	public NoRecordFoundException() {
		super();
	}

	public NoRecordFoundException(String message) {
		super(message);
	}

	public NoRecordFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRecordFoundException(Throwable cause) {
		super(cause);
	}
}
