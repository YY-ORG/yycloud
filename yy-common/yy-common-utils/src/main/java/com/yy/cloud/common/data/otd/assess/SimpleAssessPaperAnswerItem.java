package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/2/17 5:03 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessPaperAnswerItem implements Serializable {
    private static final long serialVersionUID = 8670072251606796642L;
    private String id;
    private String code;
    private String name;
    private Byte status;
    private Integer unstartedCount = 0;
    private Integer doingCount = 0;
    private Integer doneCount = 0;
    private Integer markedCount = 0;
    private Integer auditedCount = 0;
    private Integer totalCount = 0;
    private List<SimpleAssessGroupAnswerItem> groupAnswerItemList;
}
