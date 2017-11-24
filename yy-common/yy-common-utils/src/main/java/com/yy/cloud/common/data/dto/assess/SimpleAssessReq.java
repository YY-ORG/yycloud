package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/9/17 5:52 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessReq implements Serializable {
    private static final long serialVersionUID = -4209340246280892382L;
    private String assessId;
    private String assessCategoryId;
    private Integer seqNo;
}
