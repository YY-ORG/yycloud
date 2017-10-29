package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.dto.assess.AssessAnswerReq;
import com.yy.cloud.common.data.dto.assess.AssessTIItemReq;
import com.yy.cloud.common.data.dto.assess.AssessTemplateReq;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswer;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswerDetail;
import com.yy.cloud.core.assess.data.domain.PerAssessAnswerItem;
import com.yy.cloud.core.assess.data.repositories.PerAssessAnswerRepository;
import com.yy.cloud.core.assess.service.DoingAssessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public GeneralResult submitAssessAnswer(String _userId, AssessAnswerReq _answer) {
        PerAssessAnswer tempAnswer = new PerAssessAnswer();
        tempAnswer.setAssessId(_answer.getAssessId());
        tempAnswer.setAssessPaperId(_answer.getAssessPaperId());
        tempAnswer.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
        tempAnswer.setCreatorId(_userId);

        List<AssessTemplateReq> tempAnswerItemList = _answer.getAnswerList();

        tempAnswer.setPerAssessAnswerItems(new ArrayList<PerAssessAnswerItem>());
        for(AssessTemplateReq tempTemplateReq : tempAnswerItemList){
            PerAssessAnswerItem tempAnswerItem = new PerAssessAnswerItem();
            tempAnswerItem.setTemplateId(tempTemplateReq.getTemplateId());
            tempAnswerItem.setSeqNo(tempTemplateReq.getSeqNo());
            List<AssessTIItemReq> tempItemList = tempTemplateReq.getItemList();

            tempAnswerItem.setPerAssessAnswerDetails(new ArrayList<PerAssessAnswerDetail>());
            for(AssessTIItemReq tempTiItemReq : tempItemList){
                PerAssessAnswerDetail tempDetail = new PerAssessAnswerDetail();
                tempDetail.setItemCode(tempTiItemReq.getCode());
                tempDetail.setItemValue(tempTiItemReq.getValue());
                tempDetail.setItemId(tempTiItemReq.getId());

                log.debug("This time is going to add Assess Item Detail [{}].", tempDetail);
                tempAnswerItem.addPerAssessAnswerDetail(tempDetail);
//                detailList.add(tempDetail);
            }
            log.debug("This time is going to add Assess Item [{}].", tempAnswerItem);
            tempAnswer.addPerAssessAnswerItem(tempAnswerItem);
        }


        this.perAssessAnswerRepository.save(tempAnswer);
        GeneralResult tempResult = new GeneralResult();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        return tempResult;
    }
}
