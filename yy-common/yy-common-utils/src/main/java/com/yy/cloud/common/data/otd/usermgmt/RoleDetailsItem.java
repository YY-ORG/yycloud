package com.yy.cloud.common.data.otd.usermgmt;

import lombok.Data;

import java.util.List;

import com.yy.cloud.common.data.otd.sysbase.MenuItem;

@Data
public class RoleDetailsItem {

    String roleName;
    
    String roleId;

    List<MenuItem> menus;

}
