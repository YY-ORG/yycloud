package com.yy.cloud.core.assess.data.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/7/18 4:40 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Entity
@Table(name = "per_assess_period", schema = "yy", catalog = "")
@NamedQuery(name="PerAssessPeriod.findAll", query="SELECT p FROM PerAssessPeriod p")
public class PerAssessPeriod {
    private String id;
    private String assessId;
    private Boolean type;
    private Timestamp doingStart;
    private Timestamp doingEnd;
    private Timestamp scStart;
    private Timestamp scEnd;
    private Timestamp auStart;
    private Timestamp auEnd;
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
    @Column(name = "ASSESS_ID", nullable = true, length = 36)
    public String getAssessId() {
        return assessId;
    }

    public void setAssessId(String assessId) {
        this.assessId = assessId;
    }

    @Basic
    @Column(name = "TYPE", nullable = true)
    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Basic
    @Column(name = "DOING_START", nullable = true)
    public Timestamp getDoingStart() {
        return doingStart;
    }

    public void setDoingStart(Timestamp doingStart) {
        this.doingStart = doingStart;
    }

    @Basic
    @Column(name = "DOING_END", nullable = true)
    public Timestamp getDoingEnd() {
        return doingEnd;
    }

    public void setDoingEnd(Timestamp doingEnd) {
        this.doingEnd = doingEnd;
    }

    @Basic
    @Column(name = "SC_START", nullable = true)
    public Timestamp getScStart() {
        return scStart;
    }

    public void setScStart(Timestamp scStart) {
        this.scStart = scStart;
    }

    @Basic
    @Column(name = "SC_END", nullable = true)
    public Timestamp getScEnd() {
        return scEnd;
    }

    public void setScEnd(Timestamp scEnd) {
        this.scEnd = scEnd;
    }

    @Basic
    @Column(name = "AU_START", nullable = true)
    public Timestamp getAuStart() {
        return auStart;
    }

    public void setAuStart(Timestamp auStart) {
        this.auStart = auStart;
    }

    @Basic
    @Column(name = "AU_END", nullable = true)
    public Timestamp getAuEnd() {
        return auEnd;
    }

    public void setAuEnd(Timestamp auEnd) {
        this.auEnd = auEnd;
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
        PerAssessPeriod that = (PerAssessPeriod) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(assessId, that.assessId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(doingStart, that.doingStart) &&
                Objects.equals(doingEnd, that.doingEnd) &&
                Objects.equals(scStart, that.scStart) &&
                Objects.equals(scEnd, that.scEnd) &&
                Objects.equals(auStart, that.auStart) &&
                Objects.equals(auEnd, that.auEnd) &&
                Objects.equals(creatorId, that.creatorId) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assessId, type, doingStart, doingEnd, scStart, scEnd, auStart, auEnd, creatorId, createDate, updateDate);
    }
}
