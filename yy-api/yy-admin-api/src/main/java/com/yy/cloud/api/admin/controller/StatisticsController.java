package com.yy.cloud.api.admin.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yy.cloud.common.data.otd.enterprise.EnterpriseStatisticInfoResp;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseStatisticQuotaResp;

/**
 * 用于发布统计报表类API
 * Created by chenluo on 10/26/2016.
 */
@RestController
@Slf4j
public class StatisticsController {

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value ="/authsec/enterprise/{enterpriseId}/statistics/mgmt",method = RequestMethod.GET)
    @ApiOperation(value="企业管理-查看企业，管理信息（订单与审批统计，工单统计，账户统计数据）（未完成）")
    public EnterpriseStatisticInfoResp getEnterpriseStatisticInfo(@ApiParam(value = "企业ID", required = true) @PathVariable("enterpriseId") String enterpriseId){
        //todo
        return  null;
    }

    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "Token", defaultValue = "bearer ")})
    @RequestMapping(value ="/authsec/enterprise/{enterpriseId}/statistics/quota",method = RequestMethod.GET)
    @ApiOperation(value="企业管理-查看企业，企业资源配额使用率统计（未完成）")
    public EnterpriseStatisticQuotaResp getEnterpriseStatisticQuota(@ApiParam(value = "企业ID", required = true) @PathVariable("enterpriseId") String enterpriseId){
        return  null;

        //todo 后端接口拆分
        //查询企业配额，镜像，虚拟机，物理机，储存，快照，镜像
        //试试查询企业创建的物理机数量，启动未启动状态
        //计算百分比
    }

}
