package com.yy.cloud.common.data.otd.tenant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import com.yy.cloud.common.data.otd.usermgmt.UserItem;

/**
 * Created by chenluo on 10/24/2016.
 */
@Data
public class OrganizationItem implements Serializable {

    @ApiModelProperty(notes = "机构ID")
    private String id;

    @ApiModelProperty(notes = "机构名")
    private String name;


    @ApiModelProperty(notes = "机构状态")
    private Byte status;

    @ApiModelProperty(notes = "描述")
    private String description;
}
