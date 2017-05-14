package com.gemii.lizcloud.api.group.admin.service.impl;

import com.gemii.lizcloud.api.group.admin.clients.RoleClient;
import com.gemii.lizcloud.api.group.admin.service.RoleService;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.otd.role.RoleItem;
import com.gemii.lizcloud.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleClient roleClient;

    @Override
    public GeneralPagingResult<List<RoleItem>> findRoles(Integer _page, Integer _size) {
        return roleClient.findRoles(PageUtils.getPageNo(_page), _size);
    }

}
