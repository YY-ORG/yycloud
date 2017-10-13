package com.yy.cloud.common.data.dto.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 11:26 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessTemplateReq implements Serializable {
    private static final long serialVersionUID = 3190799465073233215L;
    private String templateId;
    private List<AssessTIItemReq> itemList;
}
