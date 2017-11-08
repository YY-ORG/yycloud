package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/3/17 8:53 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessProfileReq implements Serializable{
    private String name;
    private String code;
    private Byte type;

    private List<String> templateId;
}
