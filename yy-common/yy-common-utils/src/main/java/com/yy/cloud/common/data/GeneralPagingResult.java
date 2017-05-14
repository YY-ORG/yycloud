/**
 * Project Name:liz-common-utils
 * File Name:GeneralResult.java
 * Package Name:com.yy.cloud.common.dto
 * Date:Jul 11, 20164:25:52 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:GeneralPagingResult <br/>
 * Function: 带分页结果数据的返回结果. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Jul 11, 2016 4:25:52 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class GeneralPagingResult<T> implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 1540315626080625718L;

	private String resultCode;
	private String detailDescription;
	private T resultContent;
	
	/**
	 * pageInfo: For paging result ONLY.
	 */
	private PageInfo pageInfo;
}

