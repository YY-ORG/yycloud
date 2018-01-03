package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.clients.SecurityClient;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.assess.AssessPaperExamineeMapItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.utils.YYException;
import com.yy.cloud.core.assess.data.domain.PerAssessPaper;
import com.yy.cloud.core.assess.data.domain.PerAssessPaperExamineeMap;
import com.yy.cloud.core.assess.data.repositories.PerAssessPaperExamineeMapRepository;
import com.yy.cloud.core.assess.data.repositories.PerAssessPaperRepository;
import com.yy.cloud.core.assess.service.MarkedScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static Map<String, PerAssessPaper> assessPaperMap;
    @Override
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnMarkedAssessPaperListByOrg(String _orgId, Pageable _page) throws YYException {
        List<Byte> statusList = new ArrayList<>();
        statusList.add(CommonConstant.DIC_ASSESS_ANSWER_STATUS_DONE);
        statusList.add(CommonConstant.DIC_ASSESS_ANSWER_STATUS_MARKED);
        return this.getAssessPaperListByOrg(_orgId, statusList, _page);
    }

    @Override
    public GeneralPagingResult<List<AssessPaperExamineeMapItem>> getUnAuditedAssessPaperListByOrg(String _orgId, Pageable _page) throws YYException {
        List<Byte> statusList = new ArrayList<>();
        statusList.add(CommonConstant.DIC_ASSESS_ANSWER_STATUS_AUDITED);
        statusList.add(CommonConstant.DIC_ASSESS_ANSWER_STATUS_MARKED);
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
            return tempResult;
        }
        GeneralContentResult<Map<String, UserDetailsItem>> tempUserMapResult = this.securityClient.getAllMembersInOrganization(_orgId);

        if(tempUserMapResult.getResultCode().equals(ResultCode.OPERATION_SUCCESS)){
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
}
