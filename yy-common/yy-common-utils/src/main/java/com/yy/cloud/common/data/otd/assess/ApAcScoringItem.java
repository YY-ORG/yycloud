package com.yy.cloud.common.data.otd.assess;

import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/23/18 4:50 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface ApAcScoringItem{
    String getId();
    String getApAcId();
    String getCode();
    String getName();
    default BigDecimal getRatio(){
        return BigDecimal.ONE;
    }
    default BigDecimal getThreshold(){
        return BigDecimal.ZERO;
    }
}
