package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/26/18 8:34 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimplePersonalScoreDetail implements Serializable {
    private static final long serialVersionUID = -1624263329966389299L;
    private String categoryId;
    private String categoryName;
    private BigDecimal totalScore;
    private BigDecimal scoringRatio;
    private List<SimplePersonalAnswerScoreItem> itemList;
}
