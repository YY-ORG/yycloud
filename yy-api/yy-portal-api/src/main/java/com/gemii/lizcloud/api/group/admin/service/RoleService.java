package com.gemii.lizcloud.api.group.admin.service;

import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.otd.role.RoleItem;

import java.util.List;

/**
 * Created by guof on 2016/11/10.
 */
public interface RoleService {

    GeneralPagingResult<List<RoleItem>> findRoles(Integer _page, Integer _size);

}
