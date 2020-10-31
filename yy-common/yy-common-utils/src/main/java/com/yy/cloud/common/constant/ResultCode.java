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
	public final static String ORG_USER_RETRIEVE_FAILED = "10002001";

	public final static String PLATFORMACCESS_UNEXPECTED_EXCEPTION = "10006001";

    public final static String PLATFORMACCESS_RPC_FAILED = "10006002";

    public final static String PLATFORMACCESS_SYNC_FAILED = "10006003";
    
    public final static String PLATFORMACCESS_QUOTA_VALIDATE_FAILED = "10006004";

	public final static String USERMGMT_UNEXPECTED_EXCEPTION = "10001001";

	public final static String USERMGMT_USER_NOT_FOUND = "10001002";
	
	public final static String USERMGMT_ORG_EXISTED = "10001003";

	public final static String ASSESS_ANSWER_SUBMIT_ALREADY = "10001";
	public final static String ASSESS_ANSWER_SUBMIT_NOTEXISTS = "10002";

	public final static String ASSESS_ANSWER_NOTEXISTS = "10003";
	public final static String ASSESS_ANSWER_GET_FAILED = "10003001";
	
	public final static String ASSESS_GET_FAILED = "10004";
	public final static String ASSESSPAPER_CREATE_FAILED = "10005";
	public final static String ASSESSPAPER_UPDATE_FAILED = "10006";

	public final static String ASSESSPAPER_PERIOD_GET_FAILED = "10007";
	public final static String ASSESSPAPER_PERIOD_UPDATE_FAILED = "10008";

	public final static String ASSESSPAPER_COPY_FAILED_SOURCE_ERROR = "10009";
	public final static String ASSESSPAPER_COPY_FAILED_ANNUAL_EXISTS = "10010";

	public final static String ASSESS_ANSWER_EX_NOT_STARTED = "10011";
	public final static String ASSESS_ANSWER_EX_ENDED_ALREADY = "10012";
	public final static String ASSESS_ANSWER_SC_NOT_STARTED = "10013";
	public final static String ASSESS_ANSWER_SC_ENDED_ALREADY = "10014";
	public final static String ASSESS_ANSWER_AU_NOT_STARTED = "10015";
	public final static String ASSESS_ANSWER_AU_ENDED_ALREADY = "10016";

	public final static String ASSESS_ANSWER_EXAMINEE_MAP_NOT_EXISTS = "10017";
	public final static String ASSESS_ANSWER_EXAMINEE_MAP_ORG_INVALID = "10018";
	public final static String ASSESS_ANSWER_EXAMINEE_MAP_STATUS_INVALID = "10019";
	public final static String ASSESS_ANSWER_EXAMINEE_MAP_STATUS_EXPIRED = "10020";


	public final static String ACCESS_LIMITED = "999999";

	public final static String SCORING_RATIO_EXCEED_MIN = "10004001";
	public final static String SCORING_RATIO_EXCEED_MAX = "10004002";
	public final static String SCORING_THRESHOLD_EXCEED_MIN = "10004003";


	public final static String FILE_EXCEL_NOT_EXISTS = "10005001";
    public final static String FILE_EXCEL_IS_EMPTY = "10005002";
    public final static String FILE_EXCEL_UNKNOWN_ERROR = "10005003";


	public final static String FILE_EXCEED_SIZE_MAX = "10005010";
	public final static String FILE_UPLOAD_ERROR = "10005011";
}
