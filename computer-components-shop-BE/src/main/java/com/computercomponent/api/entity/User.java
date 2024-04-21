package com.computercomponent.api.entity;

import com.computercomponent.api.common.UserGender;
import com.computercomponent.api.common.UserStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "password", nullable = true, length = 255)
    private String password;
    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Column(name = "mobile", nullable = true, length = 30)
    private String mobile;
    @Basic
    @Column(name = "create_at", nullable = true)
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
    @Column(name = "user_code", nullable = true, length = 255)
    private String userCode;
    @Basic
    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Basic
    @Column(name = "full_name", nullable = true, length = 255)
    private String fullName;
    @Enumerated(EnumType.STRING)
    private UserGender gender;
    @Basic
    @Column(name = "date_of_birth", nullable = true, length = 255)
    private String dateOfBirth;
    @Basic
    @Column(name = "iamgeUrl", nullable = true, length = 255)
    private String iamgeUrl;
    @Basic
    @Column(name = "person_in_charge", nullable = true, length = 255)
    private String personInCharge;
    @Basic
    @Column(name = "change_password_token", nullable = true, length = 255)
    private String changePasswordToken;
}
