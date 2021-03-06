package com.yy.cloud.common.data.dto.metadata;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/7/17 5:36 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class TemplateItemMapReq implements Serializable {
    private static final long serialVersionUID = -4487612730564845851L;
    private String templateItemId;
    private Boolean mandatory;
    private Boolean visible;
    private Boolean exVisible;
    private Boolean scVisible;
    private Boolean auVisible;
    private Boolean editable;
    private Boolean exEditable;
    private Boolean scEditable;
    private Boolean auEditable;
    private Integer seqNo;
    private String valueFrom;
    private String minValue;
    private String maxValue;
    private String failedMsg;
}
