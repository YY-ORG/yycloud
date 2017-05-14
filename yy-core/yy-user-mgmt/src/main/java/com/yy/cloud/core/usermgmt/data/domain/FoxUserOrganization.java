package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the FOX_USER_ROLE database table.
 */
@Entity
@Table(name = "FOX_USER_ORGANIZATION")
@NamedQuery(name = "FoxUserOrganization.findAll", query = "SELECT f FROM FoxUserOrganization f")
@Data
public class FoxUserOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ORGANIZATION_ID")
    private String organizationId;

}