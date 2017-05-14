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

	public final static String ORDERMGMT_UNEXPECTED_EXCEPTION = "10003001";

	public final static String SUBINSTANCEMGMT_UNEXPECTED_EXCEPTION = "10004001";

	public final static String WORKFLOW_UNEXPECTED_EXCEPTION = "10005001";
	
	public final static String ADUSERMGMT_UNEXPECTED_EXCEPTION = "10051001";

	public final static String ADUSERMGMT_LDAP_CONNECT_EXCEPTION = "10051002";

	public final static String ADUSERMGMT_ADUSER_DUPLICATED_EXCEPTION = "10051101";

	public final static String ADUSERMGMT_LOGIN_NOT_EXIST_EXCEPTION = "10051201";

	public final static String ADUSERMGMT_LOGIN_MULTI_LDAPS_EXCEPTION = "10051202";

	public final static String ADUSERMGMT_LOGIN_LDAP_REJECT_EXCEPTION = "10051203";

	public final static String ADUSERMGMT_LOGIN_INVALID_EXCEPTION = "10051204";

    public final static String ENTERPRISE_QUOTA_VALIDATE_FAILED = "10007001";

	public final static String ORGANIZATION_QUOTA_VALIDATE_FAILED = "10007002";

	public final static String USERMGMT_ORGAN_CREATE_FAILED = "10008001";
}
