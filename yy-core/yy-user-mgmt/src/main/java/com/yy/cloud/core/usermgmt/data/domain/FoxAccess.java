package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the FOX_ACCESS database table.
 */
@Entity
@Data
@Table(name = "FOX_ACCESS")
@NamedQuery(name = "FoxAccess.findAll", query = "SELECT f FROM FoxAccess f")
public class FoxAccess implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRIORITY")
    private int priority;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "VALUE")
    private String value;

    //bi-directional many-to-one association to FoxPerm
    @ManyToOne
    @JoinColumn(name = "PERM_ID")
    private FoxPerm foxPerm;

    public FoxAccess() {
    }

}