package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/27/18 8:38 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimplePersonalAnswerScoreItem implements Serializable {
    private static final long serialVersionUID = -8658888193096121026L;
    private String assessId;
    private String assessName;
    private String categoryId;
    private String categoryName;
    private BigDecimal totalScore;
    private BigDecimal markedScore;
    private BigDecimal scoringRatio;
    private BigDecimal realScore;
    private String ownerId;
}
