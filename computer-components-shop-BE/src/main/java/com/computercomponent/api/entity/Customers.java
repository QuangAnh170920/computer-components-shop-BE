package com.computercomponent.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "customers")
public class Customers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id")
    private int customerId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone")
    private String phone;
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
