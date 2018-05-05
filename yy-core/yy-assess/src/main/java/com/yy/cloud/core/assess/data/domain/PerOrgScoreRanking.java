package com.yy.cloud.core.assess.data.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     4/22/18 9:40 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Entity
@Table(name="per_org_score_ranking")
@NamedQuery(name="PerOrgScoreRanking.findAll", query="SELECT p FROM PerOrgScoreRanking p")
public class PerOrgScoreRanking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name="ID")
    private String id;

    @Column(name="ASSESSPAPER_ID")
    private String assessPaperId;

    @Column(name="ORG_ID")
    private String orgId;

    private int ranking;

    private BigDecimal score;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="CREATE_DATE", insertable = false, updatable = false)
    private Timestamp createDate;

    @Column(name="UPDATE_DATE", insertable = false, updatable = false)
    private Timestamp updateDate;

    public PerOrgScoreRanking() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAssessPaperId(String assessPaperId) {
        this.assessPaperId = assessPaperId;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getRanking() {
        return this.ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
