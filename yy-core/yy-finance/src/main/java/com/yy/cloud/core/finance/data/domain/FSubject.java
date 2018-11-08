package com.yy.cloud.core.finance.data.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     11/8/18 8:12 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Entity
@Table(name = "f_subject", schema = "yy", catalog = "")
public class FSubject {
    private String id;
    private String code;
    private String name;
    private Byte status;
    private String parentId;
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
    @Column(name = "CODE", nullable = true, length = 255)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "PARENT_ID", nullable = true, length = 36)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
        FSubject fSubject = (FSubject) o;
        return Objects.equals(id, fSubject.id) &&
                Objects.equals(code, fSubject.code) &&
                Objects.equals(name, fSubject.name) &&
                Objects.equals(status, fSubject.status) &&
                Objects.equals(parentId, fSubject.parentId) &&
                Objects.equals(creatorId, fSubject.creatorId) &&
                Objects.equals(createDate, fSubject.createDate) &&
                Objects.equals(updateDate, fSubject.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, status, parentId, creatorId, createDate, updateDate);
    }
}
