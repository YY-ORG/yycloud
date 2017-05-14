package com.yy.cloud.common.data.otd.usermgmt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

import com.yy.cloud.common.data.otd.tenant.OrganizationItem;

@Data
@ApiModel
public class UserDetailsItem {

    private String userId;

    private String loginName;

    private String email;

    private String phone;

    private Boolean isAD;

    private String password;

    private String userName;

    private String leaderId;

    private String leaderName;

    private String organizationId;

    private String organizationName;

    private String enterpriseId;

    private String enterpriseName;

    private Byte enterpriseType;

    @ApiModelProperty(notes = "企业认证类型")
    private Byte enterpriseAuthMode;

    private String ldapId;

    private String ldapName;

    private Byte status;

    private String description;

    private List<OrganizationItem> organizations;

    private List<RoleItem> roles;

}
