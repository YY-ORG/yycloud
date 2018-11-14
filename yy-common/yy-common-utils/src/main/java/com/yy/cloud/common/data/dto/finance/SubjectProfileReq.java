package com.yy.cloud.common.data.dto.finance;

import lombok.Data;

import java.util.List;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/9/18 10:08 AM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class SubjectProfileReq {
    private String id;
    private String code;
    private String name;
    private String parentId;
    private List<SubjectProfileReq> subSubjects;
}
