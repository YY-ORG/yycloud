package com.yy.cloud.common.data.dto.sysbase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class PasswordProfile implements Serializable {

    private String id;

    @ApiModelProperty(notes = "当前密码")
    private String password;

    @ApiModelProperty(notes = "新密码")
    private String newPassword;

}
