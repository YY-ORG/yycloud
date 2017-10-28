package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;

public interface RoleService {

    String createRole(RoleProfile _roleProfile);

    List<RoleItem> listRolesByPage(PageInfo _pageInfo);

    GeneralContentResult<List<UserItem>> findMppUserByRoleList(String tenantId, List<String> roleNames);

    GeneralContentResult<List<UserItem>> findAdmUserByRoleList(List<String> roleNames);
    
    
    
    
    public GeneralResult roleAndMenuManage(
            @RequestBody RoleDetailsItem roleDetailsItem);

}
