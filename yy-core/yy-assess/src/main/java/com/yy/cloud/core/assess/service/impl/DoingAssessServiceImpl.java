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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    private PerTemplateItemRepository perTemplateItemRepository;
    @Autowired
    private PerAssessPaperExamineeMapRepository perAssessPaperExamineeMapRepository;
    @Autowired
    private PerAssessOrgMapRepository perAssessOrgMapRepository;
    @Autowired
    private PerAssessAnswerDetailRepository perAssessAnswerDetailRepository;
    @Autowired
    private PerAssessPeriodRepository perAssessPeriodRepository;
    @Autowired
    private PerContentRepository perContentRepository;

    @Override
    @Transactional
    public GeneralResult submitSingleAnswerAssessAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _answer.getAssessPaperId());
        }
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
                _answer.getAssessPaperId(), _answer.getAssessId(), _userId);
        if (tempAnswer == null) {
            tempAnswer = new PerAssessAnswer();
            tempAnswer.setAssessId(_answer.getAssessId());
            tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DONE);
            tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEANSWER);
            tempAnswer.setCreatorId(_userId);
        } /*else {
            List<String> tempTemplateIdList = _answer.getAnswerList().stream().map(tempItem -> {return tempItem.getTemplateId();}).collect(Collectors.toList());
            this.perAssessAnswerItemRepository.deleteByTypeAndTemplateIdIn(
                    CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY,
                    tempTemplateIdList);
        }*/
        PerAssessAnswer tempAnswerAA = tempAnswer;
        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream().
                map(tempTemplate -> this.packAssessAnswerItemDTO(tempTemplate, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY, tempAnswerAA, false)).collect(Collectors.toList());
        tempAnswer.setPerAssessAnswerItems(tempAnswerItemList);

        this.perAssessAnswerRepository.save(tempAnswer);
        this.perAssessAnswerRepository.flush();
        this.updateAssessPaperProcessOverview(_answer.getAssessPaperId(), _answer.getGroupId(), _userId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<String>> addAssessSubAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _answer.getAssessPaperId());
        }
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.
                findByAssessPaperIdAndAssessIdAndCreatorId(_answer.getAssessPaperId(), _answer.getAssessId(), _userId);

        if (tempAnswer == null) {
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
                        tempAnswerAA, true)).collect(Collectors.toList());

        List<PerAssessAnswerItem> tempItemList = this.perAssessAnswerItemRepository.save(tempAnswerItemList);
        List<String> tempResultList = tempItemList.stream().map(tempItem -> tempItem.getId()).collect(Collectors.toList());

        GeneralContentResult<List<String>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempResultList);
        return tempResult;
    }

    @Override
    @Transactional
    public GeneralContentResult<String> updateAssessSubAnswer(String _userId, String _subAnswerId, AssessAnswerReq _answer, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _answer.getAssessPaperId());
        }

        if (_answer.getAnswerList() == null || _answer.getAnswerList().size() == 0) {
            throw new YYException(ResultCode.ASSESS_ANSWER_NOTEXISTS, "新的答案为空，还请重新提交！");
        }

//        this.perAssessAnswerItemRepository.delete(_subAnswerId);
        AssessTemplateReq tempItemReq = _answer.getAnswerList().get(0);
        tempItemReq.setId(_subAnswerId);
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.
                findByAssessPaperIdAndAssessIdAndCreatorId(_answer.getAssessPaperId(), _answer.getAssessId(), _userId);

        PerAssessAnswerItem tempAnswerItem = this.packAssessAnswerItemDTO(tempItemReq, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_SUB,
                tempAnswer, true);

        PerAssessAnswerItem tempItemList = this.perAssessAnswerItemRepository.save(tempAnswerItem);

        GeneralContentResult<String> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempItemList.getId());
        return tempResult;
    }

    /**
     * 增加考题的做题记录
     *
     * @param _assessPaperId
     * @param _groupId
     * @param _userId
     */
    private void updateAssessPaperProcessOverview(String _assessPaperId, String _groupId, String _userId) {
        Optional<PerAspProcessOverview> tempOverviewOpt = this.perAspProcessOverviewRepository.findByAssessPaperIdAndCategoryIdAndCreatorId(
                _assessPaperId, _groupId, _userId);
        PerAspProcessOverview tempOverview;

        PerAssessAnswerCount tempCount = this.perAssessAnswerRepository.getCompletedAnswerCount(_assessPaperId, _groupId, _userId);
        if (tempOverviewOpt.isPresent()) {
            tempOverview = tempOverviewOpt.get();
            //   int tempCount = tempOverview.getCompletedCount() + 1;
            tempOverview.setCompletedCount(tempCount.getCompletedCount());
        } else {
            tempOverview = new PerAspProcessOverview();
            tempOverview.setAssessPaperId(_assessPaperId);
            tempOverview.setCategoryId(_groupId);
            tempOverview.setCreatorId(_userId);
            tempOverview.setCompletedCount(tempCount.getCompletedCount());
            tempOverview.setAssessCount(this.perAssessAspMapRepository.countByAssessPaperIdAndAssessCategoryId(
                    _assessPaperId, _groupId));
        }
        this.perAspProcessOverviewRepository.save(tempOverview);
    }

    /**
     * 扣除已答个数
     *
     * @param _assessPaperId
     * @param _groupId
     * @param _userId
     */
    private void revertAssessPaperProcessOverview(String _assessPaperId, String _groupId, String _userId) {
        Optional<PerAspProcessOverview> tempOverviewOpt = this.perAspProcessOverviewRepository.findByAssessPaperIdAndCategoryIdAndCreatorId(
                _assessPaperId, _groupId, _userId);
        PerAspProcessOverview tempOverview;
        if (tempOverviewOpt.isPresent()) {
            tempOverview = tempOverviewOpt.get();
            int tempCount = tempOverview.getCompletedCount() - 1;
            tempOverview.setCompletedCount(tempCount > 0 ? tempCount : 0);
            this.perAspProcessOverviewRepository.save(tempOverview);
        }
    }


    @Override
    @Transactional
    public GeneralResult deleteAssessSubAnswer(String _userId, String _assessPaperId, List<String> _answerItemId, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _assessPaperId);
        }

        this.perAssessAnswerDetailRepository.deleteByPerAssessAnswerItemIdIn(_answerItemId);
        this.perAssessAnswerItemRepository.deleteByIdIn(_answerItemId);
//        List<PerAssessAnswerItem> tempItems = this.perAssessAnswerItemRepository.findAll(_answerItemId);
//        this.perAssessAnswerItemRepository.deleteInBatch(tempItems);

        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralContentResult<List<String>> addMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _answer.getAssessPaperId());
        }

        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
                _answer.getAssessPaperId(), _answer.getAssessId(), _userId);
        if (tempAnswer == null) {
            tempAnswer = new PerAssessAnswer();
            tempAnswer.setAssessId(_answer.getAssessId());
            tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DOING);
            tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_MULTIANSWERS);
            tempAnswer.setCreatorId(_userId);
        }
        PerAssessAnswer tempAnswerAA = tempAnswer;
        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream().
                map(tempTemplate -> this.packAssessAnswerItemDTO(tempTemplate, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY, tempAnswerAA, true)).collect(Collectors.toList());

        List<PerAssessAnswerItem> tempResultItemList = this.perAssessAnswerItemRepository.save(tempAnswerItemList);

        List<String> tempResultContent = tempResultItemList.stream().map(tempItem -> tempItem.getId()).collect(Collectors.toList());
        GeneralContentResult<List<String>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempResultContent);
        return tempResult;
    }

    @Override
    public GeneralContentResult<String> updateMultiAnswerAssessAnswer(String _userId, String _answerItemId, AssessAnswerReq _answer, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _answer.getAssessPaperId());
        }

        if (_answer.getAnswerList() == null || _answer.getAnswerList().size() == 0) {
            throw new YYException(ResultCode.ASSESS_ANSWER_NOTEXISTS, "新的答案为空，还请重新提交！");
        }

        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.
                findByAssessPaperIdAndAssessIdAndCreatorId(_answer.getAssessPaperId(), _answer.getAssessId(), _userId);

//        this.perAssessAnswerItemRepository.delete(_answerItemId);
        AssessTemplateReq tempItemReq = _answer.getAnswerList().get(0);
        tempItemReq.setId(_answerItemId);
        PerAssessAnswerItem tempAnswerItem = this.packAssessAnswerItemDTO(tempItemReq, CommonConstant.DIC_ASSESSANSWERITEM_TYPE_PRIMARY,
                tempAnswer, true);

        PerAssessAnswerItem tempItemList = this.perAssessAnswerItemRepository.save(tempAnswerItem);

        GeneralContentResult<String> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempItemList.getId());
        return tempResult;
    }

    @Override
    @Transactional
    public GeneralResult deleteMultiAnswerAssessAnswer(String _userId, String _assessPaperId, List<String> _answerIdList, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _assessPaperId);
        }

        List<PerAssessAnswerItem> tempAnswerItemList = this.perAssessAnswerItemRepository.findAll(_answerIdList);
        log.debug("Loaded Items {}.", tempAnswerItemList);
        List<String> tempAssessAnswerIdList = tempAnswerItemList.stream().map(tempItem -> tempItem.getPerAssessAnswer().getId()).collect(Collectors.toList());
        this.perAssessAnswerDetailRepository.deleteByPerAssessAnswerItemIdIn(_answerIdList);
        this.perAssessAnswerItemRepository.deleteInBatch(tempAnswerItemList);
        log.debug("This time deleted {}'s records.", _answerIdList);
        for (String _tempAnswerId : tempAssessAnswerIdList) {
            PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.getOne(_tempAnswerId);
            List<PerAssessAnswerItem> itemList = tempAnswer.getPerAssessAnswerItems();
            if (itemList == null || itemList.size() == 0) {//如果答案删除完了，则将该题置为"未开始"
                tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_NOT_STARTED);
            }
            PerAssessAspMap tempAssessAspMap = this.perAssessAspMapRepository.findByAssessPaperIdAndAssessIdAndStatus(_assessPaperId, tempAnswer.getAssessId(), CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
            //将已做的统计减一
            this.revertAssessPaperProcessOverview(_assessPaperId, tempAssessAspMap.getAssessCategoryId(), _userId);
        }

        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);

        return tempResult;
    }

    @Override
    public GeneralResult submitMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer, String _commitorId) throws YYException {
        if (StringUtils.isBlank(_userId)) {
            _userId = _commitorId;
            this.checkAssessPaperAnswerStatus(_userId, _answer.getAssessPaperId());
        }
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(
                _answer.getAssessPaperId(), _answer.getAssessId(), _userId);
        tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DONE);
        this.perAssessAnswerRepository.save(tempAnswer);
        this.perAssessAnswerRepository.flush();
        this.updateAssessPaperProcessOverview(_answer.getAssessPaperId(), _answer.getGroupId(), _userId);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }

    @Override
    public GeneralContentResult<List<SimpleAssessAnswerItem>> getAssessAnswerItemList(String _userId, String _assessPaperId, String _assessId) {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(_assessPaperId, _assessId, _userId);

        List<SimpleAssessAnswerItem> tempAnswerItemList;
        if (tempAnswer == null)
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
    private SimpleAssessAnswerItem convertToSAAIOTD(PerAssessAnswerItem _item) {
        SimpleAssessAnswerItem tempItem = new SimpleAssessAnswerItem();
        tempItem.setId(_item.getId());
        tempItem.setTemplateId(_item.getId());
        tempItem.setAnswerId(_item.getPerAssessAnswer().getId());
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
    private SimpleAssessAnswerDetailItem convertToASSDIOTD(PerAssessAnswerDetail _detail) {
        SimpleAssessAnswerDetailItem tempItem = new SimpleAssessAnswerDetailItem();
        tempItem.setId(_detail.getId());
        PerTemplateItem tempTemplateItem = this.perTemplateItemRepository.findOne(_detail.getItemId());
        if (tempTemplateItem.getType().equals(CommonConstant.DIC_TEMPLATE_ITEM_TYPE_TEXT)) {
            PerContent tempContent = this.perContentRepository.findOne(_detail.getItemValue());
            if (tempContent == null) {
                tempItem.setItemValue(_detail.getItemValue());
            } else
                tempItem.setItemValue(tempContent.getContent());
        } else {
            tempItem.setItemValue(_detail.getItemValue());
        }

        tempItem.setItemCode(_detail.getItemCode());
        return tempItem;
    }

    /**
     * 封装考题的答案元素
     *
     * @param _req
     * @param _answer
     * @return
     */
    private PerAssessAnswerItem packAssessAnswerItemDTO(AssessTemplateReq _req, Byte _type, PerAssessAnswer _answer, Boolean _multiFlag) {
        PerAssessAnswerItem tempAnswerItem = null;
        List<PerAssessAnswerItem> tempItemList = _answer.getPerAssessAnswerItems();
        if(tempItemList == null) {
            tempItemList = new ArrayList<>();
            _answer.setPerAssessAnswerItems(tempItemList);
        }

        for (PerAssessAnswerItem _tempItem : tempItemList) {
            if (!_multiFlag && _tempItem.getTemplateId().equals(_req.getTemplateId())) {
                tempAnswerItem = _tempItem;
                tempAnswerItem.getPerAssessAnswerDetails().clear();
                break;
            } else if(_multiFlag && _tempItem.getId().equals(_req.getId())) {
                tempAnswerItem = _tempItem;
                tempAnswerItem.getPerAssessAnswerDetails().clear();
                break;
            }
        }

        if (tempAnswerItem == null) {
            tempAnswerItem = new PerAssessAnswerItem();
            tempAnswerItem.setTemplateId(_req.getTemplateId());
            tempAnswerItem.setSeqNo(_req.getSeqNo());
            tempAnswerItem.setPerAssessAnswer(_answer);
            tempAnswerItem.setType(_type);
            tempAnswerItem.setPerAssessAnswerDetails(new ArrayList<>());
        }
        PerAssessAnswerItem tempAnswerItem2 = tempAnswerItem;
        List<PerAssessAnswerDetail> tempItemDetailList = _req.getItemList().stream().
                map(tempItem -> this.packAssessAnswerDetailDTO(tempItem, tempAnswerItem2)).collect(Collectors.toList());

        tempAnswerItem.getPerAssessAnswerDetails().addAll(tempItemDetailList);
        _answer.addPerAssessAnswerItem(tempAnswerItem);

        return tempAnswerItem;
    }

    /**
     * 封装考题答案的详细记录
     *
     * @param _req
     * @param _answerItem
     * @return
     */
    private PerAssessAnswerDetail packAssessAnswerDetailDTO(AssessTIItemReq _req, PerAssessAnswerItem _answerItem) {
        PerAssessAnswerDetail tempDetail = new PerAssessAnswerDetail();
        tempDetail.setItemCode(_req.getCode());
        PerTemplateItem tempTemplateItem = this.perTemplateItemRepository.findOne(_req.getId());
        if (tempTemplateItem.getType().equals(CommonConstant.DIC_TEMPLATE_ITEM_TYPE_TEXT)) {
            PerContent tempContent = new PerContent();
            tempContent.setContent(_req.getValue());
            tempContent.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
            tempContent = this.perContentRepository.save(tempContent);
            tempDetail.setItemValue(tempContent.getId());
        } else {
            tempDetail.setItemValue(_req.getValue());
        }

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
        if (tempAssessPaper == null) {
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            tempResult.setResultContent(tempPaperAnswerItem);
            return tempResult;
        }
        tempPaperAnswerItem.setCode(tempAssessPaper.getCode());
        tempPaperAnswerItem.setName(tempAssessPaper.getName());


        PerAssessPaperExamineeMap tempPAPEM = this.perAssessPaperExamineeMapRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        if (tempPAPEM != null) {
            tempPaperAnswerItem.setStatus(tempPAPEM.getStatus());
        } else {
            tempPaperAnswerItem.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_UNSUBMIT);
        }

        List<PerAPACCount> orgCountList = this.perAssessAspMapRepository.getGroupCountByAssessPaper(_assessPaperId);

        if (orgCountList == null) {
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            tempResult.setResultContent(tempPaperAnswerItem);
            return tempResult;
        }
        int tempTotalCount = 0;
        int tempDoneCount = 0;
        List<SimpleAssessGroupAnswerItem> tempGrouAnswerItemList = new ArrayList<>();
        List<PerAspProcessOverview> answerCountList = this.perAspProcessOverviewRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        for (PerAPACCount tempItem : orgCountList) {
//            PerAssessCategory tempCategory = this.perAssessCategoryRepository.findOne(tempItem.getGroupId());
//            if(tempCategory == null){
//                continue;
//            }
            SimpleAssessGroupAnswerItem tempGroupItem = new SimpleAssessGroupAnswerItem();
            tempGroupItem.setGroupId(tempItem.getGroupId());
            tempGroupItem.setGroupCode(tempItem.getGroupCode());
            tempGroupItem.setGroupName(tempItem.getGroupName());
            tempGroupItem.setUnstartedCount(tempItem.getTotalCount());//默认所有的题都未做
            tempGroupItem.setTotalCount(tempItem.getTotalCount());
            tempTotalCount += tempItem.getTotalCount();
            for (PerAspProcessOverview tempOverViewItem : answerCountList) {
                if (tempOverViewItem.getCategoryId().equals(tempItem.getGroupId())) {//如果题做过，则会在记录表中存在记录，重设未做数量
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
    public GeneralResult submitAssessPaperAnswer(String _userId, String _orgId, Byte _title, String _assessPaperId) throws YYException {
        PerAssessOrgMap tempAssessPaper = this.perAssessOrgMapRepository.findByAssessPaperIdAndOrgIdAndTitleTypeAndStatus(_assessPaperId, _orgId, _title, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        if (tempAssessPaper == null) {
            throw new YYException(ResultCode.ASSESS_ANSWER_SUBMIT_NOTEXISTS, "该考核人员不在该考卷的考核人员列表中，请重新选择考卷答题！");
        }
        // 补全未答题的答案
        this.completeUnsubmitAnswerByAsp(_userId, _assessPaperId);

        PerAssessPaperExamineeMap tempPAPEM = this.perAssessPaperExamineeMapRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        GeneralResult tempResult = new GeneralResult();
        if (tempPAPEM == null) {
            tempPAPEM = new PerAssessPaperExamineeMap();
            tempPAPEM.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);
            tempPAPEM.setAssessPaperId(_assessPaperId);
            tempPAPEM.setDeptId(_orgId);
            tempPAPEM.setTitle(_title);
            tempPAPEM.setCreatorId(_userId);
            this.perAssessPaperExamineeMapRepository.save(tempPAPEM);
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            return tempResult;
        } else if (tempPAPEM.getStatus() == CommonConstant.DIC_ASSESSPAPER_STATUS_UNSUBMIT) {
            tempPAPEM.setStatus(CommonConstant.DIC_ASSESSPAPER_STATUS_SUBMITTED);
            this.perAssessPaperExamineeMapRepository.save(tempPAPEM);
            tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
            return tempResult;
        } else {
            throw new YYException(ResultCode.ASSESS_ANSWER_SUBMIT_ALREADY, "你已提交该考卷，无法进行更改！");
        }
    }

    private void completeUnsubmitAnswerByAsp(String _userId, String _paperId) {
        List<PerAssessAspMap> tempAssessAspList = this.perAssessAspMapRepository.findByAssessPaperIdAndStatus(_paperId, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        tempAssessAspList.stream().forEach(item -> this.completeUnsubmitAnswer(_userId, item.getAssessId(), _paperId));
    }

    private void completeUnsubmitAnswer(String _userId, String _assessId, String _paperId) {
        PerAssessAnswer tempAnswer = this.perAssessAnswerRepository.findByAssessPaperIdAndAssessIdAndCreatorId(_paperId, _assessId, _userId);
        if (tempAnswer == null) {
            tempAnswer = new PerAssessAnswer();
            tempAnswer.setAssessId(_assessId);
            tempAnswer.setAssessPaperId(_paperId);
            tempAnswer.setCreatorId(_userId);
            tempAnswer.setStatus(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DONE);
            PerAssess tempAssess = this.perAssessRepository.findOne(_assessId);
            if (tempAssess != null) {
                Byte tempAssessType = tempAssess.getType();
                if (tempAssessType.equals(CommonConstant.DIC_ASSESS_TYPE_SINGLE_ANSWER)) {
                    tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEANSWER);
                } else if (tempAssessType.equals(CommonConstant.DIC_ASSESS_TYPE_TABLE_SINGLE_ANSWER)) {
                    tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER);
                } else if (tempAssessType.equals(CommonConstant.DIC_ASSESS_TYPE_TABLE_MULTI_ANSWERS)) {
                    tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_MULTIANSWERS);
                } else if (tempAssessType.equals(CommonConstant.DIC_ASSESS_TYPE_MULTI_ANSWERS)) {
                    tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_MULTIANSWERS);
                } else {
                    tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEANSWER);
                }
            } else {
                tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEANSWER);
            }
            this.perAssessAnswerRepository.save(tempAnswer);
        }
    }

    private void checkAssessPaperAnswerStatus(String _userId, String _assessPaperId) throws YYException {
//        PerAssessOrgMap tempAssessPaper = this.perAssessOrgMapRepository.findByAssessPaperIdAndOrgIdAndTitleTypeAndStatus(_assessPaperId, _orgId, _title, CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
//        if(tempAssessPaper == null){
//            throw new YYException(ResultCode.ASSESS_ANSWER_SUBMIT_NOTEXISTS);
//        }
        PerAssessPaperExamineeMap tempPAPEM = this.perAssessPaperExamineeMapRepository.findByAssessPaperIdAndCreatorId(_assessPaperId, _userId);
        if (tempPAPEM != null && tempPAPEM.getStatus() != CommonConstant.DIC_ASSESSPAPER_STATUS_UNSUBMIT) {
            throw new YYException(ResultCode.ASSESS_ANSWER_SUBMIT_ALREADY);
        }
        PerAssessPeriod tempPeriod = this.perAssessPeriodRepository.getPerAssessPeriodByAssessPaperId(_assessPaperId);
        if (tempPeriod != null) {
            Timestamp tempCurrentTimestamp = new Timestamp(System.currentTimeMillis());
            if (tempPeriod.getDoingStart() != null && tempCurrentTimestamp.before(tempPeriod.getDoingStart())) {
                throw new YYException(ResultCode.ASSESS_ANSWER_EX_NOT_STARTED);
            }
            if (tempPeriod.getDoingEnd() != null && tempCurrentTimestamp.after(tempPeriod.getDoingEnd())) {
                throw new YYException(ResultCode.ASSESS_ANSWER_EX_ENDED_ALREADY);
            }
        }
    }
}
