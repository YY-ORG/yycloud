package com.yy.cloud.common.data.assess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/26/17 10:36 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class AssessGroupItem implements Serializable {
    private static final long serialVersionUID = 1392112604399014346L;
    private String id;
    private String code;
    private String name;
    private List<AssessMenuItem> assessItemList;
}
