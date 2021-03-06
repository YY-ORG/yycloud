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

    public final static Byte DIC_SCORING_TYPE_NORMAL = 0;
    public final static Byte DIC_SCORING_TYPE_NORMAL_SUM = 1;
    public final static Byte DIC_SCORING_TYPE_NORMAL_SUM_THREHOLD = 2;
    public final static Byte DIC_SCORING_TYPE_RATIO = 3;
    public final static Byte DIC_SCORING_TYPE_RATIO_SUM = 4;
    public final static Byte DIC_SCORING_TYPE_RATIO_SUM_THREHOLD = 5;


    public final static Byte DIC_SCORING_LEVEL_EXCELLENT = 0;
    public final static Byte DIC_SCORING_LEVEL_GOOD = 1;
    public final static Byte DIC_SCORING_LEVEL_ACCEPTABLE = 2;
    public final static Byte DIC_SCORING_LEVEL_FAILED = 3;

    public final static Byte DIC_TEMPLATE_ITEM_TYPE_TEXT = 17;

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

    public final static String ORG_ALL = "all";

    public final static Integer FILE_EXCEL_GENERAL_HEADER_ROWS = 1;
    public final static Integer FILE_EXCEL_GENERAL_TITILE_ROWS = 1;

}
