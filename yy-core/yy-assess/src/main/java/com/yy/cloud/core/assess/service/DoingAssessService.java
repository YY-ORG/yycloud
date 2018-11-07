package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessAnswerItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessGroupAnswerItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperAnswerItem;
import com.yy.cloud.common.utils.YYException;

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
    GeneralResult submitSingleAnswerAssessAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException;
    GeneralContentResult<List<String>> addAssessSubAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException;
    GeneralContentResult<String> updateAssessSubAnswer(String _userId, String _subAnswerId, AssessAnswerReq _answer, String _commitorId) throws YYException;
    GeneralResult deleteAssessSubAnswer(String _userId, String _assessPaperId, List<String> _answerItemId, String _commitorId) throws YYException;
    GeneralContentResult<List<String>> addMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException;
    GeneralContentResult<String> updateMultiAnswerAssessAnswer(String _userId, String _answerItemId, AssessAnswerReq _answer, String _commitorId) throws YYException;
    GeneralResult deleteMultiAnswerAssessAnswer(String _userId, String _assessPaperId, List<String> _answerId, String _commitorId) throws YYException;
    GeneralResult submitMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException;
    GeneralContentResult<List<SimpleAssessAnswerItem>> getAssessAnswerItemList(String _userId, String _assessPaperId, String _assessId);
    GeneralContentResult<List<SimpleAssessGroupAnswerItem>> getAllAssessPaperAnswerSumListByUser(String _userId);
    GeneralContentResult<SimpleAssessPaperAnswerItem> getAssessPaperAnswerSumByUser(String _userId, String _assessPaperId);
    GeneralContentResult<List<SimpleAssessGroupAnswerItem>> getAllAssessPaperAnswerSumListByOrg(String _orgId);
    GeneralContentResult<List<SimpleAssessGroupAnswerItem>> getAssessPaperAnswerSumListByOrg(String _orgId, String _assessPaperId);
    GeneralResult submitAssessPaperAnswer(String _userId, String _orgId, Byte _title, String _assessPaperId) throws YYException;
}
