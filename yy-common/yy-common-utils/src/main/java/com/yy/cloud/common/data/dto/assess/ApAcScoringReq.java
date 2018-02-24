package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2/23/18 4:21 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class ApAcScoringReq implements Serializable {
    private static final long serialVersionUID = -8429465466165278941L;
    private String id;
    private String apacId;
    private BigDecimal ratio = BigDecimal.ONE;
    private BigDecimal threshold = BigDecimal.ZERO;
}
