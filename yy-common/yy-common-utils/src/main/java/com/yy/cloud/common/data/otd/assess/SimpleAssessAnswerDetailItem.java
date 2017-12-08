package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/30/17 10:41 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessAnswerDetailItem implements Serializable {
    private static final long serialVersionUID = -3337815696231161356L;
    private String id;
    private String itemCode;
    private String itemValue;
}
