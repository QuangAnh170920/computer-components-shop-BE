package com.computercomponent.api.entity;

import com.computercomponent.api.common.UserStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "admin")
public class Admin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "admin_id", nullable = false)
    private Long adminId;
    @Basic
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic
    @Column(name = "create_at", nullable = false)
    private Date createAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Date updateAt;
    @Basic
    @Column(name = "create_by", nullable = true, length = 255)
    private String createBy;
    @Basic
    @Column(name = "update_by", nullable = true, length = 255)
    private String updateBy;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Integer deleted;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    private UserStatus status;
    @Basic
    @Column(name = "mobile", nullable = true, length = 30)
    private String mobile;


}
