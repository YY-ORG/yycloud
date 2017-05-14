package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the FOX_ROLE_MENU database table.
 */
@Entity
@Table(name = "FOX_ROLE_MENU")
@NamedQuery(name = "FoxRoleMenu.findAll", query = "SELECT f FROM FoxRoleMenu f")
@Data
public class FoxRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "ROLE_ID")
    private String roleId;

    @JoinColumn(name = "MENU_ID")
    private String menuId;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "STATUS")
    private Byte status;

    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;

    //bi-directional many-to-one association to FoxMenu


    //bi-directional many-to-one association to FoxRole

}