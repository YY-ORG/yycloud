package com.yy.cloud.common.data.otd.usermgmt;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrganizationProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "部门ID")
    private String id;

    @ApiModelProperty(notes = "部门名称")
    private String name;

    @ApiModelProperty(notes = "部门负责人ID")
    private String leaderId;

    
    @ApiModelProperty(notes = "部门系列")
    private String serial;

    @ApiModelProperty(notes = "描述")
    private String description;

   

}
