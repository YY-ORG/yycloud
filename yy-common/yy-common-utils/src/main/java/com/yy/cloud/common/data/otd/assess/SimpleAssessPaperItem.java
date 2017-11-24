package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/9/17 5:57 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessPaperItem implements Serializable {
    private static final long serialVersionUID = 4987170565681525423L;
    private String id;
    private String code;
    private String name;
    private List<Byte> title;
    private List<String> orgId;
    private Byte status;
}
