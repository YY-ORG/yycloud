package com.yy.cloud.common.data.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     9/30/17 6:03 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessMenuItem implements Serializable {
    private static final long serialVersionUID = -1534484637811067828L;
    private String assessId;
    private String assessCode;
    private String assessName;
    private Integer seqNo;
}
