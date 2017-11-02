package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * 部门
 * @author luckey
 *
 */
@Entity
@Table(name = "YY_ORGANIZATION")
@NamedQuery(name = "YYOrganization.findAll", query = "SELECT f FROM YYOrganization f")
@DynamicInsert
@DynamicUpdate
@Data
public class YYOrganization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -164372400859604000L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "STATUS")
	private Byte status;
	
	@Column(name = "PARENT_ID")
	private String parentId;
	
	
	@Column(name = "LEADER_SHIP")
	private String leadeShip;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "UPDATE_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	


	

}