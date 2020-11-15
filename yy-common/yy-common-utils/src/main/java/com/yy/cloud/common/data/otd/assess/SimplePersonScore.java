package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2019-05-30 22:16<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimplePersonScore implements Serializable {
    private String assessPaperId;
    private String assessPaperName;
    private String annualTitle;
    private BigDecimal totalScore;
    private List<SimplePersonalScoreDetail> detailList;
}
