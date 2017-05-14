package com.gemii.lizcloud.api.group.admin.clients;

import com.gemii.lizcloud.common.constant.ResultCode;
import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.RoleProfile;
import com.gemii.lizcloud.common.data.otd.menu.MenuItem;
import com.gemii.lizcloud.common.data.otd.role.RoleDetailsItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("usermgmt")
public interface MenuClient {

    @RequestMapping(value = "/authsec/menu/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<List<MenuItem>> getMenuTree();

    @RequestMapping(value = "/authsec/menu/tree/{role_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleId(
            @PathVariable("role_id") String _roleId);

    @RequestMapping(value = "/authsec/menu/tree/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<List<MenuItem>> getMenuTreeByRoleIds(
            @RequestBody List<RoleProfile> _roleProfiles);

}
