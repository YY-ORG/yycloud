package com.yy.cloud.common.data.dto.sysbase;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

    private String name;

    private String description;
    
    //是否选中
    private boolean selected;

}
