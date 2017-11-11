package com.yy.cloud.common.data.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 4:54 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class ApAcMapItem implements Serializable {
    private static final long serialVersionUID = -6688029972512530583L;
    private String id;
    private String assessPaperId;
    private String assessCategoryId;
    private Byte status;
}
