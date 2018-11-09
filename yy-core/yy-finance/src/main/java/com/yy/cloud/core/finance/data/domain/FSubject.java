package com.yy.cloud.core.finance.data.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the f_subject database table.
 * 
 */
@Entity
@Table(name="f_subject")
@NamedQuery(name="FSubject.findAll", query="SELECT f FROM FSubject f")
public class FSubject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String code;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="CREATOR_ID")
	private String creatorId;

	private String name;

	private byte status;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;

	//bi-directional many-to-one association to FSubject
	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private FSubject FSubject;

	//bi-directional many-to-one association to FSubject
	@OneToMany(mappedBy="FSubject")
	private List<FSubject> FSubjects;

	public FSubject() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public FSubject getFSubject() {
		return this.FSubject;
	}

	public void setFSubject(FSubject FSubject) {
		this.FSubject = FSubject;
	}

	public List<FSubject> getFSubjects() {
		return this.FSubjects;
	}

	public void setFSubjects(List<FSubject> FSubjects) {
		this.FSubjects = FSubjects;
	}

	public FSubject addFSubject(FSubject FSubject) {
		getFSubjects().add(FSubject);
		FSubject.setFSubject(this);

		return FSubject;
	}

	public FSubject removeFSubject(FSubject FSubject) {
		getFSubjects().remove(FSubject);
		FSubject.setFSubject(null);

		return FSubject;
	}

}