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
@Table(name = "PER_ASSESS_ANSWER", schema = "yy", catalog = "")
public class PerAssessAnswer {
    private String id;
    private String assessPaperId;
    private String assessId;
    private Byte status;
    private String creatorId;
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
    @Column(name = "ASSESS_PAPER_ID", nullable = true, length = 36)
    public String getAssessPaperId() {
        return assessPaperId;
    }

    public void setAssessPaperId(String assessPaperId) {
        this.assessPaperId = assessPaperId;
    }

    @Basic
    @Column(name = "ASSESS_ID", nullable = true, length = 36)
    public String getAssessId() {
        return assessId;
    }

    public void setAssessId(String assessId) {
        this.assessId = assessId;
    }

    @Basic
    @Column(name = "STATUS", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CREATOR_ID", nullable = true, length = 36)
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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

        PerAssessAnswer that = (PerAssessAnswer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (assessPaperId != null ? !assessPaperId.equals(that.assessPaperId) : that.assessPaperId != null)
            return false;
        if (assessId != null ? !assessId.equals(that.assessId) : that.assessId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (creatorId != null ? !creatorId.equals(that.creatorId) : that.creatorId != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (assessPaperId != null ? assessPaperId.hashCode() : 0);
        result = 31 * result + (assessId != null ? assessId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
