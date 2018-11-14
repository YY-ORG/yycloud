package com.yy.cloud.common.data.otd.finance;

import lombok.Data;

import java.util.ArrayList;
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
public class SubjectItemRes {
    private String id;
    private String code;
    private String name;
    private String parentId;
    private List<SubjectItemRes> subSubjects;

    public void addSubSubject(SubjectItemRes _subSubject){
        if(this.subSubjects == null) {
            this.subSubjects = new ArrayList<>();
        }
        this.subSubjects.add(_subSubject);
    }
}
