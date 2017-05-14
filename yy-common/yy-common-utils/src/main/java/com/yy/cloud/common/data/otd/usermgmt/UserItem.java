package com.yy.cloud.common.data.otd.usermgmt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guof on 2016/11/9.
 */
@Data
public class UserItem {

    @ApiModelProperty(notes = "角色ID")
    private String id;

    @ApiModelProperty(notes = "用户名")
    private String userName;

    @ApiModelProperty(notes = "账号")
    private String loginName;

    @ApiModelProperty(notes = "邮件")
    private String email;

    @ApiModelProperty(notes = "联系电话")
    private String phone;

    @ApiModelProperty(notes = "描述")
    private String description;
    
    @ApiModelProperty(notes = "是否 AD 用户,0 不是，1 是，注意是数字，不是字符")
    private Integer type;

    @ApiModelProperty(notes = "用户状态")
    private Byte status;

    @ApiModelProperty(notes = "部门名/机构名")
    private String organizationName;

    @ApiModelProperty(notes = "角色名")
    private String roleName;

}
