package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FOX_USER")
@NamedQuery(name = "FoxUser.findAll", query = "SELECT f FROM FoxUser f")
@DynamicInsert
@DynamicUpdate
@Data
public class FoxUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "LOGIN_NAME")
    private String loginName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

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

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "IS_LEADER")
    private boolean isLeader;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private Byte type;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}