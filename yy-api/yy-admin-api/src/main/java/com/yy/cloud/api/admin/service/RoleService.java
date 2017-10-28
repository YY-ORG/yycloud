package com.yy.cloud.api.admin.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;

public interface RoleService {

    GeneralPagingResult<List<RoleItem>> findRoles(Integer _page, Integer _size);
    
    GeneralResult roleAndMenuManage( RoleDetailsItem roleDetailsItem);

}
