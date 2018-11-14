package com.yy.cloud.core.finance.service.impl;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.finance.SubjectProfileReq;
import com.yy.cloud.common.data.otd.finance.SubjectItemRes;
import com.yy.cloud.core.finance.data.domain.FSubject;
import com.yy.cloud.core.finance.data.repositories.FSubjectRepository;
import com.yy.cloud.core.finance.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 5:15 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private FSubjectRepository fSubjectRepository;

    @Override
    public GeneralContentResult<List<SubjectItemRes>> createSubjects(String _creatorId, List<SubjectProfileReq> _req) {
        GeneralContentResult<List<SubjectItemRes>> tempResult = new GeneralContentResult<List<SubjectItemRes>>();
        tempResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        List<SubjectItemRes> tempResultContent = new ArrayList<SubjectItemRes>();
        if (_req == null || _req.size() == 0) {
            tempResult.setResultContent(tempResultContent);
            return tempResult;
        }
        for (SubjectProfileReq tempReq : _req) {
            FSubject tempSubject;
            if (StringUtils.isBlank(tempReq.getId())) {
                tempSubject = new FSubject();
            } else {
                tempSubject = this.fSubjectRepository.findOne(tempReq.getId());
            }
            tempSubject.setCode(tempReq.getCode());
            tempSubject.setName(tempReq.getName());
            tempSubject.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
            tempSubject = this.packSubjectTree(tempSubject, tempReq.getSubSubjects());
            if(StringUtils.isNotBlank(tempReq.getParentId())) {
                FSubject tempParentSubject = this.fSubjectRepository.findOne(tempReq.getParentId());
                tempSubject.setFSubject(tempParentSubject);
                tempParentSubject.addFSubject(tempSubject);
            }
            tempSubject = this.fSubjectRepository.save(tempSubject);

            SubjectItemRes tempSubRes = new SubjectItemRes();
            tempSubRes.setId(tempSubject.getId());
            tempSubRes.setCode(tempSubject.getCode());
            tempSubRes.setName(tempSubject.getName());
            tempSubRes.setParentId(tempSubject.getParentId());

            tempSubRes = this.packSubjectItemResTree(tempSubRes, tempSubject.getFSubjects());
            tempResultContent.add(tempSubRes);
        }

        tempResult.setResultContent(tempResultContent);
        return tempResult;
    }

    private SubjectItemRes packSubjectItemResTree(SubjectItemRes _parent, List<FSubject> _sub){
        if(_sub != null && _sub.size() > 0) {
            for(FSubject tempSubject : _sub) {
                SubjectItemRes tempSubRes = new SubjectItemRes();
                tempSubRes.setId(tempSubject.getId());
                tempSubRes.setCode(tempSubject.getCode());
                tempSubRes.setName(tempSubject.getName());
                tempSubRes.setParentId(_parent.getId());
                tempSubRes = this.packSubjectItemResTree(tempSubRes, tempSubject.getFSubjects());
                _parent.addSubSubject(tempSubRes);
            }
        }
        return _parent;
    }

    private FSubject packSubjectTree(FSubject _parent, List<SubjectProfileReq> _req) {
        if (_req != null && _req.size() > 0) {
            for (SubjectProfileReq tempItem : _req) {
                FSubject tempSubject;
                if (StringUtils.isBlank(tempItem.getId())) {
                    tempSubject = new FSubject();
                } else {
                    tempSubject = this.fSubjectRepository.findOne(tempItem.getId());
                }
                tempSubject.setId(tempItem.getId());
                tempSubject.setCode(tempItem.getCode());
                tempSubject.setName(tempItem.getName());
                tempSubject.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
                tempSubject.setFSubject(_parent);
                tempSubject = this.packSubjectTree(tempSubject, tempItem.getSubSubjects());
                if (_parent.getFSubjects() == null) {
                    _parent.setFSubjects(new ArrayList<>());
                }
                _parent.addFSubject(tempSubject);
            }
        }
        return _parent;
    }
}
