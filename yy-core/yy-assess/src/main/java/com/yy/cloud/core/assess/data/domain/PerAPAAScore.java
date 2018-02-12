package com.yy.cloud.core.assess.data.domain;

import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/6/18 10:07 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface PerAPAAScore {
 //   String getAssessAnswerId();
    BigDecimal getMarkedScore();
    BigDecimal getAuditScore();
  //  BigDecimal getRMarkedScore();
  //  BigDecimal getRAuditScore();
}
