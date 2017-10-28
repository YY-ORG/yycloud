package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * 菜单表
 * The persistent class for the YY_MENU database table.
 */
@Entity
@Table(name = "YY_MENU")
@NamedQuery(name = "YYMenu.findAll", query = "SELECT f FROM YYMenu f")
@Data
public class YYMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATE_BY")
    private String createBy;

    @Column(name = "ROUTING")
    private String routing;

    @Column(name = "I_CLASS")
    private String iClass;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "PERMISSION")
    private String permission;

    @Column(name = "TARGET")
    private String target;

    @Column(name = "STATUS")
    private Byte status;

    @Column(name = "TYPE")
    private Byte type;
    
    
    @Column(name = "ORDER_BY")
    private String orderBy;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;

}