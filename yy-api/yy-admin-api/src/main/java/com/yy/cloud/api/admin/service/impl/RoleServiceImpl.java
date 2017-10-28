package com.yy.cloud.api.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.api.admin.clients.RoleClient;
import com.yy.cloud.api.admin.service.RoleService;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.utils.PageUtils;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleClient roleClient;

    @Override
    public GeneralPagingResult<List<RoleItem>> findRoles(Integer _page, Integer _size) {
        return roleClient.findRoles(PageUtils.getPageNo(_page), _size);
    }

	@Override
	public GeneralResult roleAndMenuManage(RoleDetailsItem roleDetailsItem) {
		// TODO Auto-generated method stub
		return roleClient.roleAndMenuManage(roleDetailsItem);
	}

}
