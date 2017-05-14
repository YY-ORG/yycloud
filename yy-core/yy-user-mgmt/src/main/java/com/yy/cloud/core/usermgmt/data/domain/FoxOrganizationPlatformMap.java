package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FOX_ORGANIZATION_PLATFORM_MAP")
@NamedQuery(name = "FoxOrganizationPlatformMap.findAll", query = "SELECT f FROM FoxOrganizationPlatformMap f")
@Data
public class FoxOrganizationPlatformMap implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @Column(name = "ORGANIZATION_ID")
    private String organizationId;

    @Column(name = "PLATFORM_ID")
    private String platformId;
}
