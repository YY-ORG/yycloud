package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.UserMgmtClient;
import com.yy.cloud.api.admin.service.UserMgmtService;
import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.GeneralContentResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMgmtServiceImpl implements UserMgmtService {

    @Autowired
    private UserMgmtClient userMgmtClient;

    @Override
    public GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(String departmentId) {
        GeneralContentResult<List<CommonKeyValue>> res= userMgmtClient.getUsersByDepartment(departmentId);
        log.debug("根据部门ID 获取部门用户返回结果: {}"+res);
        return res;
    }

    @Override
    public GeneralContentResult<List<CommonKeyValue>> getApprovers(String departmentId) {
        GeneralContentResult<List<CommonKeyValue>> result= userMgmtClient.getApprovers(departmentId);
        log.debug(CommonConstant.LOG_DEBUG_TAG+"获取审批人列表，返回结果 {}",result);
        return result;
    }
}
