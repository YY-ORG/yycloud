package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     3/26/18 8:28 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimplePersonalScoreItem implements Serializable {
    private static final long serialVersionUID = 7302937662228435513L;
    private String userId;
    private String userName;
    private Byte title;
    private String deptName;
    private BigDecimal totalScore;
    private BigDecimal basicScore;
    private BigDecimal addonScore;
    private Byte level;
}
