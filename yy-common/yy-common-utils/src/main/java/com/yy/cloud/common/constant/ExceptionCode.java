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
    }
}
