package com.yy.cloud.core.usermgmt.data.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by guof on 2016/11/16.
 */
@Entity
@Table(name = "FOX_ORGANIZATION_RESOURCE_QUOTA")
@Data
public class FoxOrganizationResourceQuota {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name = "ORGANIZATION_ID")
    private String organizationId;

    @Column(name = "VCPU")
    private Integer vcpu;

    @Column(name = "MEM")
    private Integer mem;

    @Column(name = "PHYSICAL")
    private Integer physical;

    @Column(name = "DISK")
    private Integer disk;

    @Column(name = "STORAGE")
    private Integer storage;

    @Column(name = "SNAPSHOT")
    private Integer snapshot;

    @Column(name = "IMAGE")
    private Integer image;

    @Column(name = "IPADDRESS")
    private Integer ipaddress;

    @Column(name = "VM")
    private Integer vm;

    @Column(name = "NETWORK")
    private Integer network;

    @Column(name = "USED_CPU")
    private Integer usedCpu;

    @Column(name = "USED_MEM")
    private Integer usedMem;

    @Column(name = "USED_PHYSICAL")
    private Integer usedPhysical;

    @Column(name = "USED_DISK")
    private Integer usedDisk;

    @Column(name = "USED_STORAGE")
    private Integer usedStorage;

    @Column(name = "USED_SNAPSHOT")
    private Integer usedSnapshot;

    @Column(name = "USED_IMAGE")
    private Integer usedImage;

    @Column(name = "USED_IPADDRESS")
    private Integer usedIpaddress;

    @Column(name = "USED_VM")
    private Integer usedVm;

    @Column(name = "USED_NETWORK")
    private Integer usedNetwork;

}
