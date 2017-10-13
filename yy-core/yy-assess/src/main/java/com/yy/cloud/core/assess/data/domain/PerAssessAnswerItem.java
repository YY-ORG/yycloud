package com.yy.cloud.core.assess.data.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     10/13/17 5:11 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Entity
@Table(name = "PER_ASSESS_ANSWER_ITEM", schema = "yy", catalog = "")
public class PerAssessAnswerItem {
    private String id;
    private String templateId;
    private Short seqNo;
    private Timestamp createDate;
    private Timestamp updateDate;

    @Id
    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TEMPLATE_ID", nullable = true, length = 36)
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Basic
    @Column(name = "SEQ_NO", nullable = true)
    public Short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Short seqNo) {
        this.seqNo = seqNo;
    }

    @Basic
    @Column(name = "CREATE_DATE", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "UPDATE_DATE", nullable = true)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerAssessAnswerItem that = (PerAssessAnswerItem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;
        if (seqNo != null ? !seqNo.equals(that.seqNo) : that.seqNo != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        result = 31 * result + (seqNo != null ? seqNo.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
