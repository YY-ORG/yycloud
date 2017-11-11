package com.yy.cloud.common.data.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 4:52 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessCategoryItem implements Serializable {
    private static final long serialVersionUID = -1467564644645604338L;
    private String id;
    private String code;
    private String name;
    private Byte status;
    private String creatorId;
}
