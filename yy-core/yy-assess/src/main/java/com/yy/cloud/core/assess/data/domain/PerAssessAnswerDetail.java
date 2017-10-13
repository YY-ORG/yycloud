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
@Table(name = "PER_ASSESS_ANSWER_DETAIL", schema = "yy", catalog = "")
public class PerAssessAnswerDetail {
    private String id;
    private String itemId;
    private String itemCode;
    private String itemValue;
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
    @Column(name = "ITEM_ID", nullable = true, length = 36)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "ITEM_CODE", nullable = true, length = 255)
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Basic
    @Column(name = "ITEM_VALUE", nullable = true, length = 500)
    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
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

        PerAssessAnswerDetail that = (PerAssessAnswerDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        if (itemCode != null ? !itemCode.equals(that.itemCode) : that.itemCode != null) return false;
        if (itemValue != null ? !itemValue.equals(that.itemValue) : that.itemValue != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (itemCode != null ? itemCode.hashCode() : 0);
        result = 31 * result + (itemValue != null ? itemValue.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}
