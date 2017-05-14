package com.yy.cloud.common.data.otd.usermgmt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wanxiaod on 12/16/2016.
 * 该实体用户校验登录是前台(0)还是后台(1)
 */
@Data
@ApiModel
public class FoxUserItem {
    @ApiModelProperty(notes = "登录名")
    private String loginName;
    @ApiModelProperty(notes = "用户类型，0:前台；1:后台;2:用户名不存在")
    private Byte userType;
    @ApiModelProperty(notes = "密码，加密后密文")
    private String password;
}
