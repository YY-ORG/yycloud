package com.yy.cloud.api.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;

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
