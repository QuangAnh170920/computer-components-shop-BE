package com.computercomponent.api.entity;

import com.computercomponent.api.common.UserStatus;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "admin")
public class Admin extends BaseEntity {
    private String username;
    private String password;
    private String email;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    private String mobile;
    private String changePasswordToken;
    @Column(name = "admin_code")
    private String adminCode;
    private String fullName;

}
