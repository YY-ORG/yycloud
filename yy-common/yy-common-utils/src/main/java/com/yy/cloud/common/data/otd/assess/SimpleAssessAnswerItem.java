package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/30/17 10:38 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessAnswerItem implements Serializable {
    private static final long serialVersionUID = -6888585237314749671L;
    private String id;
    private String templateId;
    private String answerId;
    private Byte type;
    private Short seqNo;
    private List<SimpleAssessAnswerDetailItem> detailList;
}
