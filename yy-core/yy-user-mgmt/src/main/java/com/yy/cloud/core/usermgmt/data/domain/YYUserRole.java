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

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * 用户 角色
 * The persistent class for the YY_USER_ROLE database table.
 */
@Entity
@Table(name = "YY_USER_ROLE")
@NamedQuery(name = "YYUserRole.findAll", query = "SELECT f FROM YYUserRole f")
@Data
public class YYUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "STATUS")
    private Byte status;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}