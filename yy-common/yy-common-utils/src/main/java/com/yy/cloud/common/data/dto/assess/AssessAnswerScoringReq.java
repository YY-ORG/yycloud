package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/2/18 8:48 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessAnswerScoringReq implements Serializable {
    private static final long serialVersionUID = -7050883675720975198L;
    private String assessAnswerId;
    private String assessAnswerItemId;
    private BigDecimal score;
    private String comments;
}
