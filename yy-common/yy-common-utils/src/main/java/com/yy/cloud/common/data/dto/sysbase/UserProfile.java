package com.yy.cloud.common.data.dto.sysbase;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserProfile implements Serializable {

    private String id;

    @ApiModelProperty(notes = "账号")
    private String loginName;

    @ApiModelProperty(notes = "姓名")
    private String userName;

    private String email;

    private String phone;

    @ApiModelProperty(notes = "绑定角色")
    private List<RoleProfile> roles;


    @ApiModelProperty(notes = "是否部门负责人")
    private Boolean isLeader;

    private String description;

    @ApiModelProperty(notes = "邦定企业ID")
    private String tenantId;

    private Byte authMode;
}
