package com.gemii.lizcloud.api.group.admin.service;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.RoleProfile;
import com.gemii.lizcloud.common.data.otd.menu.MenuItem;
import com.gemii.lizcloud.common.data.otd.role.RoleDetailsItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by guof on 2016/11/15.
 */
public interface MenuService {

    GeneralContentResult<List<MenuItem>> getMenuTree();

    GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(String _roleId);

    GeneralContentResult<List<MenuItem>> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles);

}
