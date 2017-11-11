package com.yy.cloud.common.data.otd.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 5:10 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SimpleAssessCategoryItem implements Serializable {
    private static final long serialVersionUID = -6057693652697323004L;
    private String id;
    private String code;
    private String name;
}
