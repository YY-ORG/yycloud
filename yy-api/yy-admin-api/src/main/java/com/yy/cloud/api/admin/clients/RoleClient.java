package com.yy.cloud.api.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;

@FeignClient("usermgmt")
public interface RoleClient {

    @RequestMapping(value = "/authsec/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<RoleItem>> findRoles(
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size);

}
