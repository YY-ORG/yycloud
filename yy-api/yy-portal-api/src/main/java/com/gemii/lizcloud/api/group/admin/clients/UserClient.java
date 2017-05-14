package com.gemii.lizcloud.api.group.admin.clients;

import com.gemii.lizcloud.common.data.GeneralContentResult;
import com.gemii.lizcloud.common.data.GeneralPagingResult;
import com.gemii.lizcloud.common.data.GeneralResult;
import com.gemii.lizcloud.common.data.dto.accountcenter.PasswordProfile;
import com.gemii.lizcloud.common.data.dto.accountcenter.UserProfile;
import com.gemii.lizcloud.common.data.otd.user.UserDetailsItem;
import com.gemii.lizcloud.common.data.otd.user.UserItem;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("usermgmt")
//@FeignClient(url = "http://localhost:9101", name = "usermgmt")
public interface UserClient {

    @RequestMapping(value = "/authsec/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<String> createUser(@RequestBody UserProfile _userProfile);

    // --WXDOK
    @RequestMapping(value = "/authsec/admuser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<String> createAdmUser(@RequestBody UserProfile _userProfile);

    @RequestMapping(value = "/authsec/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<UserItem> findUserById(@PathVariable("user_id") String _userId);

    @RequestMapping(value = "/authsec/currentEnterpriseId", method = RequestMethod.GET)
    GeneralContentResult<String> getCurrentEnterpriseId();

    @RequestMapping(value = "/authsec/currentAdmEnterpriseId", method = RequestMethod.GET)
    GeneralContentResult<String> getCurrentAdmEnterpriseId();

    @RequestMapping(value = "/authsec/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<UserItem>> findUsers(
            @RequestParam(value = "status", required = false) Byte _status,
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size);

    @RequestMapping(value = "/authsec/user/{user_id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResult modifyUser(
            @PathVariable("user_id") String _userId,
            @RequestBody UserProfile _userProfile);

    @RequestMapping(value = "/authsec/user/{user_id}/status", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralResult updateStatus(
            @PathVariable("user_id") String _userId,
            @RequestParam("status") Byte _status);

    @RequestMapping(value = "/authsec/user/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<UserDetailsItem> findCurrentUser();

    @RequestMapping(value = "/authsec/user/password/modify", method = RequestMethod.PUT)
    GeneralResult modifyPassword(@RequestBody PasswordProfile _passwordProfile);

    @RequestMapping(value = "/authsec/admuser/password/modify", method = RequestMethod.PUT)
    GeneralResult modifyAdmPassword(@RequestBody PasswordProfile _passwordProfile);

    @RequestMapping(value = "/authsec/users/organization/{organization_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralContentResult<List<UserItem>> getMembersInOrganization(@PathVariable("organization_id") String _organizationId);

    @RequestMapping(value = "/authsec/users/nonorganization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<UserItem>> getNonOrganizationMembers(@RequestParam(value = "page") Integer _page,
                                                                  @RequestParam(value = "size") Integer _size);

    @RequestMapping(value = "/authsec/users/loadById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralContentResult<UserDetailsItem> loadUserById(@RequestParam("user_id") String _userId);

    @RequestMapping(value = "/authsec/users/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    GeneralPagingResult<List<UserItem>> findUsersByUserName(
            @RequestParam(value = "userName", required = false) String _userName,
            @RequestParam(value = "page") Integer _page,
            @RequestParam(value = "size") Integer _size);

    @RequestMapping(value = "/authsec/user/{_loginName}/validation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralContentResult<String> validateLoginName(
            @PathVariable("_loginName") String _loginName);

}
