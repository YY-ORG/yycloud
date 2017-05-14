/**
 * Project Name:liz-enterprisemgmt
 * File Name:UserMgmtClient.java
 * Package Name:com.gemii.lizcloud.composite.enterprisemgmt.clients
 * Date:Oct 5, 201611:25:55 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.api.admin.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.yy.cloud.common.data.*;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseProfile;
import com.yy.cloud.common.data.otd.enterprise.CommonKeyValue;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseGeneralItem;
import com.yy.cloud.common.data.usermgmt.TenantModel;

import java.util.List;

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
     * createEnterprise:创建企业. <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/tenant", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    GeneralPagingResult<EnterpriseGeneralItem> createEnterprise(@RequestBody EnterpriseProfile _profile);

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
     * updateEnterpriseStatusByBatch:批量更新企业状态. <br/>
     *
     * @param _list   : 待更新状态企业IDs
     * @param _status
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/tenants/status/{_status}")
    @ResponseBody
    GeneralPagingResult<GeneralContent> updateEnterpriseStatusByBatch(@RequestBody List<EnterpriseProfile> _list,
                                                                      @PathVariable("_status") Byte _status);

    /**
     * getEnterpriseGeneralItemList: 分页获取企业基本信息列表. <br/>
     *
     * @param _request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/tenants/paging")
    @ResponseBody
    GeneralPagingResult<List<EnterpriseGeneralItem>> getEnterpriseGeneralItemList(@RequestBody PageParameter _request);

    /**
     * createEnterpriseAdmin: 创建企业管理员. <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/tenants/{_enterpriseId}/admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    GeneralPagingResult<EnterpriseAdminItem> createEnterpriseAdmin(@PathVariable("_enterpriseId") String _enterpriseId, @RequestBody EnterpriseAdminProfile _profile);

    /**
     * updateEnterpriseAdmin: 更新企业管理员. <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/tenant/admin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    GeneralPagingResult<GeneralContent> updateEnterpriseAdmin(@RequestBody EnterpriseAdminProfile _profile);

    @RequestMapping(method = RequestMethod.GET, value = "/tenants/admins/{_tenantId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    GeneralPagingResult<EnterpriseAdminItem> getEnterpriseAdminById(@PathVariable("_tenantId") String id);

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
     * updateEnterpriseAdminStatusByBatch: 批量更新企业管理员状态. <br/>
     *
     * @param _adminProfiles
     * @param _status
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/tenants/admins/status/{_status}")
    @ResponseBody
    GeneralPagingResult<GeneralContent> updateEnterpriseAdminStatusByBatch(@RequestBody List<EnterpriseAdminProfile> _adminProfiles,
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

    /**
     * deleteEnterpriseAdminByBatch:批量删除企业管理员. <br/>
     *
     * @param _adminProfiles
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/tenant/batch")
    GeneralPagingResult<GeneralContent> deleteEnterpriseAdminByBatch(@RequestBody List<EnterpriseAdminProfile> _adminProfiles);

    /**
     * getEnterpriseAdminList:分页获取所有企业所有管理员. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tenants/admins/{page}/{size}")
    @ResponseBody
    GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminList(@PathVariable("page") Integer page, @PathVariable("size") Integer size);

    /**
     * getEnterpriseAdminListByEnt:分页获取某个企业下的所有管理员. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tenant/{_tenantId}/admins/{page}/{size}")
    @ResponseBody
    GeneralPagingResult<List<EnterpriseAdminItem>> getEnterpriseAdminListByEnt(
            @PathVariable("_tenantId") String tenantId, @PathVariable("page") Integer page, @PathVariable("size") Integer size);


    /**
     * getAllEnterprise: 获取所有企业基本信息列表. <br/>
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tenants/simple")
    @ResponseBody
    GeneralPagingResult<List<EnterpriseGeneralItem>> getAllEnterprise();


    /**
     * 根据企业id 获取基本企业基本信息
     *
     * @param _enterpriseId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tenants/{tenant_id}/general")
    @ResponseBody
    GeneralContentResult<EnterpriseGeneralItem> getEnterpriseInfo(@PathVariable("tenant_id") String _enterpriseId);

    /**
     * updateEnterprise:更新企业信息，对于profile中是空的字段 <br/>
     *
     * @param _profile
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/tenants", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    GeneralResult updateEnterprise(
            @RequestBody TenantModel _profile);


    //############################### sprint3 core api


    /**
     * 审批中心-待审批列表，根据部门ID 获取用户,返回结果中，key 为userid，value 为用户名
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/authsec/approval/department/{departmentId}/users",method = RequestMethod.GET)
    GeneralContentResult<List<CommonKeyValue>> getUsersByDepartment(@PathVariable("departmentId") String departmentId);

    /**
     * 审批中心-待审批列表，获取审批人列表，key 为userid，value 为用户名
     * @return
     */
    @RequestMapping(value = "/authsec/approval/approvers/department/{departmentId}",method = RequestMethod.GET)
    GeneralContentResult<List<CommonKeyValue>> getApprovers(@PathVariable("departmentId") String departmentId);
    
    /**
     * getDiskServicePlatformDetailInfo:获取平台相关信息，为创建Disk产品目录做准备. <br/>
     *
     * @return
     */
    @RequestMapping(value="/tenants/{platform_id}", method = RequestMethod.GET)
    GeneralContentResult<List<EnterpriseProfile>> getEnterprisesByPlatformId(@PathVariable("platform_id") String _platformId);
}
