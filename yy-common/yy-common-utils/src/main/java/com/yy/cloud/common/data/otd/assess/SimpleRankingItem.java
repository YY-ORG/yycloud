package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     4/22/18 8:37 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleRankingItem implements Serializable{
    private static final long serialVersionUID = -4302430759484812223L;
    private String id;
    private String userId;
    private String userName;
    private String orgId;
    private String orgName;
    private Byte title;
    private BigDecimal totalScore;
    private Integer totalRank;
    private Integer orgRank;
    private Byte rankLevel;
}
