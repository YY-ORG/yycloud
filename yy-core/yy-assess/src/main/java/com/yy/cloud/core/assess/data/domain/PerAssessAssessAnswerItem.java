package com.yy.cloud.core.assess.data.domain;

import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/5/18 5:42 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface PerAssessAssessAnswerItem {
    String getAssessId();
    String getAssessName();
    String getAssessAnswerId();
    BigDecimal getScoringThreshold();
    BigDecimal getScoringRatio();
    BigDecimal getTotalScore();
    BigDecimal getRealScore();
    String getComment();
}
