package com.gemii.lizcloud.api.group.admin.clients;

import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.otd.role.RoleItem;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("usermgmt")
public interface RoleClient {

    @RequestMapping(value = "/authsec/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<RoleItem>> findRoles(
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size);

}
