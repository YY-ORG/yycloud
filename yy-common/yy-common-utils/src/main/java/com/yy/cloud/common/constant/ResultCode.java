/**
 * Project Name:liz-common-utils
 * File Name:ResultCode.java
 * Package Name:com.gemii.lizcloud.common.constant
 * Date:Jul 18, 20165:58:14 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.constant;

/**
 * ClassName:ResultCode <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Jul 18, 2016 5:58:14 PM <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
public class ResultCode {
	/**
	 * OPERATION_SUCCESS: for all of the successful operations.
	 */
	public final static String OPERATION_SUCCESS = "100";
	/**
	 * 11002001 = "11" || "002" || "001"
	 *
	 * "11" FOR COMPOSITE SERVICES "10" FOR CORE SERVICES "12" FOR BASEPLATFORM
	 * SERVICES "13" FOR COMMON PACKAGES
	 *
	 * "002" FOR SERVICES'S SEQ IN THEIR CATEGORY
	 *
	 * "001" FOR THE ERROR CODE FOR EACH EXCEPTION
	 *
	 */
	public final static String USERMGMT_RETRIEVE_FAILED = "10002001";

	public final static String PLATFORMACCESS_UNEXPECTED_EXCEPTION = "10006001";

    public final static String PLATFORMACCESS_RPC_FAILED = "10006002";

    public final static String PLATFORMACCESS_SYNC_FAILED = "10006003";
    
    public final static String PLATFORMACCESS_QUOTA_VALIDATE_FAILED = "10006004";

	public final static String USERMGMT_UNEXPECTED_EXCEPTION = "10001001";

	public final static String USERMGMT_USER_NOT_FOUND = "10001002";
	
	public final static String USERMGMT_ORG_EXISTED = "10001003";

	public final static String ASSESS_ANSWER_SUBMIT_ALREADY = "10001";
	public final static String ASSESS_ANSWER_SUBMIT_NOTEXISTS = "10002";
	
	public final static String ASSESS_GET_FAILED = "";
}
