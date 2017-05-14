package com.yy.cloud.api.admin.service.impl;

import com.yy.cloud.api.admin.clients.SubInstanceMgmtClient;
import com.yy.cloud.api.admin.service.SubInstanceService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.submgmt.SubQueryCondition;
import com.yy.cloud.common.data.otd.subinstance.SubInstanceDetailResp;
import com.yy.cloud.common.data.otd.submgmt.SubInstanceResp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenluo on 2016/11/28.
 */
@Service
@Slf4j
public class SubInstanceServiceImpl implements SubInstanceService {

    @Autowired
    private SubInstanceMgmtClient subInstanceMgmtClient;

    @Override
    public GeneralPagingResult<List<SubInstanceResp>> searchOrder(SubQueryCondition orderSearchCondtion) {
        GeneralPagingResult<List<SubInstanceResp>> result=subInstanceMgmtClient.searchSubInstance(orderSearchCondtion);
        log.debug("查询订单返回结果: {}"+result);
        return result;
    }

    @Override
    public GeneralContentResult<SubInstanceDetailResp> getSubInstanceDetail(String subinstanceCode) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取订购实例编号 {} 的详情", subinstanceCode);
        GeneralContentResult<SubInstanceDetailResp> result = subInstanceMgmtClient.getSubInstanceDetail(subinstanceCode);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取订购实例编号 {} 的详情返回结果为 {}", subinstanceCode,result);
        return result;
    }
}
