package com.yy.cloud.core.usermgmt.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.cloud.common.constant.CommonConstant;
import com.yy.cloud.common.data.dto.menu.MenuProfile;
import com.yy.cloud.common.data.dto.sysbase.RoleProfile;
import com.yy.cloud.common.data.otd.sysbase.MenuItem;
import com.yy.cloud.common.data.otd.usermgmt.RoleDetailsItem;
import com.yy.cloud.core.usermgmt.data.domain.FoxMenu;
import com.yy.cloud.core.usermgmt.data.domain.FoxRole;
import com.yy.cloud.core.usermgmt.data.domain.FoxRoleMenu;
import com.yy.cloud.core.usermgmt.data.repositories.FoxMenuRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxRoleMenuRepository;
import com.yy.cloud.core.usermgmt.data.repositories.FoxRoleRepository;
import com.yy.cloud.core.usermgmt.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private FoxMenuRepository foxMenuRepository;

    @Autowired
    private FoxRoleMenuRepository foxRoleMenuRepository;

    @Autowired
    private FoxRoleRepository foxRoleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createMenu(MenuProfile _menuProfile) {
        FoxMenu foxMenu = new FoxMenu();
        foxMenu.setName(_menuProfile.getName());
        foxMenu.setCode(_menuProfile.getCode());
        foxMenu.setRouting(_menuProfile.getRouting());
        foxMenu.setParentId(_menuProfile.getParentId());
        foxMenu.setStatus(CommonConstant.DIC_GLOBAL_STATUS_INITIAL);
        foxMenuRepository.save(foxMenu);
        return foxMenu.getId();
    }

    @Override
    public void addRole(String _menuId, String _roleId) {
        List<FoxRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByMenuIdAndRoleId(_menuId, _roleId);
        if (foxRoleMenus.isEmpty()) {
            FoxRoleMenu foxRoleMenu = new FoxRoleMenu();
            foxRoleMenu.setMenuId(_menuId);
            foxRoleMenu.setRoleId(_roleId);
            foxRoleMenuRepository.save(foxRoleMenu);
        }
    }

    private List<MenuItem> generateMenuTree(List<FoxMenu> foxMenus) {
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
        List<FoxMenu> foxMenus = foxMenuRepository.findAll();
        return generateMenuTree(foxMenus);
    }

    @Override
    public RoleDetailsItem getMenuTreeByRoleId(String _roleId) {
        RoleDetailsItem roleDetailsItem = new RoleDetailsItem();

        List<FoxRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByRoleId(_roleId);
        List<String> menuIds = foxRoleMenus.stream()
                .map(foxRoleMenu -> foxRoleMenu.getMenuId())
                .collect(Collectors.toList());

        List<FoxMenu> foxMenus = foxMenuRepository.findByIdIn(menuIds);

        roleDetailsItem.setMenus(generateMenuTree(foxMenus));

        FoxRole foxRole = foxRoleRepository.findOne(_roleId);
        roleDetailsItem.setRoleName(foxRole.getRoleName());

        return roleDetailsItem;
    }

    @Override
    public List<MenuItem> getMenuTreeByRoleIds(List<RoleProfile> _roleProfiles) {
        List<String> roleIds = _roleProfiles.stream()
                .map(roleProfile -> roleProfile.getId())
                .distinct()
                .collect(Collectors.toList());

        List<FoxRoleMenu> foxRoleMenus = foxRoleMenuRepository.findByRoleIdIn(roleIds);
        List<String> menuIds = foxRoleMenus.stream()
                .map(foxRoleMenu -> foxRoleMenu.getMenuId())
                .distinct()
                .collect(Collectors.toList());

        List<FoxMenu> foxMenus = foxMenuRepository.findByIdIn(menuIds);
        return generateMenuTree(foxMenus);
    }
}
