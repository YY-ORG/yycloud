package com.yy.cloud.core.usermgmt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.constant.ResultCode;
import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.dto.menu.MenuProfile;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleItem;
import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;
import com.yy.cloud.common.service.SecurityService;
import com.yy.cloud.core.usermgmt.data.domain.YYMenu;
import com.yy.cloud.core.usermgmt.data.domain.YYRole;
import com.yy.cloud.core.usermgmt.data.domain.YYRoleMenu;
import com.yy.cloud.core.usermgmt.data.repositories.YYMenuRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYRoleMenuRepository;
import com.yy.cloud.core.usermgmt.data.repositories.YYRoleRepository;
import com.yy.cloud.core.usermgmt.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private YYMenuRepository yyMenuRepository;

    @Autowired
    private YYRoleMenuRepository foxRoleMenuRepository;

    @Autowired
    private YYRoleRepository foxRoleRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    
    /**
	 * 用来获取当前登录用户信息
	 */
	@Autowired
	private SecurityService securityService;


    @Override
    public String createMenu(MenuProfile _menuProfile) {
        YYMenu foxMenu = new YYMenu();
        foxMenu.setName(_menuProfile.getName());
        foxMenu.setCode(_menuProfile.getCode());
        foxMenu.setRouting(_menuProfile.getRouting());
        foxMenu.setParentId(_menuProfile.getParentId());
        foxMenu.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        yyMenuRepository.save(foxMenu);
        return foxMenu.getId();
    }

    @Override
    public void addRole(String _menuId, String _roleId) {
        List<YYRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByMenuIdAndRoleId(_menuId, _roleId);
        if (foxRoleMenus.isEmpty()) {
            YYRoleMenu foxRoleMenu = new YYRoleMenu();
            foxRoleMenu.setMenuId(_menuId);
            foxRoleMenu.setRoleId(_roleId);
            foxRoleMenuRepository.save(foxRoleMenu);
        }
    }

    private List<MenuItem> generateMenuTree(List<YYMenu> foxMenus) {
        // 把全部FoxMenu转换成MenuItem
        List<MenuItem> menuItems = foxMenus.stream()
                .map(foxMenu -> modelMapper.map(foxMenu, MenuItem.class))
                .collect(Collectors.toList());

        // 获得顶层菜单
        List<MenuItem> topMenuItems = menuItems.stream()
                .filter(menuItem -> StringUtils.isBlank(menuItem.getParentId()))
                .collect(Collectors.toList());

        // 获得非顶层菜单
        List<MenuItem> nonTopMenuItems = menuItems.stream()
                .filter(menuItem -> !StringUtils.isBlank(menuItem.getParentId()))
                .collect(Collectors.toList());

        for (MenuItem menuItem : nonTopMenuItems) {
            for (MenuItem subMenuItem : nonTopMenuItems) {
                if (menuItem.getId().equals(subMenuItem.getParentId())) {
                    menuItem.addChildren(subMenuItem);
                }
            }
        }

        for (MenuItem topMenuItem : topMenuItems) {
            for (MenuItem menuItem : nonTopMenuItems) {
                if (topMenuItem.getId().equals(menuItem.getParentId())) {
                    topMenuItem.addChildren(menuItem);
                }
            }
        }

        return topMenuItems;
    }

    @Override
    public List<MenuItem> getAllMenuTree() {
        List<YYMenu> foxMenus = yyMenuRepository.findAll();
        return generateMenuTree(foxMenus);
    }

    @Override
    public RoleDetailsItem getMenuTreeByRoleId(String _roleId) {
        RoleDetailsItem roleDetailsItem = new RoleDetailsItem();

        List<YYRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByRoleId(_roleId);
        List<String> menuIds = foxRoleMenus.stream()
                .map(foxRoleMenu -> foxRoleMenu.getMenuId())
                .collect(Collectors.toList());

        List<YYMenu> foxMenus = yyMenuRepository.findByIdIn(menuIds);

        roleDetailsItem.setMenus(generateMenuTree(foxMenus));

        YYRole foxRole = foxRoleRepository.findOne(_roleId);
        roleDetailsItem.setRoleName(foxRole.getRoleName());
        roleDetailsItem.setRoleId(_roleId);

        return roleDetailsItem;
    }

    @Override
    public List<MenuItem> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles) {
    	UserDetailsItem tempUser = this.securityService.getCurrentUser();
    	List<RoleItem> roles= tempUser.getRoles();
    	boolean adminFlag=false;
		if (roles != null) {
			for (RoleItem roleItem : roles) {
				if ("系统管理员".equalsIgnoreCase(roleItem.getName())) {
					adminFlag = true;
				}
			}
		}
		if(adminFlag){
			List<YYMenu> foxMenus =yyMenuRepository.findAll();
			return generateMenuTree(foxMenus);
		}
    	
        List<String> roleIds = _roleProfiles.stream()
                .map(roleProfile -> roleProfile.getId())
                .distinct()
                .collect(Collectors.toList());

        List<YYRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByRoleIdIn(roleIds);
        List<String> menuIds = foxRoleMenus.stream()
                .map(foxRoleMenu -> foxRoleMenu.getMenuId())
                .distinct()
                .collect(Collectors.toList());

        List<YYMenu> foxMenus = yyMenuRepository.findByIdIn(menuIds);
        return generateMenuTree(foxMenus);
    }

	@Override
	public GeneralContentResult<RoleDetailsItem> getMenuTreeByRoleIdForEdit(String _roleId) {
		List<String> existMenuIds=new ArrayList<String>();
		 List<YYRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByRoleId(_roleId);
	        List<String> menuIds = foxRoleMenus.stream()
	                .map(foxRoleMenu -> foxRoleMenu.getMenuId())
	                .collect(Collectors.toList());
	        List<YYMenu> foxMenus = yyMenuRepository.findByIdIn(menuIds);
	        if(foxMenus!=null){
	        	for(YYMenu yyMenu :foxMenus){
	        		existMenuIds.add(yyMenu.getId());
	        	}
	        }
	        List<YYMenu> allMenus = yyMenuRepository.findAll();
	        List<MenuItem> menus=new ArrayList<MenuItem>();
	        if(allMenus!=null){
	        	menus=generateMenuTreeForMenuEdit(allMenus,existMenuIds );
	        }
		
	        GeneralContentResult<RoleDetailsItem> result= new GeneralContentResult<RoleDetailsItem>();
	        RoleDetailsItem roleDetailsItem=new RoleDetailsItem();
	        roleDetailsItem.setRoleId(_roleId);
	        roleDetailsItem.setMenus(menus);
	        result.setResultContent(roleDetailsItem);
	        result.setResultCode(ResultCode.OPERATION_SUCCESS);
	        result.setDetailDescription(ResultCode.OPERATION_SUCCESS);
		
		return result;
	}
	
	
	private List<MenuItem> generateMenuTreeForMenuEdit(List<YYMenu> foxMenus, List<String> menuids) {
        // 把全部FoxMenu转换成MenuItem
        List<MenuItem> menuItems = foxMenus.stream() 
                .map(foxMenu -> modelMapper.map(foxMenu, MenuItem.class))
                .collect(Collectors.toList());

        // 获得顶层菜单
        List<MenuItem> topMenuItems = menuItems.stream()
                .filter(menuItem -> StringUtils.isBlank(menuItem.getParentId()))
                .collect(Collectors.toList());

        // 获得非顶层菜单
        List<MenuItem> nonTopMenuItems = menuItems.stream()
                .filter(menuItem -> !StringUtils.isBlank(menuItem.getParentId()))
                .collect(Collectors.toList());

        for (MenuItem menuItem : nonTopMenuItems) {
            for (MenuItem subMenuItem : nonTopMenuItems) {
                if (menuItem.getId().equals(subMenuItem.getParentId())) {
                	
                	if(menuids.contains(subMenuItem.getId())){
                		subMenuItem.setSelected(true);
                	}
                    menuItem.addChildren(subMenuItem);
                }
            }
        }

        for (MenuItem topMenuItem : topMenuItems) {
            for (MenuItem menuItem : nonTopMenuItems) {
                if (topMenuItem.getId().equals(menuItem.getParentId())) {
                	if(menuids.contains(menuItem.getId())){
                		menuItem.setSelected(true);
                		topMenuItem.setSelected(true);
                	}
                    topMenuItem.addChildren(menuItem);
                }
            }
        }

        return topMenuItems;
    }
}
