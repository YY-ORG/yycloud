package com.yy.cloud.core.assess.service.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import com.yy.cloud.core.assess.clients.UserMgmtClient;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.*;
import com.yy.cloud.core.assess.service.MarkedScoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
    @Autowired
    private PerAssessPeriodRepository perAssessPeriodRepository;
    @Autowired
    private PerTemplateItemRepository perTemplateItemRepository;
    @Autowired
    private PerContentRepository perContentRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserMgmtClient userMgmtClient;

    private static Map<String, PerAssessPaper> assessPaperMap;

    @Override
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnMarkedAssessPaperListByOrg(
            String _examineeName, Integer _annual, Byte _status, String _orgId, Pageable _page) throws YYException {
        List<Byte> statusList = new ArrayList<>();
        if(_status == null || _status < 0) {
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_UNSUBMIT);// Add unsubmitted status for phase2.
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED);
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_AUDITED);// Add unsubmitted status for phase2.
        } else {
            statusList.add(_status);
        }
        return this.getAssessPaperListByOrg(_examineeName, _annual, _orgId, statusList, _page);
    }

    @Override
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnAuditedAssessPaperListByOrg(
            String _examineeName, Integer _annual, Byte _status, String _orgId, Pageable _page) throws YYException {
        List<Byte> statusList = new ArrayList<>();
        if(_status == null || _status < 0) {
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_UNSUBMIT);// Add unsubmitted status for phase2.
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);// Add unsubmitted status for phase2.
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED);
            statusList.add(CommonConstant.DIC_ASSESSPAPER_STATUS_AUDITED);
        } else {
            statusList.add(_status);
        }
        return this.getAssessPaperListByOrg(_examineeName, _annual, CommonConstant.ORG_ALL, statusList, _page);
    }

    /**
     * Load Assess Paper List By Org
     *
     * @param _orgId
     * @param _statusList
     * @return
     */
    private GeneralPagingResult<List<AssessPaperExamineeMapItem>> getAssessPaperListByOrg(
            String _examineeName, Integer _annual, String _orgId, List<Byte> _statusList, Pageable _page) throws YYException {
        log.info("The Query ExamineeName:{}, Annual:{}", _examineeName, _annual);
        //动态条件
        QPerAssessPaperExamineeMap tempPAPEMQuery = QPerAssessPaperExamineeMap.perAssessPaperExamineeMap;
        //该Predicate为querydsl下的类,支持嵌套组装复杂查询条件
        BooleanExpression predicate = tempPAPEMQuery.status.in(_statusList);
        if(!_orgId.equals(CommonConstant.ORG_ALL)) {
            predicate = predicate.and(tempPAPEMQuery.deptId.eq(_orgId));
        }
        if(StringUtils.isNotBlank(_examineeName)) {
            GeneralContentResult<List<String>> tempUserIdList = this.userMgmtClient.getUserByUserName(_examineeName);
            List<String> tempIdList = tempUserIdList.getResultContent();
            if(!CollectionUtils.isEmpty(tempIdList)) {
                log.info("Add ExamineeName QueyCOndition: {}", tempUserIdList.getResultContent().toString());
                predicate = predicate.and(tempPAPEMQuery.creatorId.in(tempUserIdList.getResultContent()));
            } else {
                log.info("Could not found user with name: {}, so use the original examinee name.", _examineeName);
                predicate = predicate.and(tempPAPEMQuery.creatorId.in(_examineeName));
            }
        }
        if(_annual != null && _annual > 2000) {
            log.info("Add Annual QueyCOndition: {}", _annual);
            predicate = predicate.and(tempPAPEMQuery.assessPaperId.in(
                    JPAExpressions.selectFrom(QPerAssessPaper.perAssessPaper).where(
                            QPerAssessPaper.perAssessPaper.status.eq(CommonConstant.DIC_GLOBAL_STATUS_ENABLE).and(
                                    QPerAssessPaper.perAssessPaper.annual.eq(_annual))).select(QPerAssessPaper.perAssessPaper.id)));
        }
        OrderSpecifier tempOrder = new OrderSpecifier(Order.ASC, QPerAssessPaperExamineeMap.perAssessPaperExamineeMap.createDate);
        JPAQueryFactory tempFactory = new JPAQueryFactory(this.entityManager);
        JPAQuery<PerAssessPaperExamineeMap> tempQuery = tempFactory.select(QPerAssessPaperExamineeMap.perAssessPaperExamineeMap).from(
                QPerAssessPaperExamineeMap.perAssessPaperExamineeMap);
        tempQuery.where(predicate).
        offset(_page.getOffset()).limit(_page.getPageSize())
        .orderBy(tempOrder);

        QueryResults<PerAssessPaperExamineeMap> tempQueryResult = tempQuery.fetchResults();

        List<PerAssessPaperExamineeMap> tempPAPEList = tempQueryResult.getResults();
        GeneralPagingResult<List<AssessPaperExamineeMapItem>> tempResult = new GeneralPagingResult<>();
        if (tempPAPEList == null || tempPAPEList.size() == 0) {
            log.info("There is nobody having submitted the assess paper...");
            PageInfo _pageInfo = new PageInfo();
            _pageInfo.setCurrentPage(_page.getPageNumber());
            _pageInfo.setPageSize(_page.getPageSize());
            _pageInfo.setTotalPage(0);
            _pageInfo.setTotalRecords(0L);
            tempResult.setPageInfo(_pageInfo);
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            return tempResult;
        }

        if (assessPaperMap == null) {
            assessPaperMap = new HashMap<>();
        }
        List<AssessPaperExamineeMapItem> tempResultList = new ArrayList<>();
        for (PerAssessPaperExamineeMap tempItem : tempPAPEList) {
            AssessPaperExamineeMapItem tempAPEMItem = new AssessPaperExamineeMapItem();
            tempAPEMItem.setId(tempItem.getId());
            tempAPEMItem.setAssessPaperId(tempItem.getAssessPaperId());
            PerAssessPaper tempAssessPaper = assessPaperMap.get(tempItem.getAssessPaperId());
            if (tempAssessPaper == null) {
                tempAssessPaper = this.perAssessPaperRepository.findOne(tempItem.getAssessPaperId());
                assessPaperMap.put(tempItem.getAssessPaperId(), tempAssessPaper);
            }
            if(tempAssessPaper.getName().contains("年度")) {
                tempAPEMItem.setAssessPaperName(tempAssessPaper.getName());
            } else {
                tempAPEMItem.setAssessPaperName(tempAssessPaper.getAnnual() + "年度" + tempAssessPaper.getName());
            }
            tempAPEMItem.setUserId(tempItem.getCreatorId());

            tempAPEMItem.setOrgId(_orgId);
            GeneralContentResult<UserDetailsItem> tempUserResult = this.securityClient.loadUserById(tempItem.getCreatorId());
            if (!tempUserResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)) {
                log.error("Retrieve User Info failed: {}", tempItem.getCreatorId());
                throw new YYException(ResultCode.ORG_USER_RETRIEVE_FAILED);
            }
            UserDetailsItem tempUserItem = tempUserResult.getResultContent();
            //UserDetailsItem tempUserItem = tempUserMap.get(tempItem.getCreatorId());
            if (tempUserItem == null) {
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
        _pageInfo.setCurrentPage(Long.valueOf(tempQueryResult.getOffset()).intValue());
        _pageInfo.setPageSize(Long.valueOf(tempQueryResult.getLimit()).intValue());
        _pageInfo.setTotalPage(Double.valueOf(Math.ceil(Double.valueOf(tempQueryResult.getTotal()) / Double.valueOf(tempQueryResult.getLimit()))).intValue());
        _pageInfo.setTotalRecords(Long.valueOf(tempQueryResult.getTotal()));
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

        if (tempAnswer != null) {
            MarkedAssessAnswer tempMarkAnswer = new MarkedAssessAnswer();
            List<MarkedAssessAnswerItem> tempItemList = null;
            if (tempAnswer.getType() == CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER) {
                tempItemList = tempAnswer.getPerAssessAnswerItems().stream().filter(item -> item.getType().equals(
                        CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY)).map(this::convertToMAAI).collect(Collectors.toList());
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
            if (tempPAAM != null) {
                tempMarkAnswer.setScoringType(tempPAAM.getScoringType());
                tempMarkAnswer.setScoringRatio(tempPAAM.getScoringRatio());
                tempMarkAnswer.setScoringThreshold(tempPAAM.getScoringThreshold());
            }
            tempResult.setResultContent(tempMarkAnswer);
        }

        return tempResult;
    }

    @Override
    public GeneralResult rollbackAssessPaperCommit(String _aspExamineeMapId, String _orgId) throws YYException {
        PerAssessPaperExamineeMap tempAspEM = this.perAssessPaperExamineeMapRepository.findOne(_aspExamineeMapId);
        if(tempAspEM == null) {
            throw new YYException(ResultCode.ASSESS_ANSWER_EXAMINEE_MAP_NOT_EXISTS);
        }
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        //For Auditor.
        if(_orgId.equals(CommonConstant.ORG_ALL)) {
            if(tempAspEM.getStatus().equals(CommonConstant.DIC_ASSESSPAPER_STATUS_AUDITED)) {
                throw new YYException(ResultCode.ASSESS_ANSWER_EXAMINEE_MAP_STATUS_EXPIRED);
            } else if(tempAspEM.getStatus().equals(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED)) {
                tempAspEM.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);
                this.perAssessPaperExamineeMapRepository.save(tempAspEM);
                return tempResult;
            }
        } else { // For Marker.
            if(!tempAspEM.getDeptId().equals(_orgId)) {
                throw new YYException(ResultCode.ASSESS_ANSWER_EXAMINEE_MAP_ORG_INVALID);
            }
            if(tempAspEM.getStatus().equals(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED)) {
                tempAspEM.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_UNSUBMIT);
                this.perAssessPaperExamineeMapRepository.save(tempAspEM);
                return tempResult;
            }
        }
        throw new YYException(ResultCode.ASSESS_ANSWER_EXAMINEE_MAP_STATUS_INVALID);
    }

    /**
     * 转换答案元素
     *
     * @param _item
     * @return
     */
    private MarkedAssessAnswerItem convertToMAAI(PerAssessAnswerItem _item) {
        MarkedAssessAnswerItem tempItem = new MarkedAssessAnswerItem();
        tempItem.setId(_item.getId());
        tempItem.setTemplateId(_item.getTemplateId());
        tempItem.setAnswerId(_item.getPerAssessAnswer().getId());
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
    private SimpleAssessAnswerDetailItem convertToASSDIOTD(PerAssessAnswerDetail _detail) {
        SimpleAssessAnswerDetailItem tempItem = new SimpleAssessAnswerDetailItem();
        tempItem.setId(_detail.getId());
        PerTemplateItem tempTemplateItem = this.perTemplateItemRepository.findOne(_detail.getItemId());
        if (tempTemplateItem.getType().equals(CommonConstant.DIC_TEMPLATE_ITEM_TYPE_TEXT)) {
            PerContent tempContent = this.perContentRepository.findOne(_detail.getItemValue());
            if(tempContent == null) {
                tempItem.setItemValue(_detail.getItemValue());
            } else {
                tempItem.setItemValue(tempContent.getContent());
            }
        } else {
            tempItem.setItemValue(_detail.getItemValue());
        }

        tempItem.setItemCode(_detail.getItemCode());
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

    private GeneralContentResult<MarkedAssessAnswer> calculateAnswerScore(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req, Boolean _maFlag) throws YYException {
        this.checkAssessPaperAnswerStatus(_maFlag, _assessPaperId);
        PerAssessAspMap tempPerAssessAspMap = this.perAssessAspMapRepository.findByAssessPaperIdAndAssessIdAndStatus(_assessPaperId, _assessId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        PerAPAAScore tempScore = this.perAssessAnswerItemRepository.getAssessAnswerScore(_req.getAssessAnswerId(), _req.getAssessAnswerItemId());
        BigDecimal tempTotalMarkedScore = BigDecimal.ZERO;
        BigDecimal tempTotalAuditScore = BigDecimal.ZERO;
        BigDecimal tempItemMarkedScore = BigDecimal.ZERO;
        BigDecimal tempItemAuditScore = BigDecimal.ZERO;
        BigDecimal tempScoringRatio = tempPerAssessAspMap.getScoringRatio();
//        BigDecimal tempAnswerScore = BigDecimal.ZERO;
        log.info("Loaded Score is:[{}] - [{}]", tempScore.getMarkedScore(), tempScore.getAuditScore());
        if (tempScore != null) {
            if (tempScore.getMarkedScore() != null)
                tempTotalMarkedScore = tempScore.getMarkedScore();
            if (tempScore.getAuditScore() != null)
                tempTotalAuditScore = tempScore.getAuditScore();
        }
        if (tempPerAssessAspMap.getItemThreshold().compareTo(BigDecimal.ZERO) == 0) {
            tempItemMarkedScore = _req.getScore();
            tempItemAuditScore = _req.getScore();
        } else {// 答案的总计分值不超过阈值（积分比率折算前的总分值），
            //    log.info("tempTotalMarkedScore={}, _req.score={}, tempPerAssessAspMap={}", tempTotalMarkedScore, _req, tempPerAssessAspMap);
            tempItemMarkedScore = _req.getScore().compareTo(tempPerAssessAspMap.getItemThreshold()) > 0 ? tempPerAssessAspMap.getItemThreshold() : _req.getScore();
            tempItemAuditScore = _req.getScore().compareTo(tempPerAssessAspMap.getItemThreshold()) > 0 ? tempPerAssessAspMap.getItemThreshold() : _req.getScore();
        }
        if (tempPerAssessAspMap.getScoringThreshold().compareTo(BigDecimal.ZERO) == 0) {
            tempTotalMarkedScore = tempTotalMarkedScore.add(tempItemMarkedScore);
            tempTotalAuditScore = tempTotalAuditScore.add(tempItemAuditScore);
        } else {// 答案的总计分值不超过阈值（积分比率折算前的总分值），
            //    log.info("tempTotalMarkedScore={}, _req.score={}, tempPerAssessAspMap={}", tempTotalMarkedScore, _req, tempPerAssessAspMap);
            tempTotalMarkedScore = (tempTotalMarkedScore.add(tempItemMarkedScore)).compareTo(tempPerAssessAspMap.getScoringThreshold()) > 0 ? tempPerAssessAspMap.getScoringThreshold() : tempTotalMarkedScore.add(tempItemMarkedScore);
            tempTotalAuditScore = (tempTotalAuditScore.add(tempItemAuditScore)).compareTo(tempPerAssessAspMap.getScoringThreshold()) > 0 ? tempPerAssessAspMap.getScoringThreshold() : tempTotalAuditScore.add(tempItemAuditScore);
        }
        log.info("The total Marked Score is: [{}] and Audit Score is:[{}], Marker is:[{}]", tempTotalMarkedScore, tempTotalAuditScore, _userId);
//        tempAnswerScore = tempTotalMarkedScore.multiply(tempScoringRatio);
        PerAssessAnswerItem tempPerAssessAnswerItem = this.perAssessAnswerItemRepository.findOne(_req.getAssessAnswerItemId());
        PerAssessAnswer tempAnswer = tempPerAssessAnswerItem.getPerAssessAnswer();
        if (_maFlag) { // first mark scoring.
            tempPerAssessAnswerItem.setMarkedScore(tempItemMarkedScore);
            tempPerAssessAnswerItem.setRMarkedScore(tempItemMarkedScore.multiply(tempScoringRatio));
            tempPerAssessAnswerItem.setMarkedComment(_req.getComments());
            tempAnswer.setMarkedScore(tempTotalMarkedScore);
            tempAnswer.setRMarkedScore(tempTotalMarkedScore.multiply(tempScoringRatio));
            tempAnswer.setMarkedComment(_req.getComments());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_MARKED);
            tempAnswer.setMarker(_userId);
        } else {
            tempPerAssessAnswerItem.setAuditScore(tempItemAuditScore);
            tempPerAssessAnswerItem.setRAuditScore(tempItemAuditScore.multiply(tempScoringRatio));
            tempPerAssessAnswerItem.setAuditComment(_req.getComments());
            tempAnswer.setAuditScore(tempTotalAuditScore);
//            tempAnswerScore = tempTotalAuditScore.multiply(tempScoringRatio);
            tempAnswer.setRAuditScore(tempTotalAuditScore.multiply(tempScoringRatio));
            tempAnswer.setAuditComment(_req.getComments());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_AUDITED);
            tempAnswer.setAuditor(_userId);
        }
//        log.info("Going to update assess answer[{}] and assess answer item[{}]",
//                tempAnswer, tempPerAssessAnswerItem);
//        this.perAssessAnswerItemRepository.save(tempPerAssessAnswerItem);
        tempAnswer = this.perAssessAnswerRepository.save(tempAnswer);
        GeneralContentResult<MarkedAssessAnswer> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
//        tempAnswer = this.perAssessAnswerRepository.findOne(_req.getAssessAnswerId());
        if (tempAnswer != null) {
            MarkedAssessAnswer tempMarkAnswer = new MarkedAssessAnswer();
            List<MarkedAssessAnswerItem> tempItemList = null;
            if (tempAnswer.getType() == CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER) {
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
        this.calculateSummaryScore(_userId, _assessPaperId, true, _markerId, (byte) 1);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralResult submitAssessPaperAuditScore(String _userId, String _assessPaperId, String _auditorId, Byte _level) throws YYException {
        this.calculateSummaryScore(_userId, _assessPaperId, false, _auditorId, _level);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    private void calculateSummaryScore(String _userId, String _assessPaperId, Boolean _maFlag, String _currentUserId, Byte _level) throws YYException {
        List<PerApacExamineeMap> tempApacExamineeMapList = this.perApacExamineeMapRepository.getApacExamineeMapItems(_assessPaperId, _userId);
        BigDecimal tempScore = BigDecimal.ZERO;
        PerAssessPaperExamineeMap tempPaperExamineeMap = this.perAssessPaperExamineeMapRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        if (tempPaperExamineeMap == null) {
            tempPaperExamineeMap = new PerAssessPaperExamineeMap();
            UserDetailsItem tempUserInfo = this.getUserDetailInfo(_userId);
            tempPaperExamineeMap.setTitle(tempUserInfo.getProfessionalTitle());
            tempPaperExamineeMap.setDeptId(tempUserInfo.getDeptId());
            tempPaperExamineeMap.setAssessPaperId(_assessPaperId);
        }
        if (tempApacExamineeMapList != null) {
            for (PerApacExamineeMap tempItem : tempApacExamineeMapList) {
                PerApAcMap tempApAcMap = this.perApAcMapRepository.findOne(tempItem.getApAcMapId());
                BigDecimal tempRatio = BigDecimal.ONE;
                if (tempApAcMap != null && tempApAcMap.getScoringRatio() != null) {
                    tempRatio = tempApAcMap.getScoringRatio();
                }

                BigDecimal tempThreshold = BigDecimal.ZERO;
                if (tempApAcMap != null && tempApAcMap.getScoringThreshold() != null) {
                    tempThreshold = tempApAcMap.getScoringThreshold();
                }
                //计分比率折算后的阈值
                if (_maFlag) {
                    BigDecimal tempItemScore = tempItem.getMarkedScore();
                    if (tempItemScore == null)
                        tempItemScore = BigDecimal.ZERO;
                    if (tempThreshold.compareTo(BigDecimal.ZERO) > 0) {
                        tempItemScore = tempItemScore.compareTo(tempThreshold) > 0 ? tempThreshold : tempItemScore;
                    }
                    tempItemScore = tempItemScore.multiply(tempRatio);
                    tempItem.setrMarkedScore(tempItemScore);
                    tempScore = tempScore.add(tempItemScore);
                } else {
                    BigDecimal tempItemScore = tempItem.getAuditScore();
                    if (tempItemScore == null)
                        tempItemScore = BigDecimal.ZERO;

                    if (tempThreshold.compareTo(BigDecimal.ZERO) > 0) {
                        tempItemScore = tempItemScore.compareTo(tempThreshold) > 0 ? tempThreshold : tempItemScore;
                    }
                    tempItemScore = tempItemScore.multiply(tempRatio);
                    tempItem.setrAuditScore(tempItemScore);
                    tempScore = tempScore.add(tempItemScore);
                }
                tempItem.setPerAssessPaperExamineeMap(tempPaperExamineeMap);
            }
            tempPaperExamineeMap.setPerApacExamineeMaps(tempApacExamineeMapList);
        }
        if (_maFlag) {
            tempPaperExamineeMap.setMarkedScore(tempScore);
            tempPaperExamineeMap.setMarker(_currentUserId);
            tempPaperExamineeMap.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_MARKED);
        } else {
            tempPaperExamineeMap.setAuditScore(tempScore);
            tempPaperExamineeMap.setAuditor(_currentUserId);
            tempPaperExamineeMap.setLevel(_level);
            tempPaperExamineeMap.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_AUDITED);
        }

        this.perAssessPaperExamineeMapRepository.save(tempPaperExamineeMap);
    }

    private UserDetailsItem getUserDetailInfo(String _userId) throws YYException {
        GeneralContentResult<UserDetailsItem> tempResult = this.securityClient.loadUserById(_userId);
//        log.info("The result is: {}", tempResult);
        if (tempResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)) {
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
        if (tempScoringItemPage != null) {
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
        if (_apAcList == null || _apAcList.size() == 0)
            return tempResult;
        List<PerApAcMap> tempApAcMapList = new ArrayList<>();
        for (ApAcScoringReq tempReq : _apAcList) {
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
        if (_apAssessList == null || _apAssessList.size() == 0)
            return tempResult;
        List<PerAssessAspMap> tempAssessAspMapList = new ArrayList<>();
        for (ApAssessScoringReq tempReq : _apAssessList) {
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
        if (_ratio == null)
            return BigDecimal.ONE;
        if (_ratio.compareTo(BigDecimal.ZERO) < 0) {
            throw new YYException(ResultCode.SCORING_RATIO_EXCEED_MIN);
        }
        if (_ratio.compareTo(BigDecimal.ONE) > 0) {
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
        if (_threshold == null)
            return BigDecimal.ZERO;
        if (_threshold.compareTo(BigDecimal.ZERO) < 0)
            throw new YYException(ResultCode.SCORING_THRESHOLD_EXCEED_MIN);
        return _threshold;
    }

    private void checkAssessPaperAnswerStatus(Boolean _maFlag, String _assessPaperId) throws YYException {
        PerAssessPeriod tempPeriod = this.perAssessPeriodRepository.getPerAssessPeriodByAssessPaperId(_assessPaperId);
        if (tempPeriod != null) {
            Timestamp tempCurrentTimestamp = new Timestamp(System.currentTimeMillis());
            if (_maFlag && tempPeriod.getScStart() != null && tempCurrentTimestamp.before(tempPeriod.getScStart())) {
                throw new YYException(ResultCode.ASSESS_ANSWER_SC_NOT_STARTED);
            }
            if (_maFlag && tempPeriod.getScEnd() != null && tempCurrentTimestamp.after(tempPeriod.getScEnd())) {
                throw new YYException(ResultCode.ASSESS_ANSWER_SC_ENDED_ALREADY);
            }
            if (!_maFlag && tempPeriod.getAuStart() != null && tempCurrentTimestamp.before(tempPeriod.getAuStart())) {
                throw new YYException(ResultCode.ASSESS_ANSWER_AU_NOT_STARTED);
            }
            if (!_maFlag && tempPeriod.getAuEnd() != null && tempCurrentTimestamp.after(tempPeriod.getAuEnd())) {
                throw new YYException(ResultCode.ASSESS_ANSWER_AU_ENDED_ALREADY);
            }
        }
    }
}
