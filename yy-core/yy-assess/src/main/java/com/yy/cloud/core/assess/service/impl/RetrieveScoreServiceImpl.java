package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.assess.SimplePersonalAnswerScoreItem;
import com.yy.cloud.common.data.otd.assess.SimplePersonalScoreDetail;
import com.yy.cloud.common.data.otd.assess.SimpleRankingItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.PerApacExamineeMapRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessAnswerRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessPaperExamineeMapRepository;
import com.yy.cloud.core.assess.data.repositories.PerExamineeRankingRepository;
import com.yy.cloud.core.assess.service.RetrieveScoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/28/18 9:20 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class RetrieveScoreServiceImpl implements RetrieveScoreService {
    @Autowired
    private PerExamineeRankingRepository perExamineeRankingRepository;
    @Autowired
    private PerAssessPaperExamineeMapRepository perAssessPaperExamineeMapRepository;
    @Autowired
    private PerApacExamineeMapRepository perApacExamineeMapRepository;
    @Autowired
    private PerAssessAnswerRepository perAssessAnswerRepository;

    @Override
    public GeneralContentResult<List<SimplePersonalScoreDetail>> getPersoanlAnswerScoreList(String _userId, String _assessPaperId) throws YYException {
        GeneralContentResult<List<SimplePersonalScoreDetail>> tempResult = new GeneralContentResult<>();
        PerAssessPaperExamineeMap tempAPEMItem = this.perAssessPaperExamineeMapRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        if(tempAPEMItem == null){
            throw new YYException(ResultCode.ASSESS_ANSWER_GET_FAILED);
        }
        List<PerApAcExamineeDetailItem> tempCategoryExamineeItemList = this.perApacExamineeMapRepository.getApAcExamineeDetailItemList(tempAPEMItem.getId());
        List<SimplePersonalScoreDetail> tempContent = new ArrayList<>();
        for(PerApAcExamineeDetailItem tempItem : tempCategoryExamineeItemList){
            SimplePersonalScoreDetail tempContentItem = new SimplePersonalScoreDetail();
            tempContentItem.setCategoryId(tempItem.getApAcId());
            tempContentItem.setCategoryName(tempItem.getApAcName());
            tempContentItem.setScoringRatio(tempItem.getScoringRatio());
            tempContentItem.setTotalScore(tempItem.getAuditScore());
            List<PerAssessAssessAnswerItem> tempAnswerItemList = this.perAssessAnswerRepository.getAssessAssessAnswerItemList(_assessPaperId, tempItem.getApAcId(), _userId);
            List<SimplePersonalAnswerScoreItem> tempItemList = tempAnswerItemList.stream().map(this::convertPAAIToSPASI).collect(Collectors.toList());
            tempContentItem.setItemList(tempItemList);
            tempContent.add(tempContentItem);
        }
        tempResult.setResultContent(tempContent);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    private SimplePersonalAnswerScoreItem convertPAAIToSPASI(PerAssessAssessAnswerItem _item){
        SimplePersonalAnswerScoreItem tempItem = new SimplePersonalAnswerScoreItem();
        tempItem.setAssessId(_item.getAssessId());
        tempItem.setAssessName(_item.getAssessName());
        tempItem.setAssessAnswerId(_item.getAssessAnswerId());
//        tempItem.setCategoryId(_categoryId);
//        tempItem.setCategoryName(_categoryName);
        tempItem.setTotalScore(_item.getTotalScore());
        tempItem.setRealScore(_item.getRealScore());
        tempItem.setScoringRatio(_item.getScoringRatio());
        tempItem.setScoringThreshold(_item.getScoringThreshold());
        tempItem.setComment(_item.getComment());
        return tempItem;
    }

    @Override
    public GeneralPagingResult<List<SimpleRankingItem>> getTotalRankList(String _assessPaperId, Pageable _page) throws YYException {

        return null;
    }

    @Override
    public GeneralPagingResult<List<SimpleRankingItem>> getTotalRankListByCon(String _assessPaperId, String _orgId, Byte _title, Pageable _page) throws YYException{
        GeneralPagingResult<List<SimpleRankingItem>> tempResult = new GeneralPagingResult<>();
        Page<PerExamineeRanking> tempPage = null;
        if(StringUtils.isBlank(_orgId) && _title == null)
            tempPage = this.perExamineeRankingRepository.findByAssessPaperId(_assessPaperId, _page);
        else if(StringUtils.isNotBlank(_orgId) && _title == null)
            tempPage = this.perExamineeRankingRepository.findByAssessPaperIdAndOrgId(_assessPaperId, _orgId, _page);
        else if(StringUtils.isBlank(_orgId) && _title != null)
            tempPage = this.perExamineeRankingRepository.findByAssessPaperIdAndTitle(_assessPaperId, _title, _page);
        else if(StringUtils.isNotBlank(_orgId) && _title != null)
            tempPage = this.perExamineeRankingRepository.findByAssessPaperIdAndOrgIdAndTitle(_assessPaperId, _orgId, _title, _page);

        List<SimpleRankingItem> tempContent = tempPage.getContent().stream().map(this::convertPERToSRI).collect(Collectors.toList());
        tempResult.setResultContent(tempContent);

        PageInfo _pageInfo = new PageInfo();
        _pageInfo.setCurrentPage(tempPage.getNumber());
        _pageInfo.setPageSize(tempPage.getSize());
        _pageInfo.setTotalPage(tempPage.getTotalPages());
        _pageInfo.setTotalRecords(tempPage.getTotalElements());

        tempResult.setPageInfo(_pageInfo);
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    private SimpleRankingItem convertPERToSRI(PerExamineeRanking _item){
        SimpleRankingItem tempItem = new SimpleRankingItem();
        tempItem.setId(_item.getId());
        tempItem.setOrgId(_item.getOrgId());
        tempItem.setOrgName(_item.getOrgName());
        tempItem.setUserId(_item.getUserId());
        tempItem.setUserName(_item.getUserName());
        tempItem.setTitle(_item.getTitle());
        tempItem.setTotalScore(_item.getScore());
        tempItem.setTotalRank(_item.getTotalRanking());
        tempItem.setOrgRank(_item.getOrgRanking());
        tempItem.setRankLevel(_item.getLevel());

        return tempItem;
    }
}
