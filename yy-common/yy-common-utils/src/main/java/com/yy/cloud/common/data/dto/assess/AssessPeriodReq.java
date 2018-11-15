package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/15/18 11:55 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessPeriodReq {
    @NotNull
    private String assessPaperId;
    private String periodId;
    private Timestamp exStartTime;
    private Timestamp exEndTime;
    private Timestamp scStartTime;
    private Timestamp scEndTime;
    private Timestamp auStartTime;
    private Timestamp auEndTime;
}
