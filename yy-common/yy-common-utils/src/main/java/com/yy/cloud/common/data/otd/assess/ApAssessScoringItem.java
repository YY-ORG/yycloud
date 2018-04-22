package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/23/18 5:00 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
public interface ApAssessScoringItem {
    String getId();
    String getApAssessId();
    String getCode();
    String getName();
    Byte getType();
    Integer getSeqNo();
    BigDecimal getRatio();
    BigDecimal getItemThreshold();
    BigDecimal getThreshold();
}
