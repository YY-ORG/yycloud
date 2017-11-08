package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.data.domain.YYRole;
import com.yy.cloud.core.usermgmt.data.domain.YYRoleMenu;
import com.yy.cloud.core.usermgmt.data.domain.YYUser;
import com.yy.cloud.core.usermgmt.data.repositories.YYRoleMenuRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYRoleRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYUserRoleRepository;
import com.yy.cloud.core.usermgmt.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private YYRoleRepository yyRoleRepository;

    @Autowired
    private YYUserRoleRepository yyUserRoleRepository;
    
    @Autowired
    private YYRoleMenuRepository yyRoleMenuRepository;

    @Autowired
    private YYUserRepository yyUserRepository;

    @Autowired
    private ModelMapper modelMapp;

    @Autowired
    private SecurityService securityService;

    @Override
    public String createRole(RoleProfile _roleProfile) {
        YYRole foxRole = modelMapp.map(_roleProfile, YYRole.class);
        foxRole.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        yyRoleRepository.save(foxRole);
        return foxRole.getId();
    }

    @Override
    public List<RoleItem> listRolesByPage(PageInfo _pageInfo) {
        PageRequest pageRequest = new PageRequest(_pageInfo.getCurrentPage(), _pageInfo.getPageSize());
        Page<YYRole> foxRoles = yyRoleRepository.findAll(pageRequest);

    	_pageInfo.setTotalPage(foxRoles.getTotalPages());
		_pageInfo.setTotalRecords(new Long(foxRoles.getTotalElements()).intValue());
        
        List<RoleItem> roleItems = new ArrayList<>();
        foxRoles.forEach(
                foxRole -> {
                    RoleItem roleItem = modelMapp.map(foxRole, RoleItem.class);
                    roleItem.setDescription(foxRole.getDescription());
                    roleItem.setId(foxRole.getId());
                    roleItems.add(roleItem);
                }
        );


        return roleItems;
    }


    @Override
    public GeneralContentResult<List<UserItem>> findMppUserByRoleList(String tenantId, List<String> roleNames) {
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询企业ID：{}下，所有该：{}角色成员",tenantId, roleNames);
        GeneralContentResult<List<UserItem>> generalContentResult = new GeneralContentResult<List<UserItem>>();
        generalContentResult.setResultCode(ResultCode.OPERATION_SUCCESS);
        List<YYUser> foxUsers =  new ArrayList<YYUser>();//ToDO
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
        List<YYUser> foxUsers = yyUserRepository.findAdmUserByRoleList(roleNames);
        List<UserItem> userItemList = new ArrayList<UserItem>();
        foxUsers.forEach(foxUser -> {
            UserItem userItem = modelMapp.map(foxUser, UserItem.class);
            userItemList.add(userItem);
        });
        generalContentResult.setResultContent(userItemList);
        log.debug(CommonConstant.LOG_DEBUG_TAG + "查询后台企业，所有该：{}角色的所有成员：{}", roleNames, generalContentResult);
        return generalContentResult;
    }

	@Override
	public GeneralResult roleAndMenuManage(RoleDetailsItem roleDetailsItem) {
		String _roleId=roleDetailsItem.getRoleId();
		List<MenuItem> menus=roleDetailsItem.getMenus();
		if (menus != null) {
			for (MenuItem menu : menus) {
				if(menu.isSelected()){
					saveRoleMenu(_roleId, menu);
				}else{
					
					deleteRoleMenu(_roleId, menu);
				}
				
				if (menu.getChildren() != null) {
					List<MenuItem> MenuItemchilds = menu.getChildren();
					if (MenuItemchilds != null) {
						for (MenuItem childMenu : MenuItemchilds) {
							if(childMenu.isSelected()){
								saveRoleMenu(_roleId, childMenu);
							}else{
								deleteRoleMenu(_roleId, childMenu);
							}
							
						}
					}
				}
			}
		}
		GeneralResult reslut=new GeneralResult();
		reslut.setResultCode(ResultCode.OPERATION_SUCCESS);
		reslut.setDetailDescription(ResultCode.OPERATION_SUCCESS);
		return reslut;
	}

	private void saveRoleMenu(String _roleId, MenuItem menu) {
		List<YYRoleMenu> foxRoleMenus = yyRoleMenuRepository.findByMenuIdAndRoleId(menu.getId(), _roleId);
		    if (foxRoleMenus.isEmpty()) {
		        YYRoleMenu foxRoleMenu = new YYRoleMenu();
		        foxRoleMenu.setMenuId(menu.getId());
		        foxRoleMenu.setRoleId(_roleId);
		        foxRoleMenu.setStatus(CommonConstant.DIC_GLOBAL_STATUS_ENABLE);
		        yyRoleMenuRepository.save(foxRoleMenu);
		    }
	}
	private void deleteRoleMenu(String _roleId, MenuItem menu) {
		List<YYRoleMenu> foxRoleMenus = yyRoleMenuRepository.findByMenuIdAndRoleId(menu.getId(), _roleId);
		    if (!foxRoleMenus.isEmpty()) {
		    	yyRoleMenuRepository.delete(foxRoleMenus);
		    }
	}
	
}
