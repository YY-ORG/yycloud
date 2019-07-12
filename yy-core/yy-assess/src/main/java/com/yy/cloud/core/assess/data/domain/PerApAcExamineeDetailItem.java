package com.yy.cloud.core.assess.data.domain;

import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     5/5/18 5:11 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface PerApAcExamineeDetailItem {
    String getApAcId();
    String getApAcName();
    BigDecimal getScoringRatio();
    BigDecimal getScoringThreshold();
    BigDecimal getMarkedScore();
    BigDecimal getAuditScore();
    BigDecimal getrMarkedScore();
    BigDecimal getrAuditScore();
    String getMarker();
    String getAuditor();
}
