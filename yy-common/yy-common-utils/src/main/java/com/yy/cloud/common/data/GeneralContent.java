/**
 * Project Name:liz-common-utils
 * File Name:GeneralContent.java
 * Package Name:com.gemii.lizcloud.common.data
 * Date:Oct 1, 20166:19:29 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:GeneralContent <br/>
 * Function: Please use this object to replace the NULL or empty Object Entity
 * when you do not know how to choose the content entity in General Result,
 * Or when you do not need to give any content info in the response, please use it.
 * <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Oct 1, 2016 6:19:29 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class GeneralContent implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 5836675483521135229L;

	private String processResult = "SUCCESS";
}
