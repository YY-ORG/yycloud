package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.submgmt.SubQueryCondition;
import com.yy.cloud.common.data.otd.subinstance.SubInstanceDetailResp;
import com.yy.cloud.common.data.otd.submgmt.SubInstanceResp;

/**
 * Created by chenluo on 2016/11/28.
 */
public interface SubInstanceService {

    /**
     * 运营中心->订单管理，查询订单(开发中）
     * 根据条件查询订单
     * @param orderSearchCondtion
     * @return
     */
    GeneralPagingResult<List<SubInstanceResp>> searchOrder (SubQueryCondition orderSearchCondtion);

    /**
     * 运营中心->已购服务管理，已购服务详情
     *
     * @param subinstanceCode
     * @return
     */
    GeneralContentResult<SubInstanceDetailResp> getSubInstanceDetail(String subinstanceCode);
}
