package com.yy.cloud.common.data.otd.metadata;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/7/17 2:58 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleTemplateItem implements Serializable {
    private String id;
    private String name;
    private String label;
    private String code;
    private Byte type;
    private Byte status;
    private String defaultValue;
    private String valueOwner;
    private String valueField;
    private String placeholderTip;
    private String tip;
}
