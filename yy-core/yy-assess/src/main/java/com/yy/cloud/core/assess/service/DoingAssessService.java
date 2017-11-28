package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;

import java.util.List;

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
    GeneralResult submitSingleAnswerAssessAnswer(String _userId, AssessAnswerReq _answer);
    GeneralContentResult<List<String>> addAssessSubAnswer(String _userId, AssessAnswerReq _answer);
    GeneralResult deleteAssessSubAnswer(List<String> _answerItemId);
    GeneralContentResult<List<String>> addMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer);
    GeneralResult deleteMultiAnswerAssessAnswer(List<String> _answerId);
    GeneralResult submitMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer);
}
