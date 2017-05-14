/**
 * Project Name:liz-common-utils
 * File Name:GeneralContentRsult.java
 * Package Name:com.gemii.lizcloud.common.data
 * Date:Oct 17, 20161:09:15 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:GeneralContentRsult <br/>
 * Function: 带普通结果数据的返回结果. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Oct 17, 2016 1:09:15 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class GeneralContentResult<T> implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -8104955278209569617L;

	private String resultCode;
	private String detailDescription;
	private T resultContent;
}

