package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/7/17 4:11 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessItem implements Serializable {
    private String id;
    private String name;
    private String code;
    private Byte status;
    private Byte type;
}
