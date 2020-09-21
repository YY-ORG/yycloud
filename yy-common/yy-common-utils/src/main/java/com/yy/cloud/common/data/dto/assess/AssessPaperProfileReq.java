package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/9/17 5:45 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessPaperProfileReq implements Serializable {
    private static final long serialVersionUID = 6974505426039942226L;
    private String code;
    private String name;
    private Integer annual;
    private List<Byte> titleList;
    private List<String> orgIdList;
    private List<SimpleAssessReq> assessList;
}
