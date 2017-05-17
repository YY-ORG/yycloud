package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 角色表
 * The persistent class for the YYRole database table.
 */
@Entity
@Data
@Table(name = "YY_ROLE")
@NamedQuery(name = "YYRole.findAll", query = "SELECT f FROM YYRole f")
public class YYRole implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1843317481975477267L;

	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "STATUS")
    private Byte status;

    @Column(name = "DESCRIPTION")
    private String description;

    
    /**
     * 内置两大类角色 0：考核类角色  1：查阅类角色
     */
    @Column(name = "TYPE")
    private Byte type;

    
    
    /**
     * --科研 0、社科 1、图资 2, 
     */
    @Column(name = "CATEGORY")
    private Byte category;

    
    
    @Column(name = "CREATE_BY")
    private String createBy;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}