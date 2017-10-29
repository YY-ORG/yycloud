package com.yy.cloud.common.data.otd.sysbase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuItem {

    private String id;

    private String name;

    private String code;

    private String routing;
    
    //图标
    private String iClass;
    
    
    //菜单排序
    private String orderBy;
    
    private boolean selected;

    private Byte status;

    @JsonIgnore
    private String parentId;

    List<MenuItem> children;

    public void addChildren(MenuItem menuItem) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(menuItem);
    }

}
