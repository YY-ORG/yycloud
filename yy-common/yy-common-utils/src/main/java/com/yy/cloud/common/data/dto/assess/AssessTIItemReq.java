package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 11:32 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessTIItemReq implements Serializable {
    private static final long serialVersionUID = -6305659455769463394L;
    private String id;
    private String code;
    private String name;
    private String value;
}
