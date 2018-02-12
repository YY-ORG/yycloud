package com.yy.cloud.core.assess.service;

import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/5/18 8:56 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface ItemScoreCalculatorService {
    public BigDecimal calculate(BigDecimal _score, BigDecimal _thershold);
}
