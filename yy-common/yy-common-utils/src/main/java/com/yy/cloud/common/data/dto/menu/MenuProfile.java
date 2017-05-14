package com.yy.cloud.common.data.dto.menu;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuProfile implements Serializable {

    private String name;

    private String code;

    private String parentId;

    private String routing;

    private Byte status;

}
