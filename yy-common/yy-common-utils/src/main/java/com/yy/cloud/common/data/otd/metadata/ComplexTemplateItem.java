package com.yy.cloud.common.data.otd.metadata;

import lombok.Data;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/13/17 4:05 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class ComplexTemplateItem extends SimpleTemplateItem {
    private static final long serialVersionUID = 8595475516065635684L;
    private Boolean mandatory;
    private Boolean editable;
    private Boolean visible;
    private Integer seqNo;
    private String reliedId;
}
