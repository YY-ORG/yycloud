package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/2/17 4:38 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessGroupAnswerItem implements Serializable {
    private static final long serialVersionUID = -6462991229450045809L;
    private String groupId;
    private String groupCode;
    private String groupName;
    private Integer unstartedCount = 0;
    private Integer doingCount = 0;
    private Integer doneCount = 0;
    private Integer markedCount = 0;
    private Integer auditedCount = 0;
    private Integer totalCount = 0;
}
