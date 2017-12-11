package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;
import com.yy.cloud.common.data.dto.assess.AssessTIItemReq;
import com.yy.cloud.common.data.dto.assess.AssessTemplateReq;
import com.yy.cloud.common.data.otd.assess.SimpleAssessAnswerDetailItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessAnswerItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessGroupAnswerItem;
import com.yy.cloud.common.data.otd.assess.SimpleAssessPaperAnswerItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.data.domain.*;
import com.yy.cloud.core.assess.data.repositories.*;
import com.yy.cloud.core.assess.service.DoingAssessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 3:39 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class DoingAssessServiceImpl implements DoingAssessService {
    @Autowired
    private PerAssessAnswerRepository perAssessAnswerRepository;
    @Autowired
    private PerAssessAnswerItemRepository perAssessAnswerItemRepository;
    @Autowired
    private PerAspProcessOverviewRepository perAspProcessOverviewRepository;
    @Autowired
    private PerAssessAspMapRepository perAssessAspMapRepository;
    @Autowired
    private PerAssessRepository perAssessRepository;
    @Autowired
    private PerAssessPaperRepository perAssessPaperRepository;
    @Autowired
    private PerAssessCategoryRepository perAssessCategoryRepository;

    @Override
    @Transactional
    public GeneralResult submitSingleAnswerAssessAnswer(String _userId, AssessAnswerReq _answer) {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
                _answer.getAssessPaperId(), _answer.getAssessId(), _userId);
        if(tempAnswer == null) {
            tempAnswer = new PerAssessAnswer();
            tempAnswer.setAssessId(_answer.getAssessId());
            tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DONE);
            tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEANSWER);
            tempAnswer.setCreatorId(_userId);
            this.updateAssessPaperProcessOverview(_answer.getAssessPaperId(), _answer.getGroupId(), _userId);
        } else {
            List<String> tempTemplateIdList = _answer.getAnswerList().stream().map(tempItem -> {return tempItem.getTemplateId();}).collect(Collectors.toList());
            this.perAssessAnswerItemRepository.deleteByTypeAndTemplateIdIn(
                    CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY,
                    tempTemplateIdList);
        }
        PerAssessAnswer tempAnswerAA = tempAnswer;
        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream().
                map(tempTemplate -> this.packAssessAnswerItemDTO(tempTemplate, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY, tempAnswerAA)).collect(Collectors.toList());
        tempAnswer.setPerAssessAnswerItems(tempAnswerItemList);

        this.perAssessAnswerRepository.save(tempAnswer);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<String>> addAssessSubAnswer(String _userId, AssessAnswerReq _answer) {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.
                findByAssessPaperIdAndAssessIdAndCreatorId(_answer.getAssessPaperId(), _answer.getAssessId(), _userId);

        if(tempAnswer == null){
            tempAnswer = new PerAssessAnswer();
            tempAnswer.setAssessId(_answer.getAssessId());
            tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DOING);
            tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER);
            tempAnswer.setCreatorId(_userId);
        }
        PerAssessAnswer tempAnswerAA = tempAnswer;
        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream()
                .map(tempItem -> this.packAssessAnswerItemDTO(tempItem, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_SUB,
                        tempAnswerAA)).collect(Collectors.toList());

        List<PerAssessAnswerItem> tempItemList = this.perAssessAnswerItemRepository.save(tempAnswerItemList);
        List<String> tempResultList = tempItemList.stream().map(tempItem -> {return tempItem.getId();}).collect(Collectors.toList());

        GeneralContentResult<List<String>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempResultList);
        return tempResult;
    }

    /**
     * 增加考题的做题记录
     *
     * @param _assessPaperId
     * @param _groupId
     * @param _userId
     */
    private void updateAssessPaperProcessOverview(String _assessPaperId, String _groupId, String _userId){
        Optional<PerAspProcessOverview> tempOverviewOpt = this.perAspProcessOverviewRepository.findByAssessPaperIdAndCategoryIdAndCreatorId(
                _assessPaperId, _groupId, _userId);
        PerAspProcessOverview tempOverview;
        if(tempOverviewOpt.isPresent()){
            tempOverview = tempOverviewOpt.get();
            tempOverview.setCompletedCount(tempOverview.getAssessCount() + 1);
        } else {
            tempOverview = new PerAspProcessOverview();
            tempOverview.setAssessPaperId(_assessPaperId);
            tempOverview.setCategoryId(_groupId);
            tempOverview.setCreatorId(_userId);
            tempOverview.setAssessCount(this.perAssessAspMapRepository.countByAssessPaperIdAndAssessCategoryId(
                    _assessPaperId, _groupId));
        }
        this.perAspProcessOverviewRepository.save(tempOverview);
    }

    @Override
    @Transactional
    public GeneralResult deleteAssessSubAnswer(List<String> _answerItemId) {
        this.perAssessAnswerItemRepository.deleteByIdIn(_answerItemId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralContentResult<List<String>> addMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer) {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
                _answer.getAssessPaperId(), _answer.getAssessId(), _userId);
        if(tempAnswer == null) {
            tempAnswer = new PerAssessAnswer();
            tempAnswer.setAssessId(_answer.getAssessId());
            tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DOING);
            tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_MULTIANSWERS);
            tempAnswer.setCreatorId(_userId);
//            PerAssessAnswer tempAnswerAA = tempAnswer;
//            List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream().
//                    map(tempTemplate -> this.packAssessAnswerItemDTO(tempTemplate, CommonConstant.DIC_ASSESSANSWER_TYPE_MULTIANSWERS, tempAnswerAA)).collect(Collectors.toList());
//            tempAnswer.setPerAssessAnswerItems(tempAnswerItemList);
//            this.perAssessAnswerRepository.save(tempAnswer);
        }
        PerAssessAnswer tempAnswerAA = tempAnswer;
        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream().
                map(tempTemplate -> this.packAssessAnswerItemDTO(tempTemplate, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY, tempAnswerAA)).collect(Collectors.toList());
        List<PerAssessAnswerItem> tempResultItemList = this.perAssessAnswerItemRepository.save(tempAnswerItemList);

        List<String> tempResultContent = tempResultItemList.stream().map(tempItem -> {return tempItem.getId();}).collect(Collectors.toList());
        GeneralContentResult<List<String>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempResultContent);
        return tempResult;
    }

    @Override
    @Transactional
    public GeneralResult deleteMultiAnswerAssessAnswer(List<String> _answerIdList) {
        this.perAssessAnswerItemRepository.deleteByIdIn(_answerIdList);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralResult submitMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer) {
        this.updateAssessPaperProcessOverview(_answer.getAssessPaperId(), _answer.getGroupId(), _userId);
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
                _answer.getAssessPaperId(), _answer.getAssessId(), _userId);
        tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DONE);
        this.perAssessAnswerRepository.save(tempAnswer);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<SimpleAssessAnswerItem>> getAssessAnswerItemList(String _userId, String _assessPaperId, String _assessId) {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(_assessPaperId, _assessId, _userId);

        List<SimpleAssessAnswerItem> tempAnswerItemList;
        if(tempAnswer == null)
            tempAnswerItemList = new ArrayList<>();
        else
            tempAnswerItemList = tempAnswer.getPerAssessAnswerItems().stream().map(this::convertToSAAIOTD).collect(Collectors.toList());

        GeneralContentResult<List<SimpleAssessAnswerItem>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempAnswerItemList);
        return tempResult;
    }

    /**
     * 转换答案的Item
     *
     * @param _item
     * @return
     */
    private SimpleAssessAnswerItem convertToSAAIOTD(PerAssessAnswerItem _item){
        SimpleAssessAnswerItem tempItem = new SimpleAssessAnswerItem();
        tempItem.setId(_item.getId());
        tempItem.setTemplateId(_item.getId());
        tempItem.setSeqNo(_item.getSeqNo());
        tempItem.setType(_item.getType());
        tempItem.setDetailList(
                _item.getPerAssessAnswerDetails().stream().map(
                        this::convertToASSDIOTD
                ).collect(Collectors.toList())
        );

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

    /**
     * 封装考题的答案元素
     *
     * @param _req
     * @param _answer
     * @return
     */
    private PerAssessAnswerItem packAssessAnswerItemDTO(AssessTemplateReq _req, Byte _type, PerAssessAnswer _answer){
        PerAssessAnswerItem tempAnswerItem = new PerAssessAnswerItem();
        tempAnswerItem.setTemplateId(_req.getTemplateId());
        tempAnswerItem.setSeqNo(_req.getSeqNo());
        tempAnswerItem.setPerAssessAnswer(_answer);
        tempAnswerItem.setType(_type);
        List<PerAssessAnswerDetail> tempItemList = _req.getItemList().stream().
                map(tempItem -> this.packAssessAnswerDetailDTO(tempItem, tempAnswerItem)).collect(Collectors.toList());
        tempAnswerItem.setPerAssessAnswerDetails(tempItemList);
        return tempAnswerItem;
    }

    /**
     * 封装考题答案的详细记录
     *
     * @param _req
     * @param _answerItem
     * @return
     */
    private PerAssessAnswerDetail packAssessAnswerDetailDTO(AssessTIItemReq _req, PerAssessAnswerItem _answerItem){
        PerAssessAnswerDetail tempDetail = new PerAssessAnswerDetail();
        tempDetail.setItemCode(_req.getCode());
        tempDetail.setItemValue(_req.getValue());
        tempDetail.setItemId(_req.getId());
        tempDetail.setPerAssessAnswerItem(_answerItem);
        return tempDetail;
    }

    @Override
    public GeneralContentResult<List<SimpleAssessGroupAnswerItem>> getAllAssessPaperAnswerSumListByUser(String _userId) {



        return null;
    }

    @Override
    public GeneralContentResult<SimpleAssessPaperAnswerItem> getAssessPaperAnswerSumByUser(String _userId, String _assessPaperId) {
        GeneralContentResult<SimpleAssessPaperAnswerItem> tempResult = new GeneralContentResult<>();
        SimpleAssessPaperAnswerItem tempPaperAnswerItem = new SimpleAssessPaperAnswerItem();
        tempPaperAnswerItem.setId(_assessPaperId);

        PerAssessPaper tempAssessPaper = this.perAssessPaperRepository.findOne(_assessPaperId);
        if(tempAssessPaper == null){
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            tempResult.setResultContent(tempPaperAnswerItem);
            return tempResult;
        }
        tempPaperAnswerItem.setCode(tempAssessPaper.getCode());
        tempPaperAnswerItem.setName(tempAssessPaper.getName());

        List<PerAPACCount> orgCountList = this.perAssessAspMapRepository.getGroupCountByAssessPaper(_assessPaperId);

        if(orgCountList == null) {
//            tempPaperAnswerItem.setUnstartedCount(0);
//            tempPaperAnswerItem.setDoingCount(0);
//            tempPaperAnswerItem.setDoneCount(0);
//            tempPaperAnswerItem.setMarkedCount(0);
//            tempPaperAnswerItem.setAuditedCount(0);
//            tempPaperAnswerItem.setTotalCount(0);
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            tempResult.setResultContent(tempPaperAnswerItem);
            return tempResult;
        }
        int tempTotalCount = 0;
        int tempDoneCount = 0;
        List<SimpleAssessGroupAnswerItem> tempGrouAnswerItemList = new ArrayList<>();
        List<PerAspProcessOverview> answerCountList = this.perAspProcessOverviewRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        for(PerAPACCount tempItem : orgCountList){
            PerAssessCategory tempCategory = this.perAssessCategoryRepository.findOne(tempItem.getGroupId());
            if(tempCategory == null){
                continue;
            }
            SimpleAssessGroupAnswerItem tempGroupItem = new SimpleAssessGroupAnswerItem();
            tempGroupItem.setGroupId(tempItem.getGroupId());
            tempGroupItem.setGroupCode(tempCategory.getCode());
            tempGroupItem.setGroupName(tempCategory.getName());
            tempGroupItem.setUnstartedCount(tempItem.getTotalCount());//默认所有的题都未做
            tempGroupItem.setTotalCount(tempItem.getTotalCount());
            tempTotalCount += tempItem.getTotalCount();
            for(PerAspProcessOverview tempOverViewItem : answerCountList) {
                if(tempOverViewItem.getCategoryId().equals(tempItem.getGroupId())) {//如果题做过，则会在记录表中存在记录，重设未做数量
                    tempGroupItem.setUnstartedCount(tempItem.getTotalCount() - tempOverViewItem.getCompletedCount());
                    tempGroupItem.setDoneCount(tempOverViewItem.getCompletedCount());
                    tempDoneCount += tempOverViewItem.getCompletedCount();
                }
            }
            tempGrouAnswerItemList.add(tempGroupItem);
        }
        tempPaperAnswerItem.setUnstartedCount(tempTotalCount - tempDoneCount);
        tempPaperAnswerItem.setDoneCount(tempDoneCount);
        tempPaperAnswerItem.setTotalCount(tempTotalCount);
        tempPaperAnswerItem.setGroupAnswerItemList(tempGrouAnswerItemList);

        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempPaperAnswerItem);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<SimpleAssessGroupAnswerItem>> getAllAssessPaperAnswerSumListByOrg(String _orgId) {
        return null;
    }

    @Override
    public GeneralContentResult<List<SimpleAssessGroupAnswerItem>> getAssessPaperAnswerSumListByOrg(String _orgId, String _assessPaperId) {
        return null;
    }

    @Override
    public GeneralResult submitAssessPaperAnswer(String _userId, String _assessPaperId) throws YYException {


        return null;
    }
}
