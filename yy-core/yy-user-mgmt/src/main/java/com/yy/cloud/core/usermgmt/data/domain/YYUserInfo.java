package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 用户 详情
 * @author luckey
 *
 */

@Entity
@Table(name = "YY_USER_INFO")
@NamedQuery(name = "YYUserInfo.findAll", query = "SELECT f FROM YYUserInfo f")
@DynamicInsert
@DynamicUpdate
@Data
public class YYUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String id;

	@Column(name = "USER_ID")
	private String userId ;

	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "EMAIL")
	private String email;

	
	/**
	 * 性别
	 * 0：男
	 * 1：女
	 */
	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PHONE")
	private String phone;

	
	
	@Column(name = "TITLE")
	private String title;
	
	
	@Column(name = "BIRTHDAY")
	private String birthiday;
	
	
	@Column(name = "ADDRESS")
	private String address;
	
	/**
	 * 岗位类型:参考数据字典
	 */
	@Column(name = "OCCUPATION_TYPE")
	private byte occupationType;
	
	
	/**
	 * 职称:参考数据字典
	 */
	@Column(name = "PROFESSIONAL_TITLE")
	private byte professionalTitle;
	
	/**
	 * 行政级别:参考数据字典
	 */
	@Column(name = "ADMINISTRATIVE_RANK")
	private byte administrativeRank;
	
	/**
	 * 行政职务: 参考数据字典
	 */
	@Column(name = "ADMINISTRATIVE_POST")
	private byte administrativePost;
	
	
	/**
	 * 部门Id
	 */
	@Column(name = "DEPT_ID")
	private String deptId;
	
	
	@Column(name = "CREATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createDate;

	@Column(name = "UPDATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date updateDate;

}