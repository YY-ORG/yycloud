package com.yy.cloud.core.usermgmt.data.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * The persistent class for the YYUserOrganization database table.
 */
@Entity
@Table(name = "YY_USER_ORGANIZATION")
@NamedQuery(name = "YYUserOrganization.findAll", query = "SELECT f FROM YYUserOrganization f")
@Data
public class YYUserOrganization implements Serializable {
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