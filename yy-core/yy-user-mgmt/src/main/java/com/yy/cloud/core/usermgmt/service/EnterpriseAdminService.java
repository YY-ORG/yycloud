/**
 * Project Name:liz-sysbase
 * File Name:SysBaseService.java
 * Package Name:com.yy.cloud.core.sysbase.service
 * Date:Jul 11, 20164:14:50 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 */

package com.yy.cloud.core.usermgmt.service;

import com.yy.cloud.common.data.GeneralPagingResult;
import com.yy.cloud.common.data.dto.enterprise.EnterpriseAdminProfile;
import com.yy.cloud.common.data.otd.enterprise.EnterpriseAdminItem;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName:SysBaseService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Jul 11, 2016 4:14:50 PM <br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface EnterpriseAdminService extends Serializable {
//	public List<FoxMenu> getMenuList() throws RuntimeException;
//
//	public CurrentUser getUserDetailInfo(String _userName) throws RuntimeException;

    FoxUser createUser(EnterpriseAdminProfile adminProfile);

    void updateUser(EnterpriseAdminProfile adminProfile);

    FoxUser findUserById(String id);

    Page<FoxUser> listAllUsers(Integer page, Integer size);

    Page<FoxUser> listUsersByTenantId(Integer page, Integer size, String tenantId);

    void deleteUser(String id);

    void deleteUsersBatch(List<EnterpriseAdminProfile> adminProfiles);

    void updateUserStatus(String id, Byte status);

    void updateUsersStatusBatch(List<EnterpriseAdminProfile> adminProfiles, Byte status);

    GeneralPagingResult<EnterpriseAdminItem> findAdminByTenantId(String tenantId);
}
