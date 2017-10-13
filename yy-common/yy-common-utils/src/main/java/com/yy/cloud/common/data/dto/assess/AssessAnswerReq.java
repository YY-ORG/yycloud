package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 10:52 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessAnswerReq implements Serializable {
    private static final long serialVersionUID = 2956254657119482596L;

    private String assessPaperId;
    private String assessId;
    private List<AssessTemplateReq> answerList;
}
