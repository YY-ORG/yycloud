package com.yy.cloud.common.data.otd.usermgmt;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RoleItem implements Serializable {

	private static final long serialVersionUID = 1L;

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
