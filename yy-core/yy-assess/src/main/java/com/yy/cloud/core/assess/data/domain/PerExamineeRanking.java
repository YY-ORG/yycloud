package com.yy.cloud.core.assess.data.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     4/22/18 9:22 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Entity
@Table(name="per_examinee_ranking")
@NamedQuery(name="PerExamineeRanking.findAll", query="SELECT p FROM PerExamineeRanking p")
public class PerExamineeRanking implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name="ID")
    private String id;

    @Column(name="ASSESSPAPER_ID")
    private String assessPaperId;

    @Column(name="ASSESSPAPER_Name")
    private String assessPaperName;

    @Column(name="ORG_ID")
    private String orgId;

    @Column(name="ORG_NAME")
    private String orgName;

    @Column(name="TITLE")
    private Byte title;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="USER_NAME")
    private String userName;

    @Column(name="SCORE")
    private BigDecimal score;

    @Column(name="TOTAL_RANKING")
    private int totalRanking;

    @Column(name="ORG_RANKING")
    private int orgRanking;

    @Column(name="LEVEL")
    private Byte level;

    @Column(name="MAKER")
    private String maker;

    @Column(name="AUDITOR")
    private String auditor;

    @Column(name="CREATE_DATE", insertable = false, updatable = false)
    private Timestamp createDate;

    @Column(name="UPDATE_DATE", insertable = false, updatable = false)
    private Timestamp updateDate;
    public PerExamineeRanking() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssessPaperId() {
        return this.assessPaperId;
    }

    public void setAssessPaperId(String assessPaperId) {
        this.assessPaperId = assessPaperId;
    }

    public String getAssessPaperName() {
        return this.assessPaperName;
    }

    public void setAssessPaperName(String assessPaperName) {
        this.assessPaperName = assessPaperName;
    }

    public String getAuditor() {
        return this.auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public byte getLevel() {
        return this.level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getOrgRanking() {
        return this.orgRanking;
    }

    public void setOrgRanking(int orgRanking) {
        this.orgRanking = orgRanking;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public byte getTitle() {
        return this.title;
    }

    public void setTitle(byte title) {
        this.title = title;
    }

    public int getTotalRanking() {
        return this.totalRanking;
    }

    public void setTotalRanking(int totalRanking) {
        this.totalRanking = totalRanking;
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

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
