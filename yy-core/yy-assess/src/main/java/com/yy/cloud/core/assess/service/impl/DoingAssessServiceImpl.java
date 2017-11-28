package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;
import com.yy.cloud.common.data.dto.assess.AssessTIItemReq;
import com.yy.cloud.common.data.dto.assess.AssessTemplateReq;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswer;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswerDetail;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswerItem;
import com.yy.cloud.core.assess.data.repositories.PerAssessAnswerItemRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessAnswerRepository;
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

    @Override
    public GeneralResult submitSingleAnswerAssessAnswer(String _userId, AssessAnswerReq _answer) {
        PerAssessAnswer tempAnswer = new PerAssessAnswer();
        tempAnswer.setAssessId(_answer.getAssessId());
        tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
        tempAnswer.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        tempAnswer.setCreatorId(_userId);

        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream().
                map(tempTemplate -> this.packAssessAnswerItemDTO(tempTemplate, CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEANSWER, tempAnswer)).collect(Collectors.toList());
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
            tempAnswer.setType(CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER);
            tempAnswer.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
            tempAnswer.setCreatorId(_userId);
        }
        PerAssessAnswer tempAnswerAA = tempAnswer;
        List<PerAssessAnswerItem> tempAnswerItemList = _answer.getAnswerList().stream()
                .map(tempItem -> this.packAssessAnswerItemDTO(tempItem, CommonConstant.DIC_ASSESSANSWER_TYPE_SINGLEWITHMULTISUBS_ANSWER,
                        tempAnswerAA)).collect(Collectors.toList());

        List<PerAssessAnswerItem> tempItemList = this.perAssessAnswerItemRepository.save(tempAnswerItemList);
        List<String> tempResultList = tempItemList.stream().map(tempItem -> {return tempItem.getId();}).collect(Collectors.toList());

        GeneralContentResult<List<String>> tempResult = new GeneralContentResult<>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        tempResult.setResultContent(tempResultList);
        return tempResult;
    }

    @Override
    @Transactional
    public GeneralResult deleteAssessSubAnswer(List<String> _answerItemId) {
        return null;
    }

    @Override
    public GeneralContentResult<List<String>> addMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer) {
        return null;
    }

    @Override
    public GeneralResult deleteMultiAnswerAssessAnswer(List<String> _answerId) {
        return null;
    }

    @Override
    public GeneralResult submitMultiAnswerAssessAnswer(String _userId, AssessAnswerReq _answer) {
        return null;
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
}
