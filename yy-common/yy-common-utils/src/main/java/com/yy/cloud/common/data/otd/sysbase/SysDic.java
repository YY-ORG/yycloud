/**
 * Project Name:liz-common-utils
 * File Name:SystemDictionary.java
 * Package Name:com.hpe.foxcloud.common.data.system
 * Date:Sep 28, 20163:59:55 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.data.otd.sysbase;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:SystemDictionary <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 28, 2016 3:59:55 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@Data
public class SysDic implements Serializable {

	/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -4353876424766935472L;

	private String owner;
	private String field;
	/**
	 * code: Map to LIZ_SYS_DICTIONARY.value
	 */
	private String code;
	/**
	 * value: Map to LIZ_SYS_DICTIONARY.code
	 */
	private String value;
	/**
	 * displayValue: Map to LIZ_SYS_DICTIONARY.text
	 */
	private String displayValue;
}
