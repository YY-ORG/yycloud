package com.yy.cloud.common.data.dto.metadata;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/3/17 9:36 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class TemplateItemProfileReq implements Serializable {
    private String name;
    private String label;
    private String code;
    private Byte type;
    private String defaultValue;
    private String valueOwner;
    private String valueField;
    private String placeHolder;
    private String tip;
}
