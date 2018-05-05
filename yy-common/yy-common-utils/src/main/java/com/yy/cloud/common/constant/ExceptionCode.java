package com.yy.cloud.common.constant;

import java.util.HashMap;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/11/17 7:54 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public final class ExceptionCode {
    public final static HashMap<String, String> EXCEPTION_MSG = new HashMap<>();
    static {
        EXCEPTION_MSG.put(ResultCode.ASSESS_ANSWER_SUBMIT_ALREADY, "该考卷已提交，无法进行更改！");
        EXCEPTION_MSG.put(ResultCode.ASSESS_ANSWER_SUBMIT_NOTEXISTS, "当前考核人员没有不在该卷子的考核列表中，请重新答题！");
        EXCEPTION_MSG.put(ResultCode.ASSESS_ANSWER_NOTEXISTS, "新的答案为空，还请重新提交！");
        EXCEPTION_MSG.put(ResultCode.ORG_USER_RETRIEVE_FAILED, "查询部门员工失败！");
        EXCEPTION_MSG.put(ResultCode.ACCESS_LIMITED, "权限不足，请申请开通该权限！");
        EXCEPTION_MSG.put(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION, "获取考生信息异常！");
        EXCEPTION_MSG.put(ResultCode.SCORING_RATIO_EXCEED_MIN, "评分计分比率不能小于等于0！");
        EXCEPTION_MSG.put(ResultCode.SCORING_RATIO_EXCEED_MAX, "评分计分比率不能大于100%！");
        EXCEPTION_MSG.put(ResultCode.SCORING_THRESHOLD_EXCEED_MIN, "评分计分限额不能小于0！");
        EXCEPTION_MSG.put(ResultCode.ASSESSPAPER_CREATE_FAILED, "创建试卷失败，信息校验失败或同一题被分入不同组！");
        EXCEPTION_MSG.put(ResultCode.ASSESSPAPER_UPDATE_FAILED, "更新试卷失败，信息校验失败或同一题被分入不同组！");
        EXCEPTION_MSG.put(ResultCode.ASSESS_ANSWER_GET_FAILED, "获取考生答案详情失败，该考生可能还未提交答案或者还未完成评分！");
    }
}
