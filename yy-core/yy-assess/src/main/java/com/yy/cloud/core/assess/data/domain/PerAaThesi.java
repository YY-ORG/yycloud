package com.yy.cloud.core.assess.data.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the PER_AA_THESIS database table.
 * 
 */
@Entity
@Table(name="PER_AA_THESIS")
@NamedQuery(name="PerAaThesi.findAll", query="SELECT p FROM PerAaThesi p")
public class PerAaThesi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name="ID")
	private String id;

	@Column(name="CREATE_DATE", insertable = false, updatable = false)
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	@Column(name="ISSUE")
	private String issue;

	@Column(name="ISSUE_YEAR")
	private int issueYear;

	@Column(name="JOURNAL_ID")
	private String journalId;

	@Column(name="NAME")
	private String name;

	@Column(name="REPRINT_JOURNAL_ID")
	private String reprintJournalId;

	@Column(name="REPRINT_TYPE")
	private Byte reprintType;

	@Column(name="UPDATE_DATE", insertable = false, updatable = false)
	private Timestamp updateDate;

	public PerAaThesi() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getIssue() {
		return this.issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public int getIssueYear() {
		return this.issueYear;
	}

	public void setIssueYear(int issueYear) {
		this.issueYear = issueYear;
	}

	public String getJournalId() {
		return this.journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReprintJournalId() {
		return this.reprintJournalId;
	}

	public void setReprintJournalId(String reprintJournalId) {
		this.reprintJournalId = reprintJournalId;
	}

	public Byte getReprintType() {
		return this.reprintType;
	}

	public void setReprintType(Byte reprintType) {
		this.reprintType = reprintType;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}