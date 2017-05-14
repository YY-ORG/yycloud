/**
 * Project Name:liz-enterprisemgmt
 * File Name:UserMgmtClient.java
 * Package Name:com.gemii.lizcloud.composite.enterprisemgmt.clients
 * Date:Oct 5, 201611:25:55 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.clients;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.cloud.common.data.GeneralContent;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.GeneralResult;

/**
 * ClassName:UserMgmtClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Oct 5, 2016 11:25:55 AM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */

@FeignClient("usermgmt")
//@FeignClient(url = "http://localhost:9101", name = "usermgmt")
public interface UserMgmtClient {


    /**
     * updateEnterpriseStatus:更新企业状态. <br/>
     *
     * @param _tenantId
     * @param _status
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/tenant/{_tenantId}/status/{_status}")
    @ResponseBody
    GeneralPagingResult<GeneralContent> updateEnterpriseStatus(@PathVariable("_tenantId") String _tenantId,
                                                               @PathVariable("_status") Byte _status);




    /**
     * updateEnterpriseAdminStatus:更新企业管理员状态. <br/>
     *
     * @param _adminId
     * @param _status
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/tenant/admin/{_adminId}/status/{_status}")
    @ResponseBody
    GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatus(@PathVariable("_adminId") String _adminId,
                                                                    @PathVariable("_status") Byte _status);


    /**
     * deleteEnterpriseAdmin:删除某个企业管理员. <br/>
     *
     * @param _adminId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/tenant/admin/{_adminId}")
    @ResponseBody
    GeneralPagingResult<GeneralContent> deleteEnterpriseAdmin(@PathVariable("_adminId") String _adminId);



}
