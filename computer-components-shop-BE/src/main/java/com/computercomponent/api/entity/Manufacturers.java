package com.computercomponent.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "manufactures", schema = "qap_store", catalog = "")
public class Manufacturers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "manufacturer_id")
    private int manufacturerId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "website")
    private String website;
    @Basic
    @Column(name = "create_at")
    private Date createAt;
    @Basic
    @Column(name = "update_at")
    private Date updateAt;
    @Basic
    @Column(name = "create_by")
    private String createBy;
    @Basic
    @Column(name = "update_by")
    private String updateBy;
    @Basic
    @Column(name = "deleted")
    private Integer deleted;
}
