package com.yy.cloud.common.data.dto.metadata;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/7/17 3:53 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class TemplateProfileReq implements Serializable {

    private static final long serialVersionUID = -6304318606769126684L;
    private String name;
    private String code;
    private Byte type;

    private List<TemplateItemMapReq> itemList;
}
