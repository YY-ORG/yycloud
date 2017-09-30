/**
 * Project Name:yy-common-utils
 * File Name:TemplateTiMapItem.java
 * Package Name:com.yy.cloud.common.data.metadata
 * Date:Sep 27, 20178:23:27 PM
 * Copyright (c) 2017, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.metadata;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:TemplateTiMapItem <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 27, 2017 8:23:27 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class TemplateTiMapItem implements Serializable {
	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = 1491385307828511635L;
	
	private String id;
	private String reliedId;
	private Byte status;
	private String templateId;
	private String templateItemId;
}
