package com.yy.cloud.core.assess.data.domain;

import java.sql.Timestamp;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/15/18 11:19 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface IPerAssespaperPeriod {
    String getAssessPaperId();
    String getAssessPaperName();
    String getPeriodId();
    Timestamp getExStartTime();
    Timestamp getExEndTime();
    Timestamp getScStartTime();
    Timestamp getScEndTime();
    Timestamp getAuStartTime();
    Timestamp getAuEndTime();
}
