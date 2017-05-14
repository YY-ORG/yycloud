/**
 * Project Name:liz-common-utils
 * File Name:GeneralResult.java
 * Package Name:com.gemii.lizcloud.common.data
 * Date:Oct 17, 20161:32:04 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:GeneralResult <br/>
 * Function: 不带内容的返回结果. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Oct 17, 2016 1:32:04 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class GeneralResult implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 908203438033789670L;

	private String resultCode;
	private String detailDescription;
}

