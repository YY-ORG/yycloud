package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/11/17 5:01 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessCategoryReq implements Serializable {
    private static final long serialVersionUID = -4163051104843514492L;
    private String code;
    private String name;
}
