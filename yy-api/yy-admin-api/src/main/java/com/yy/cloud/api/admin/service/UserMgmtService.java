package com.yy.cloud.api.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.sysbase.CommonKeyValue;

/**
 * Created by chenluo on 11/7/2016.
 */
public interface UserMgmtService {


    /**
     * 审批中心-待审批列表，根据部门ID 获取用户,返回结果中，key 为userid，value 为用户名
     * @param departmentId
     * @return
     */
    public GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(@PathVariable("departmentId") String departmentId);

    /**
     * 审批中心-待审批列表，获取审批人列表，key 为userid，value 为用户名
     * @return
     */
    public GeneralContentResult<List<CommonKeyValue>> getApprovers(String departmentId);
}
