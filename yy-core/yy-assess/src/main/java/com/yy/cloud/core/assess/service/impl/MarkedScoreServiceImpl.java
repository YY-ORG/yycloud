package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.assess.ScoreDetail;
import com.yy.cloud.common.data.dto.assess.AssessAnswerScoringReq;
import com.yy.cloud.common.data.otd.assess.AssessPaperExamineeMapItem;
import com.yy.cloud.common.data.otd.assess.MarkedAssessAnswer;
import com.yy.cloud.common.data.otd.assess.MarkedAssessAnswerItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessAnswerDetailItem;
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

import javax.xml.transform.Result;
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
        return this.calculateScore(_userId, _assessPaperId, _assessId, _req, Boolean.TRUE);
    }

    @Override
    public GeneralContentResult<MarkedAssessAnswer> auditScoreAssessAnswer(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req) throws YYException {
        return this.calculateScore(_userId, _assessPaperId, _assessId, _req, Boolean.FALSE);
    }

    private GeneralContentResult<MarkedAssessAnswer> calculateScore(String _userId, String _assessPaperId, String _assessId, AssessAnswerScoringReq _req, Boolean _maFlag){
        PerAssessAspMap tempPerAssessAspMap = this.perAssessAspMapRepository.findByAssessPaperIdAndAssessIdAndStatus(_assessPaperId, _assessId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        PerAPAAScore tempScore = this.perAssessAnswerItemRepository.getAssessAnswerScore(_req.getAssessAnswerId(), _req.getAssessAnswerItemId());
        BigDecimal tempTotalMarkedScore = BigDecimal.ZERO;
        BigDecimal tempTotalAuditScore = BigDecimal.ZERO;
        if(tempScore != null) {
            if(tempScore.getMarkedScore() != null)
                tempTotalMarkedScore = tempScore.getMarkedScore();
            if(tempScore.getAuditScore() != null)
                tempTotalAuditScore = tempScore.getAuditScore();
        }

        if(tempPerAssessAspMap.getScoringThreshold().compareTo(BigDecimal.ZERO) == 0) {
            tempTotalMarkedScore = tempTotalMarkedScore.add(_req.getScore());
            tempTotalAuditScore = tempTotalAuditScore.add(_req.getScore());
        } else {
        //    log.info("tempTotalMarkedScore={}, _req.score={}, tempPerAssessAspMap={}", tempTotalMarkedScore, _req, tempPerAssessAspMap);
            tempTotalMarkedScore = (tempTotalMarkedScore.add(_req.getScore())).compareTo(tempPerAssessAspMap.getScoringThreshold()) > 0 ? tempPerAssessAspMap.getScoringThreshold() : tempTotalMarkedScore.add(_req.getScore());
            tempTotalAuditScore = (tempTotalAuditScore.add(_req.getScore())).compareTo(tempPerAssessAspMap.getScoringThreshold()) > 0 ? tempPerAssessAspMap.getScoringThreshold() : tempTotalAuditScore.add(_req.getScore());
        }

        PerAssessAnswerItem tempPerAssessAnswerItem = this.perAssessAnswerItemRepository.findOne(_req.getAssessAnswerItemId());
        if(_maFlag) { // first mark scoring.
            tempPerAssessAnswerItem.setMarkedScore(_req.getScore());
            tempPerAssessAnswerItem.setRMarkedScore(_req.getScore().multiply(tempPerAssessAspMap.getScoringRatio()));
            tempPerAssessAnswerItem.setMarkedComment(_req.getComments());
            tempPerAssessAnswerItem.getPerAssessAnswer().setMarkedScore(tempTotalMarkedScore);
            tempPerAssessAnswerItem.getPerAssessAnswer().setRMarkedScore(tempTotalMarkedScore.multiply(tempPerAssessAspMap.getScoringRatio()));
            tempPerAssessAnswerItem.getPerAssessAnswer().setMarkedComment(_req.getComments());
            tempPerAssessAnswerItem.getPerAssessAnswer().setMarker(_userId);
        } else {
            tempPerAssessAnswerItem.setAuditScore(_req.getScore());
            tempPerAssessAnswerItem.setRAuditScore(_req.getScore().multiply(tempPerAssessAspMap.getScoringRatio()));
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
}
