/**
 * Project Name:liz-common-utils
 * File Name:CommonConstant.java
 * Package Name:com.gemii.lizcloud.common.constant
 * Date:Sep 30, 20162:56:31 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.common.constant;

/**
 * ClassName:CommonConstant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 30, 2016 2:56:31 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public class CommonConstant {
    public final static Byte DIC_REGION_ADMIN = 0;
    public final static Byte DIC_REGION_VIRTAUL = 1;
    public final static Byte DIC_GLOBAL_STATUS_INITIAL = 0;
    public final static Byte DIC_GLOBAL_STATUS_ENABLE = 1;
    public final static Byte DIC_GLOBAL_STATUS_SUSPEND = 2;
    public final static Byte DIC_GLOBAL_STATUS_CANCELLED = 3;
    public final static Byte DIC_GLOBAL_STATUS_DELETED = 4;
    public final static Byte DIC_GLOBAL_STATUS_DISABLE = 5;

    public final static Byte DIC_ASSESSANSWER_TYPE_SINGLEANSWER = 0;
    public final static Byte DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER = 1;
    public final static Byte DIC_ASSESSANSWER_TYPE_MULTIANSWERS = 2;
    public final static Byte DIC_ASSESSANSWERITEM_TYPE_PRIMARY = 0;
    public final static Byte DIC_ASSESSANSWERITEM_TYPE_SUB = 1;

    public final static Byte DIC_ASSESS_TYPE_SINGLE_ANSWER= 0;
    public final static Byte DIC_ASSESS_TYPE_MULTI_ANSWERS = 1;
    public final static Byte DIC_ASSESS_TYPE_TABLE_SINGLE_ANSWER = 2;
    public final static Byte DIC_ASSESS_TYPE_TABLE_MULTI_ANSWERS = 3;

    //ASSESS ANSWER STATUS
    public final static Byte DIC_ASSESS_ANSWER_STATUS_NOT_STARTED=0;
    public final static Byte DIC_ASSESS_ANSWER_STATUS_DOING=1;
    public final static Byte DIC_ASSESS_ANSWER_STATUS_DONE=2;
    public final static Byte DIC_ASSESS_ANSWER_STATUS_MARKED=3;
    public final static Byte DIC_ASSESS_ANSWER_STATUS_AUDITED=4;

    public final static Byte DIC_ASSESSPAPER_STATUS_UNSUBMIT=0;
    public final static Byte DIC_ASSESSPAPER_STATUS_SUBMITTED=1;
    public final static Byte DIC_ASSESSPAPER_STATUS_MARKED=2;
    public final static Byte DIC_ASSESSPAPER_STATUS_AUDITED=3;

    public final static Byte DIC_USER_INFO_TITLE_ZG=0;
    public final static Byte DIC_USER_INFO_TITLE_FG=1;
    public final static Byte DIC_USER_INFO_TITLE_ZJ=2;
    public final static Byte DIC_USER_INFO_TITLE_CJ=3;
    public final static Byte DIC_USER_INFO_TITLE_W=4;

    // TENANT TYPE: 0: buyer(前台企业), 1: provider(后台企业)
    public final static Byte DIC_TENANT_TYPE_BUYER=0;
    public final static Byte DIC_TENANT_TYPE_PROVIDER=1;
    /**
     * 鉴权类型
     */
    public final static Byte DIC_AUTHENTICATION_MODE_NORMAL = 0;
    public final static Byte DIC_AUTHENTICATION_MODE_LDAP = 1;

    /**
     * 前台部门
     */
    public final static Byte DIC_ORGANIZATION_TYPE_DEPARTMENT = 0;
    /**
     * 后台机构
     */
    public final static Byte DIC_ORGANIZATION_TYPE_ORGANIZATION = 1;
    
    public final static String LOG_DEBUG_TAG = "################### ";
    public final static String LOG_ERROR_TAG = "%%%%%%%%%%%%%%%%%%% ";

	//审批中心--拒绝/同意
    public final static byte ORDER_AUDIT_REFUSAL = 0;
    public final static byte ORDER_AUDIT_CONSENT = 1;

	//IMAGETYPE
	public static final byte IMAGETYPE_PUBLIC = 0; //标准镜像
	public static final byte IMAGETYPE_PRIVATE = 1; //私有镜像
	public static final byte IMAGETYPE_ENT = 2; //企业镜像
	
    public final static Byte UI_RELY_TYPE_NO = 0;
    public final static Byte UI_RELY_TYPE_HAVE = 1;

    public static final String DATE_FOTMAT_STYLE = "yyyyMMddHHmmssSSS";

    //used_quota action type
    public final static byte RES_QUOTA_ACTION_TYPE_DECREASE = 0;
    public final static byte RES_QUOTA_ACTION_TYPE_INCREASE = 1;

    public final static String DEFAULT_ADMIN_DEPARTMENT_OF_ENTERPRISE_CODE="ADMIN_DEPARTMENT";
    public final static String DEFAULT_NORMAL_DEPARTMENT_OF_ENTERPRISE_CODE="NORMAL_DEPARTMENT";
    public final static String DEFAULT_ADMIN_DEPARTMENT_OF_ENTERPRISE_NAME="管理员部门";
    public final static String DEFAULT_NORMAL_DEPARTMENT_OF_ENTERPRISE_NAME="普通用户部门";
    public final static String DEFAULT_ADMIN_ORGANIZATION_CODE="ADMIN_ORGANIZATION";
    public final static String DEFAULT_ADMIN_ORGANIZATION_NAME="云平台管理部门";
    public final static byte DEPARTMENT_TYPE=0;
    public final static byte ORGANIZATION_TYPE=1;
    public final static String ROLE_ENTERPRISE_ADMIN="ENTERPRISE_ADMIN";

    // 前台MPP用户
    public final static Byte USER_TYPE_BUYER=0;
    // 后台ADM用户
    public final static Byte USER_TYPE_PROVIDER=1;
    // 登录名不存在
    public final static Byte USER_NOT_FOUND=2;
}
