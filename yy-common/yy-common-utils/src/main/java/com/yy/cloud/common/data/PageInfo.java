/**
 * Project Name:liz-common-utils
 * File Name:PageInfo.java
 * Package Name:com.gemii.lizcloud.common.data
 * Date:Sep 29, 20162:22:06 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:PageInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 29, 2016 2:22:06 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class PageInfo implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -7644066325412964122L;

	/**
	 * Required. currentPage:the No. of current page.
	 */
	private Integer currentPage;
	/**
	 * Required. totalPage: Total count of the pages.
	 */
	private Integer totalPage;
	/**
	 * Required. pageSize: the size of the page.
	 */
	private Integer pageSize;
	/**
	 * Optional. totalRecords: total records of the entities.
	 */
	private Long totalRecords;
}
