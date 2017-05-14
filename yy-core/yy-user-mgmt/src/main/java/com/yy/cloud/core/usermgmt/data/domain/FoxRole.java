package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the FOX_ROLE database table.
 */
@Entity
@Data
@Table(name = "FOX_ROLE")
@NamedQuery(name = "FoxRole.findAll", query = "SELECT f FROM FoxRole f")
public class FoxRole implements Serializable {

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

    @Column(name = "TYPE")
    private Byte type;

    @Column(name = "CREATE_BY")
    private String createBy;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

}