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
public class User extends BaseEntity {
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "mobile")
    private String mobile;
    @Basic
    @Column(name = "user_code")
    private String userCode;
    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Basic
    @Column(name = "full_name")
    private String fullName;
    @Enumerated(EnumType.STRING)
    private UserGender gender;
    @Basic
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Basic
    @Column(name = "iamgeUrl")
    private String iamgeUrl;
    @Basic
    @Column(name = "person_in_charge")
    private String personInCharge;
    @Basic
    @Column(name = "change_password_token")
    private String changePasswordToken;
}
