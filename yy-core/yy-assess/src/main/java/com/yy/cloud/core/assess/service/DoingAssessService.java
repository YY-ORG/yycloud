package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 10:49 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface DoingAssessService {
    GeneralResult submitAssessAnswer(String _userId, AssessAnswerReq _answer);
}
