package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/28/17 8:39 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessPaperExamineeMapItem implements Serializable {
    private static final long serialVersionUID = 228037165958467588L;
    private String id;
    private String userId;
    private String userName;
    private Byte title;
    private Byte status;
    private String orgId;
    private String orgName;
    private String assessPaperId;
    private String assessPaperName;
    private BigDecimal markedScore;
    private BigDecimal auditScore;
}
