package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/23/18 4:24 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class ApAssessScoringReq implements Serializable {
    private static final long serialVersionUID = 4696853643812644941L;
    private String assessId;
    private String apAssessId;
    private BigDecimal ratio = BigDecimal.ONE;
    private BigDecimal itemThreshold = BigDecimal.ZERO;
    private BigDecimal threshold = BigDecimal.ZERO;
}
