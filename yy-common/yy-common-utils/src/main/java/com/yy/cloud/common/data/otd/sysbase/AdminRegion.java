/**
 * Project Name:liz-common-utils
 * File Name:AdminRegion.java
 * Package Name:com.gemii.lizcloud.common.data.otd.sysbase
 * Date:Oct 24, 20163:44:00 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.otd.sysbase;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:AdminRegion <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Oct 24, 2016 3:44:00 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class AdminRegion implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 2186208991969328495L;

	private String id;
	private String code;
	private String name;
	private String parentId;
}

