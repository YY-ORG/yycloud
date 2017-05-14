package com.yy.cloud.common.data.otd.sysbase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用key value 参数类，应对hashmap 无法被jackson处理的问题。
 * Created by chenluo on 10/21/2016.
 */
@ApiModel
@Data
public class CommonKeyValue {

    private String key;
    private String value;
    @ApiModelProperty(notes = "在消费中心中，作为月份")
    private Integer num;
    @ApiModelProperty(notes = "在消费中心中，作为每个月的金额")
    private Double doubleValue;

    @ApiModelProperty(notes = "消费中心，按月统计的时候，标识年份")
    private Integer year;
}
