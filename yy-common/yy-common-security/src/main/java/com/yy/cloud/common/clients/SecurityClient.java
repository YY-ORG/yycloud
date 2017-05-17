package com.yy.cloud.common.clients;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.otd.usermgmt.AdLoginReq;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;

//@FeignClient("user-mgmt")
@FeignClient(url = "http://localhost:9101", name = "user-mgmt")
public interface SecurityClient {

    @RequestMapping(value = "/authsec/users/loadByLoginName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GeneralContentResult<UserDetailsItem> loadUserByLoginName(@RequestParam("login_name") String _loginName);

    @RequestMapping(value = "/authsec/users/loadById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GeneralContentResult<UserDetailsItem> loadUserById(@RequestParam("user_id") String _id);

    @RequestMapping(value = "/noauth/user/loginnameorid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GeneralContentResult<UserDetailsItem> loadUserByUsernameOrId(@RequestParam("_loginNameOrId") String _loginNameOrId);

    @ResponseBody
    @RequestMapping(value = "/noauth/aduser/login", method = RequestMethod.POST)
    public GeneralContentResult<String> loginAdUser(@RequestBody AdLoginReq loginReq);

}
