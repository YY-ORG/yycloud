package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the FOX_TENANT database table.
 */
@Entity
@Data
@Table(name = "FOX_TENANT")
@NamedQuery(name = "FoxTenant.findAll", query = "SELECT f FROM FoxTenant f")
@DynamicInsert
@DynamicUpdate
public class FoxTenant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "STATUS")
    private Byte status;

    @Column(name = "CURRENCY")
    private Byte currencyType;

    @Column(name = "CONTACTOR")
    private String contactor;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATE_DATE", updatable = false)
    private Timestamp createDate;

    @Column(name = "UPDATE_DATE", updatable = false)
    private Timestamp updateDate;

    @Column(name = "TYPE")
    private Byte type;

    @Column(name = "CREATOR_ID")
    private String creatorId;

    @Column(name = "AUTHMODE")
    private Byte authmode;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "SSL_ENABLE")
    private Byte sslEnable;

//    //bi-directional many-to-one association to FoxOrganization
//    @OneToMany(mappedBy = "foxTenant")
//    private List<FoxOrganization> foxOrganizations;
//
//    //bi-directional many-to-one association to FoxUser
//    @OneToMany(mappedBy = "foxTenant")
//    private List<FoxUser> foxUsers;
//
//    public FoxTenant() {
//    }
//
//    public FoxOrganization addFoxOrganization(FoxOrganization foxOrganization) {
//        getFoxOrganizations().add(foxOrganization);
//        foxOrganization.setFoxTenant(this);
//
//        return foxOrganization;
//    }
//
//    public FoxOrganization removeFoxOrganization(FoxOrganization foxOrganization) {
//        getFoxOrganizations().remove(foxOrganization);
//        foxOrganization.setFoxTenant(null);
//
//        return foxOrganization;
//    }
//
//    public FoxUser addFoxUser(FoxUser foxUser) {
//        getFoxUsers().add(foxUser);
//        foxUser.setFoxTenant(this);
//
//        return foxUser;
//    }
//
//    public FoxUser removeFoxUser(FoxUser foxUser) {
//        getFoxUsers().remove(foxUser);
//        foxUser.setFoxTenant(null);
//
//        return foxUser;
//    }

}