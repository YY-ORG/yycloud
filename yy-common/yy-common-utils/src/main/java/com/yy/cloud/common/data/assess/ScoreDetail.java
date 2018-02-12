package com.yy.cloud.common.data.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/5/18 8:05 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class ScoreDetail implements Serializable{
    private static final long serialVersionUID = -2320047130274449420L;
    private String assessAnswerItemId;
    private BigDecimal itemMarkedScore;
    private BigDecimal itemRealScore;
    private BigDecimal answerMarkedScore;
    private BigDecimal answerRealScore;
}
