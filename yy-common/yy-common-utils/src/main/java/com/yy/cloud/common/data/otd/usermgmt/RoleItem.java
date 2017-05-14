package com.yy.cloud.common.data.otd.usermgmt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by guof on 2016/11/9.
 */
@Data
public class RoleItem implements Serializable {

    @ApiModelProperty(notes = "角色ID")
    private String id;

    @ApiModelProperty(notes = "角色名")
    private String name;

    @ApiModelProperty(notes = "角色权限名")
    private String roleName;

    @ApiModelProperty(notes = "状态")
    private Byte status;

    @ApiModelProperty(notes = "描述")
    private String description;

}
