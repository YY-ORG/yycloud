package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     1/24/18 9:33 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class MarkedAssessAnswerItem extends SimpleAssessAnswerItem {
    private static final long serialVersionUID = -7659550556386376601L;
    private BigDecimal auxiliaryScore = BigDecimal.ZERO;
    private BigDecimal markedScore = BigDecimal.ZERO;
    private BigDecimal auditScore = BigDecimal.ZERO;
    private BigDecimal rMarkedScore = BigDecimal.ZERO;
    private BigDecimal rAuditScore = BigDecimal.ZERO;
    private String markedComment;
    private String auditComment;
}
