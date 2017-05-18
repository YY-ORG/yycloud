package com.yy.cloud.common.data.otd.usermgmt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class OrganizationItem {

    @ApiModelProperty(notes = "部门ID")
    private String id;

    @ApiModelProperty(notes = "部门名称")
    private String organizitionName;
    
    @ApiModelProperty(notes = "部门描述")
    private String desc;

}
