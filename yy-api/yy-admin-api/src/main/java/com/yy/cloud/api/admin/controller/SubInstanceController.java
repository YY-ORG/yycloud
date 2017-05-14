package com.yy.cloud.api.admin.controller;

import com.yy.cloud.api.admin.service.SubInstanceService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.submgmt.SubQueryCondition;
import com.yy.cloud.common.data.otd.subinstance.SubInstanceDetailResp;
import com.yy.cloud.common.data.otd.submgmt.SubInstanceResp;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by chenluo on 2016/11/28.
 */
@RestController
@Slf4j
public class SubInstanceController {

    @Autowired
    private SubInstanceService subInstanceService;

    /**
     * 根据条件查询已购服务
     *
     * @param orderSearchCondtion
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/subinstance/subscription/list", method = RequestMethod.POST)
    @ApiOperation(value = "运营中心->已购服务管理，查询已购服务(已完成）")
    public GeneralPagingResult<List<SubInstanceResp>> searchOrder(
            @ApiParam("查询条件") @RequestBody SubQueryCondition orderSearchCondtion) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询订单-查询参数：{}", orderSearchCondtion);
        return subInstanceService.searchOrder(orderSearchCondtion);
    }

    /**
     * 运营中心->已购服务管理，已购服务详情
     *
     * @param subinstanceCode
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value = "/authsec/subinstance/{subinstanceCode}/detail", method = RequestMethod.POST)
    @ApiOperation(value = "运营中心->已购服务管理，已购服务详情(开发中)")
    public GeneralContentResult<SubInstanceDetailResp> getSubInstanceDetail(
            @ApiParam("订购实例编号") @PathVariable String subinstanceCode) {
        return subInstanceService.getSubInstanceDetail(subinstanceCode);
    }
}
