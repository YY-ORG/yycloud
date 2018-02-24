package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.assess.ApAcScoringReq;
import com.yy.cloud.common.data.dto.assess.ApAssessScoringReq;
import com.yy.cloud.common.data.dto.assess.AssessAnswerScoringReq;
import com.yy.cloud.common.data.otd.assess.*;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.*;
import com.yy.cloud.core.assess.service.MarkedScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     1/3/18 3:42 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class MarkedScoreServiceImpl implements MarkedScoreService {
    @Autowired
    private PerAssessPaperExamineeMapRepository perAssessPaperExamineeMapRepository;
    @Autowired
    private PerAssessPaperRepository perAssessPaperRepository;
    @Autowired
    private SecurityClient securityClient;
    @Autowired
    private PerAssessAnswerRepository perAssessAnswerRepository;
    @Autowired
    private PerAssessAspMapRepository perAssessAspMapRepository;
    @Autowired
    private PerAssessAnswerItemRepository perAssessAnswerItemRepository;
    @Autowired
    private PerApacExamineeMapRepository perApacExamineeMapRepository;
    @Autowired
    private PerApAcMapRepository perApAcMapRepository;

    private static Map<String, PerAssessPaper> assessPaperMap;
    @Override
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnMarkedAssessPaperListByOrg(String _orgId, Pageable _page) throws YYException {
        List<Byte> statusList = new ArrayList<>();
        statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);
        statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED);
        return this.getAssessPaperListByOrg(_orgId, statusList, _page);
    }

    @Override
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnAuditedAssessPaperListByOrg(String _orgId, Pageable _page) throws YYException {
        List<Byte> statusList = new ArrayList<>();
        statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED);
        statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_AUDITED);
        return this.getAssessPaperListByOrg(_orgId, statusList, _page);
    }

    /**
     * Load Assess Paper List By Org
     *
     * @param _orgId
     * @param _statusList
     * @return
     */
    private GeneralPagingResult<List<AssessPaperExamineeMapItem>> getAssessPaperListByOrg(String _orgId, List<Byte> _statusList, Pageable _page) throws YYException {
        Page<PerAssessPaperExamineeMap> tempPAPEPage = this.perAssessPaperExamineeMapRepository.findByDeptIdAndStatusIn(_orgId, _statusList, _page);
        List<PerAssessPaperExamineeMap> tempPAPEList = tempPAPEPage.getContent();

        GeneralPagingResult<List<AssessPaperExamineeMapItem>> tempResult = new GeneralPagingResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(tempPAPEList == null || tempPAPEList.size() == 0){
            log.info("There  is nobody having submitted the assess paper...");
            return tempResult;
        }
        GeneralContentResult<Map<String, UserDetailsItem>> tempUserMapResult = this.securityClient.getAllMembersInOrganization(_orgId);

        if(!tempUserMapResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)){
            log.error("Retrieve Org Mem List failed: {}", tempUserMapResult);
            throw new YYException(ResultCode.ORG_USER_RETRIEVE_FAILED);
        }
        Map<String, UserDetailsItem> tempUserMap = tempUserMapResult.getResultContent();
        if(assessPaperMap == null){
            assessPaperMap = new HashMap<>();
        }
        List<AssessPaperExamineeMapItem> tempResultList = new ArrayList<>();
        for(PerAssessPaperExamineeMap tempItem : tempPAPEList) {
            AssessPaperExamineeMapItem tempAPEMItem = new AssessPaperExamineeMapItem();
            tempAPEMItem.setAssessPaperId(tempItem.getAssessPaperId());
            PerAssessPaper tempAssessPaper = assessPaperMap.get(tempItem.getAssessPaperId());
            if(tempAssessPaper == null) {
                tempAssessPaper = this.perAssessPaperRepository.findOne(tempItem.getAssessPaperId());
                assessPaperMap.put(tempItem.getAssessPaperId(), tempAssessPaper);
            }
            tempAPEMItem.setAssessPaperName(tempAssessPaper.getName());
            tempAPEMItem.setUserId(tempItem.getCreatorId());

            tempAPEMItem.setOrgId(_orgId);

            UserDetailsItem tempUserItem = tempUserMap.get(tempItem.getCreatorId());
            if(tempUserItem == null) {
                tempAPEMItem.setUserName("未知");
                tempAPEMItem.setOrgName("未知");
                tempAPEMItem.setTitle(CommonConstant.DIC_USER_INFO_TITLE_W);
            } else {
                tempAPEMItem.setUserName(tempUserItem.getUserName());
                tempAPEMItem.setOrgName(tempUserItem.getDeptName());
                tempAPEMItem.setTitle(tempUserItem.getProfessionalTitle());
            }
            tempAPEMItem.setStatus(tempItem.getStatus());
            tempAPEMItem.setAuditScore(tempItem.getAuditScore());
            tempAPEMItem.setMarkedScore(tempItem.getMarkedScore());
            tempResultList.add(tempAPEMItem);
        }
        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(tempPAPEPage.getNumber());
        _pageInfo.setPageSize(tempPAPEPage.getSize());
        _pageInfo.setTotalPage(tempPAPEPage.getTotalPages());
        _pageInfo.setTotalRecords(tempPAPEPage.getTotalElements());
        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempResultList);
        return tempResult;
    }

    @Override
    public GeneralContentResult<MarkedAssessAnswer> getUnMarkedAssessAnswer(String _userId, String _assessPaperId, String _assessId) throws YYException {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(_assessPaperId, _assessId, _userId);
        GeneralContentResult<MarkedAssessAnswer> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        if(tempAnswer != null) {
            MarkedAssessAnswer tempMarkAnswer = new MarkedAssessAnswer();
            List<MarkedAssessAnswerItem> tempItemList = null;
            if(tempAnswer.getType() == CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER) {
                tempItemList = tempAnswer.getPerAssessAnswerItems().stream().filter(item -> item.getType() ==
                CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY).map(this::convertToMAAI).collect(Collectors.toList());
            } else {
                tempItemList = tempAnswer.getPerAssessAnswerItems().stream().map(this::convertToMAAI).collect(Collectors.toList());
            }
            tempMarkAnswer.setId(tempAnswer.getId());
            tempMarkAnswer.setAuditComment(tempAnswer.getAuditComment());
            tempMarkAnswer.setAuditScore(tempAnswer.getAuditScore());
            tempMarkAnswer.setAuxiliaryScore(tempAnswer.getAuxiliaryScore());
            tempMarkAnswer.setMarkedComment(tempAnswer.getMarkedComment());
            tempMarkAnswer.setMarkedScore(tempAnswer.getMarkedScore());
            tempMarkAnswer.setRAuditScore(tempAnswer.getRAuditScore());
            tempMarkAnswer.setRMarkedScore(tempAnswer.getRMarkedScore());
            tempMarkAnswer.setItemList(tempItemList);
            //need to add

            PerAssessAspMap tempPAAM = this.perAssessAspMapRepository.findByAssessPaperIdAndAssessIdAndStatus(_assessPaperId, _assessId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
            if(tempPAAM != null) {
                tempMarkAnswer.setScoringType(tempPAAM.getScoringType());
                tempMarkAnswer.setScoringRatio(tempPAAM.getScoringRatio());
                tempMarkAnswer.setScoringThreshold(tempPAAM.getScoringThreshold());
            }
            tempResult.setResultContent(tempMarkAnswer);
        }

        return tempResult;
    }

    /**
     * 转换答案元素
     *
     * @param _item
     * @return
     */
    private MarkedAssessAnswerItem convertToMAAI(PerAssessAnswerItem _item){
        MarkedAssessAnswerItem tempItem = new MarkedAssessAnswerItem();
        tempItem.setId(_item.getId());
        tempItem.setTemplateId(_item.getId());
        tempItem.setSeqNo(_item.getSeqNo());
        tempItem.setType(_item.getType());
        tempItem.setDetailList(
                _item.getPerAssessAnswerDetails().stream().map(
                        this::convertToASSDIOTD
                ).collect(Collectors.toList())
        );
        tempItem.setAuxiliaryScore(_item.getAuxiliaryScore());
        tempItem.setAuditScore(_item.getAuditScore());
        tempItem.setAuditComment(_item.getAuditComment());
        tempItem.setMarkedScore(_item.getMarkedScore());
        tempItem.setMarkedComment(_item.getMarkedComment());
        tempItem.setRAuditScore(_item.getRAuditScore());
        tempItem.setRMarkedScore(_item.getRMarkedScore());
        return tempItem;
    }

    /**
     * 转换答案的详情
     *
     * @param _detail
     * @return
     */
    private SimpleAssessAnswerDetailItem convertToASSDIOTD(PerAssessAnswerDetail _detail){
        SimpleAssessAnswerDetailItem tempItem = new SimpleAssessAnswerDetailItem();
        tempItem.setId(_detail.getId());
        tempItem.setItemCode(_detail.getItemCode());
        tempItem.setItemValue(_detail.getItemValue());
        return tempItem;
    }

    @Override
    public GeneralContentResult<MarkedAssessAnswer> markScoreAssessAnswer(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req) throws YYException {
        return this.calculateAnswerScore(_userId, _assessPaperId, _assessId, _req, Boolean.TRUE);
    }

    @Override
    public GeneralContentResult<MarkedAssessAnswer> auditScoreAssessAnswer(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req) throws YYException {
        return this.calculateAnswerScore(_userId, _assessPaperId, _assessId, _req, Boolean.FALSE);
    }

    private GeneralContentResult<MarkedAssessAnswer> calculateAnswerScore(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req, Boolean _maFlag){
        PerAssessAspMap tempPerAssessAspMap = this.perAssessAspMapRepository.findByAssessPaperIdAndAssessIdAndStatus(_assessPaperId, _assessId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        PerAPAAScore tempScore = this.perAssessAnswerItemRepository.getAssessAnswerScore(_req.getAssessAnswerId(), _req.getAssessAnswerItemId());
        BigDecimal tempTotalMarkedScore = BigDecimal.ZERO;
        BigDecimal tempTotalAuditScore = BigDecimal.ZERO;
        BigDecimal tempItemMarkedScore = BigDecimal.ZERO;
        BigDecimal tempItemAuditScore = BigDecimal.ZERO;

        if(tempScore != null) {
            if(tempScore.getMarkedScore() != null)
                tempTotalMarkedScore = tempScore.getMarkedScore();
            if(tempScore.getAuditScore() != null)
                tempTotalAuditScore = tempScore.getAuditScore();
        }
        if(tempPerAssessAspMap.getItemThreshold().compareTo(BigDecimal.ZERO) == 0) {
            tempItemMarkedScore = _req.getScore();
            tempItemAuditScore = _req.getScore();
        } else {// 答案的总计分值不超过阈值（积分比率折算前的总分值），
            //    log.info("tempTotalMarkedScore={}, _req.score={}, tempPerAssessAspMap={}", tempTotalMarkedScore, _req, tempPerAssessAspMap);
            tempItemMarkedScore = _req.getScore().compareTo(tempPerAssessAspMap.getItemThreshold()) > 0 ? tempPerAssessAspMap.getItemThreshold() : _req.getScore();
            tempItemAuditScore = _req.getScore().compareTo(tempPerAssessAspMap.getItemThreshold()) > 0 ? tempPerAssessAspMap.getItemThreshold() : _req.getScore();
        }
        if(tempPerAssessAspMap.getScoringThreshold().compareTo(BigDecimal.ZERO) == 0) {
            tempTotalMarkedScore = tempTotalMarkedScore.add(tempItemMarkedScore);
            tempTotalAuditScore = tempTotalAuditScore.add(tempItemAuditScore);
        } else {// 答案的总计分值不超过阈值（积分比率折算前的总分值），
        //    log.info("tempTotalMarkedScore={}, _req.score={}, tempPerAssessAspMap={}", tempTotalMarkedScore, _req, tempPerAssessAspMap);
            tempTotalMarkedScore = (tempTotalMarkedScore.add(tempItemMarkedScore)).compareTo(tempPerAssessAspMap.getScoringThreshold()) > 0 ? tempPerAssessAspMap.getScoringThreshold() : tempTotalMarkedScore.add(tempItemMarkedScore);
            tempTotalAuditScore = (tempTotalAuditScore.add(tempItemAuditScore)).compareTo(tempPerAssessAspMap.getScoringThreshold()) > 0 ? tempPerAssessAspMap.getScoringThreshold() : tempTotalAuditScore.add(tempItemAuditScore);
        }

        PerAssessAnswerItem tempPerAssessAnswerItem = this.perAssessAnswerItemRepository.findOne(_req.getAssessAnswerItemId());
        if(_maFlag) { // first mark scoring.
            tempPerAssessAnswerItem.setMarkedScore(tempItemMarkedScore);
            tempPerAssessAnswerItem.setRMarkedScore(tempItemMarkedScore.multiply(tempPerAssessAspMap.getScoringRatio()));
            tempPerAssessAnswerItem.setMarkedComment(_req.getComments());
            tempPerAssessAnswerItem.getPerAssessAnswer().setMarkedScore(tempTotalMarkedScore);
            tempPerAssessAnswerItem.getPerAssessAnswer().setRMarkedScore(tempTotalMarkedScore.multiply(tempPerAssessAspMap.getScoringRatio()));
            tempPerAssessAnswerItem.getPerAssessAnswer().setMarkedComment(_req.getComments());
            tempPerAssessAnswerItem.getPerAssessAnswer().setMarker(_userId);
        } else {
            tempPerAssessAnswerItem.setAuditScore(tempItemAuditScore);
            tempPerAssessAnswerItem.setRAuditScore(tempItemAuditScore.multiply(tempPerAssessAspMap.getScoringRatio()));
            tempPerAssessAnswerItem.setAuditComment(_req.getComments());
            tempPerAssessAnswerItem.getPerAssessAnswer().setAuditScore(tempTotalAuditScore);
            tempPerAssessAnswerItem.getPerAssessAnswer().setRAuditScore(tempTotalAuditScore.multiply(tempPerAssessAspMap.getScoringRatio()));
            tempPerAssessAnswerItem.getPerAssessAnswer().setAuditComment(_req.getComments());
            tempPerAssessAnswerItem.getPerAssessAnswer().setAuditor(_userId);
        }
        this.perAssessAnswerItemRepository.save(tempPerAssessAnswerItem);
        GeneralContentResult<MarkedAssessAnswer> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findOne(_req.getAssessAnswerId());
        if(tempAnswer != null) {
            MarkedAssessAnswer tempMarkAnswer = new MarkedAssessAnswer();
            List<MarkedAssessAnswerItem> tempItemList = null;
            if(tempAnswer.getType() == CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER) {
                tempItemList = tempAnswer.getPerAssessAnswerItems().stream().filter(item -> item.getType() ==
                        CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY).map(this::convertToMAAI).collect(Collectors.toList());
            } else {
                tempItemList = tempAnswer.getPerAssessAnswerItems().stream().map(this::convertToMAAI).collect(Collectors.toList());
            }
            tempMarkAnswer.setId(tempAnswer.getId());
            tempMarkAnswer.setAuditComment(tempAnswer.getAuditComment());
            tempMarkAnswer.setAuditScore(tempAnswer.getAuditScore());
            tempMarkAnswer.setAuxiliaryScore(tempAnswer.getAuxiliaryScore());
            tempMarkAnswer.setMarkedComment(tempAnswer.getMarkedComment());
            tempMarkAnswer.setMarkedScore(tempAnswer.getMarkedScore());
            tempMarkAnswer.setRAuditScore(tempAnswer.getRAuditScore());
            tempMarkAnswer.setRMarkedScore(tempAnswer.getRMarkedScore());
            tempMarkAnswer.setItemList(tempItemList);
            //need to add

            tempMarkAnswer.setScoringType(tempPerAssessAspMap.getScoringType());
            tempMarkAnswer.setScoringRatio(tempPerAssessAspMap.getScoringRatio());
            tempMarkAnswer.setScoringThreshold(tempPerAssessAspMap.getScoringThreshold());
            tempResult.setResultContent(tempMarkAnswer);
        }

        return tempResult;
    }

    @Override
    public GeneralResult submitAssessPaperScoring(String _userId, String _assessPaperId, String _markerId) throws YYException {
        this.calculateSummaryScore(_userId, _assessPaperId, true, _markerId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralResult submitAssessPaperAuditScore(String _userId, String _assessPaperId, String _auditorId) throws YYException {
        this.calculateSummaryScore(_userId, _assessPaperId, false, _auditorId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    private void calculateSummaryScore(String _userId, String _assessPaperId, Boolean _maFlag, String _currentUserId) throws YYException{
        List<PerApacExamineeMap> tempApacExamineeMapList = this.perApacExamineeMapRepository.getApacExamineeMapItems(_assessPaperId, _userId);
        BigDecimal tempScore = BigDecimal.ZERO;

        PerAssessPaperExamineeMap tempPaperExamineeMap = this.perAssessPaperExamineeMapRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        if(tempPaperExamineeMap == null) {
            tempPaperExamineeMap = new PerAssessPaperExamineeMap();
            UserDetailsItem tempUserInfo = this.getUserDetailInfo(_userId);
            tempPaperExamineeMap.setTitle(tempUserInfo.getProfessionalTitle());
            tempPaperExamineeMap.setDeptId(tempUserInfo.getDeptId());
            tempPaperExamineeMap.setAssessPaperId(_assessPaperId);
        }
        if(tempApacExamineeMapList != null){
            for(PerApacExamineeMap tempItem : tempApacExamineeMapList) {
                PerApAcMap tempApAcMap = this.perApAcMapRepository.findOne(tempItem.getApAcMapId());
                BigDecimal tempRatio = BigDecimal.ONE;
                if(tempApAcMap != null && tempApAcMap.getScoringRatio() != null){
                    tempRatio = tempApAcMap.getScoringRatio();
                }

                BigDecimal tempThreshold = BigDecimal.ZERO;
                if(tempApAcMap != null && tempApAcMap.getScoringThreshold() != null){
                    tempThreshold = tempApAcMap.getScoringThreshold();
                }
                //计分比率折算后的阈值
                if(_maFlag) {
                    BigDecimal tempItemScore = tempItem.getMarkedScore();
                    if(tempItemScore == null)
                        tempItemScore = BigDecimal.ZERO;
                    tempItemScore = tempItemScore.multiply(tempRatio);
                    if(tempThreshold.compareTo(BigDecimal.ZERO) > 0){
                        tempItemScore = tempItemScore.compareTo(tempThreshold) >0? tempThreshold : tempItemScore;
                    }
                    tempScore = tempScore.add(tempItemScore);
                } else {
                    BigDecimal tempItemScore = tempItem.getAuditScore();
                    if(tempItemScore == null)
                        tempItemScore = BigDecimal.ZERO;
                    tempItemScore = tempItemScore.multiply(tempRatio);
                    if(tempThreshold.compareTo(BigDecimal.ZERO) > 0){
                        tempItemScore = tempItemScore.compareTo(tempThreshold) >0? tempThreshold : tempItemScore;
                    }
                    tempScore = tempScore.add(tempItemScore);
                }
                tempItem.setPerAssessPaperExamineeMap(tempPaperExamineeMap);
            }
            tempPaperExamineeMap.setPerApacExamineeMaps(tempApacExamineeMapList);
        }
        if(_maFlag) {
            tempPaperExamineeMap.setMarkedScore(tempScore);
            tempPaperExamineeMap.setMarker(_currentUserId);
            tempPaperExamineeMap.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_MARKED);
        } else {
            tempPaperExamineeMap.setAuditScore(tempScore);
            tempPaperExamineeMap.setAuditor(_currentUserId);
            tempPaperExamineeMap.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_AUDITED);
        }

        this.perAssessPaperExamineeMapRepository.save(tempPaperExamineeMap);
    }

    private UserDetailsItem getUserDetailInfo(String _userId) throws YYException {
        GeneralContentResult<UserDetailsItem> tempResult = this.securityClient.loadUserById(_userId);
//        log.info("The result is: {}", tempResult);
        if(tempResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)){
            log.info("the user is: {}", tempResult.getResultContent().getLoginName());
            return tempResult.getResultContent();
        }
        throw new YYException(ResultCode.USERMGMT_UNEXPECTED_EXCEPTION);
    }

    @Override
    public GeneralContentResult<List<ApAcScoringItem>> getScoringCategoryListForPaper(String _assessPaperId) throws YYException {
        List<ApAcScoringItem> tempApAcScoringItemList = this.perApAcMapRepository.getApAcMapListByAssessPaperId(_assessPaperId);
        GeneralContentResult<List<ApAcScoringItem>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempApAcScoringItemList);
        return tempResult;
    }

    @Override
    public GeneralPagingResult<List<ApAssessScoringItem>> getScoringAssessListForPaper(String _assessPaperId, String _categoryId, Pageable _page) throws YYException {
        Page<ApAssessScoringItem> tempScoringItemPage = this.perAssessAspMapRepository.getAssessScoringForAssessPaper(_assessPaperId, _categoryId, _page);
        GeneralPagingResult<List<ApAssessScoringItem>> tempResult = new GeneralPagingResult<>();
        if(tempScoringItemPage != null){
            tempResult.setResultContent(tempScoringItemPage.getContent());
            PageInfo tempPage = new PageInfo();
            tempPage.setCurrentPage(tempScoringItemPage.getNumber());
            tempPage.setPageSize(tempScoringItemPage.getSize());
            tempPage.setTotalPage(tempScoringItemPage.getTotalPages());
            tempPage.setTotalRecords(tempScoringItemPage.getTotalElements());
            tempResult.setPageInfo(tempPage);
        }
        return tempResult;
    }

    @Override
    public GeneralResult commitScoringForApAc(String _userId, List<ApAcScoringReq> _apAcList) throws YYException {
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_apAcList == null || _apAcList.size() == 0)
            return tempResult;
        List<PerApAcMap> tempApAcMapList = new ArrayList<>();
        for(ApAcScoringReq tempReq : _apAcList){
            PerApAcMap tempItem = this.perApAcMapRepository.findOne(tempReq.getApacId());
            tempItem.setScoringRatio(this.checkRatio(tempReq.getRatio()));
            tempItem.setScoringThreshold(this.checkThreshold(tempReq.getThreshold()));
            tempApAcMapList.add(tempItem);
        }
        this.perApAcMapRepository.save(tempApAcMapList);

        return tempResult;
    }

    @Override
    public GeneralResult commitScoringForApAssess(String _userId, List<ApAssessScoringReq> _apAssessList) throws YYException {
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        if(_apAssessList == null || _apAssessList.size() == 0)
            return tempResult;
        List<PerAssessAspMap> tempAssessAspMapList = new ArrayList<>();
        for(ApAssessScoringReq tempReq : _apAssessList){
            PerAssessAspMap tempItem = this.perAssessAspMapRepository.findOne(tempReq.getApAssessId());
            tempItem.setScoringRatio(this.checkRatio(tempReq.getRatio()));
            tempItem.setScoringThreshold(this.checkThreshold(tempReq.getThreshold()));
            tempItem.setItemThreshold(this.checkThreshold(tempReq.getItemThreshold()));
            tempAssessAspMapList.add(tempItem);
        }
        this.perAssessAspMapRepository.save(tempAssessAspMapList);
        return tempResult;
    }

    /**
     * Check Ratio 0<ratio<=1
     *
     * @param _ratio
     * @return
     * @throws YYException
     */
    private BigDecimal checkRatio(BigDecimal _ratio) throws YYException {
        if(_ratio == null)
            return BigDecimal.ONE;
        if(_ratio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new YYException(ResultCode.SCORING_RATIO_EXCEED_MIN);
        }
        if(_ratio.compareTo(BigDecimal.ONE) > 0) {
            throw new YYException(ResultCode.SCORING_RATIO_EXCEED_MAX);
        }
        return _ratio;
    }

    /**
     * Check the threshold 0<threshold
     *
     * @param _threshold
     * @return
     * @throws YYException
     */
    private BigDecimal checkThreshold(BigDecimal _threshold) throws YYException {
        if(_threshold == null)
            return BigDecimal.ZERO;
        if(_threshold.compareTo(BigDecimal.ZERO) <= 0)
            throw new YYException(ResultCode.SCORING_THRESHOLD_EXCEED_MIN);
        return _threshold;
    }
}
