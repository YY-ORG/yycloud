package com.yy.cloud.core.assess.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.ApAcScoringReq;
import com.yy.cloud.common.data.dto.assess.ApAssessScoringReq;
import com.yy.cloud.common.data.dto.assess.AssessAnswerScoringReq;
import com.yy.cloud.common.data.otd.assess.ApAcScoringItem;
import com.yy.cloud.common.data.otd.assess.ApAssessScoringItem;
import com.yy.cloud.common.data.otd.assess.AssessPaperExamineeMapItem;
import com.yy.cloud.common.data.otd.assess.MarkedAssessAnswer;
import com.yy.cloud.common.utils.YYException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/28/17 8:38 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface MarkedScoreService {
    GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnMarkedAssessPaperListByOrg(String _orgId, Pageable _page) throws YYException;
    GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnAuditedAssessPaperListByOrg(String _orgId, Pageable _page) throws YYException;
    GeneralContentResult<MarkedAssessAnswer> getUnMarkedAssessAnswer(String _userId, String _assessPaperId, String _assessId) throws YYException;
    GeneralContentResult<MarkedAssessAnswer> markScoreAssessAnswer(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req) throws YYException;
    GeneralContentResult<MarkedAssessAnswer> auditScoreAssessAnswer(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req) throws YYException;
    GeneralResult submitAssessPaperScoring(String _userId, String _assessPaperId, String _markerId) throws YYException;
    GeneralResult submitAssessPaperAuditScore(String _userId, String _assessPaperId, String _auditorId, Byte _level) throws YYException;
    GeneralContentResult<List<ApAcScoringItem>> getScoringCategoryListForPaper(String _assessPaperId) throws YYException;
    GeneralPagingResult<List<ApAssessScoringItem>> getScoringAssessListForPaper(String _assessPaperId, String _categoryId, Pageable _page) throws YYException;
    GeneralResult commitScoringForApAc(String _userId, List<ApAcScoringReq> _apAcList) throws YYException;
    GeneralResult commitScoringForApAssess(String _userId, List<ApAssessScoringReq> _apAssessList) throws YYException;
}
