package com.yy.cloud.core.assess.service.impl;

import com.yy.cloud.common.data.assess.ScoreDetail;
import com.yy.cloud.core.assess.service.ItemScoreCalculatorService;

import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/5/18 9:06 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public class GeneralScoreCalculatorServiceImpl implements ItemScoreCalculatorService {
    @Override
    public BigDecimal calculate(BigDecimal _score, BigDecimal _thershold) {
        return _score;
    }
}
