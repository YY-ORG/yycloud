package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.constant.RoleTypeConstant;
import com.yy.cloud.core.usermgmt.data.domain.FoxRole;
import com.yy.cloud.core.usermgmt.data.domain.FoxUser;
import com.yy.cloud.core.usermgmt.data.repositories.FoxRoleRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxUserRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxUserRoleRepository;
import com.yy.cloud.core.usermgmt.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private FoxRoleRepository foxRoleRepository;

    @Autowired
    private FoxUserRoleRepository foxUserRoleRepository;

    @Autowired
    private FoxUserRepository foxUserRepository;

    @Autowired
    private ModelMapper modelMapp;

    @Autowired
    private SecurityService securityService;

    @Override
    public String createRole(RoleProfile _roleProfile) {
        FoxRole foxRole = modelMapp.map(_roleProfile, FoxRole.class);
        foxRole.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        foxRoleRepository.save(foxRole);
        return foxRole.getId();
    }

    @Override
    public List<RoleItem> listRolesByPage(PageInfo _pageInfo) {
        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize(), Sort.Direction.ASC, "id");
        //UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        //Page<FoxRole> foxRoles = foxRoleRepository.findAll(pageRequest);
        Byte type = isProviderUser()? RoleTypeConstant.ROLE_PROVIDER:RoleTypeConstant.ROLE_BUYER;
        List<FoxRole> foxRoles = foxRoleRepository.findByType(type);

        List<RoleItem> roleItems = new ArrayList<>();
        foxRoles.forEach(
                foxRole -> {
                    RoleItem roleItem = modelMapp.map(foxRole, RoleItem.class);
                    roleItem.setDescription(foxRole.getDescription());
                    roleItems.add(roleItem);
                }
        );

        //_pageInfo.setTotalPage(foxRoles.getTotalPages());
        //_pageInfo.setTotalRecords(new Long(foxRoles.getTotalElements()).intValue());

        return roleItems;
    }

    //是否后台用户
    private boolean isProviderUser(){
        UserDetailsItem userDetailsItem = securityService.getCurrentUser();
        log.debug(CommonConstant.LOG_DEBUG_TAG + "获取当前用户信息：{}", userDetailsItem);
        String tenantId = userDetailsItem.getEnterpriseId();
        Byte tenantType = userDetailsItem.getEnterpriseType();
        if(null == tenantId || null == tenantType || CommonConstant.DIC_TENANT_TYPE_PROVIDER.equals(tenantType)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public GeneralContentResult<List<UserItem>> findMppUserByRoleList(String tenantId, List<String> roleNames) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询企业ID：{}下，所有该：{}角色成员",tenantId, roleNames);
        GeneralContentResult<List<UserItem>> generalContentResult = new GeneralContentResult<List<UserItem>>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        List<FoxUser> foxUsers = foxUserRepository.findMppUserByRoleList(tenantId, roleNames);
        List<UserItem> userItemList = new ArrayList<UserItem>();
        foxUsers.forEach(foxUser -> {
            UserItem userItem = modelMapp.map(foxUser, UserItem.class);
            userItemList.add(userItem);
        });
        generalContentResult.setResultContent(userItemList);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询企业ID：{}下，所有该：{}角色的所有成员：{}",tenantId, roleNames, generalContentResult);
        return generalContentResult;
    }

    @Override
    public GeneralContentResult<List<UserItem>> findAdmUserByRoleList(List<String> roleNames) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询后台企业，所有该：{}角色成员", roleNames);
        GeneralContentResult<List<UserItem>> generalContentResult = new GeneralContentResult<List<UserItem>>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        List<FoxUser> foxUsers = foxUserRepository.findAdmUserByRoleList(roleNames);
        List<UserItem> userItemList = new ArrayList<UserItem>();
        foxUsers.forEach(foxUser -> {
            UserItem userItem = modelMapp.map(foxUser, UserItem.class);
            userItemList.add(userItem);
        });
        generalContentResult.setResultContent(userItemList);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询后台企业，所有该：{}角色的所有成员：{}", roleNames, generalContentResult);
        return generalContentResult;
    }
}
