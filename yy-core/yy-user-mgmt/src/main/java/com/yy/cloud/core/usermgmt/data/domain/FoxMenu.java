package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the FOX_MENU database table.
 */
@Entity
@Table(name = "FOX_MENU")
@NamedQuery(name = "FoxMenu.findAll", query = "SELECT f FROM FoxMenu f")
@Data
public class FoxMenu implements Serializable {
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

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;

}