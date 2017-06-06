package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
 * 账号
 * @author luckey
 *
 */
@Entity
@Table(name = "YY_USER")
@NamedQuery(name = "YYUser.findAll", query = "SELECT f FROM YYUser f")
@DynamicInsert
@DynamicUpdate
@Data
public class YYUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String id;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CREATE_BY")
	private String createBy;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SEC_ANSWER")
	private String secAnswer;

	@Column(name = "SEC_QUE")
	private Byte secQue;

	@Column(name = "STATUS")
	private Byte status;

	@Column(name = "IS_LEADER")
	private boolean isLeader;

	@Column(name = "TITLE")
	private String title;

	
	/**
	 * 0：系统管理员账号  1：系统账号 2：个人账号
	 */
	@Column(name = "TYPE")
	private Byte type;
	
	/**
	 * 账号状态：未审核、审核通过、审核未通过、停用；参考字典
	 */
	@Column(name = "USER_STATUS")
	private Byte userStatus;

	
	/**
	 * 登录成功总次数次数
	 */
	@Column(name = "LOGINTIMES")
	private Byte loginTimes;
	
	
	
	@OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy = "user")
	private YYUserInfo userInfo;
	
	@Column(name = "LASTLOGINDATA", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginData;
	
	@Column(name = "CREATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "UPDATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

}