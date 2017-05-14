package com.yy.cloud.common.data.dto.sysbase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleProfile implements Serializable {

    private String id;

    private String name;

    private String description;

}
