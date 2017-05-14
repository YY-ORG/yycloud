package com.yy.cloud.core.usermgmt.service;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.accountcenter.RoleProfile;
import com.yy.cloud.common.data.otd.role.RoleItem;
import com.yy.cloud.common.data.otd.user.UserItem;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;

import java.util.List;

public interface RoleService {

    String createRole(RoleProfile _roleProfile);

    List<RoleItem> listRolesByPage(PageInfo _pageInfo);

    GeneralContentResult<List<UserItem>> findMppUserByRoleList(String tenantId, List<String> roleNames);

    GeneralContentResult<List<UserItem>> findAdmUserByRoleList(List<String> roleNames);

}
