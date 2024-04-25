package com.computercomponent.api.entity;

import com.computercomponent.api.common.UserStatus;
import lombok.Data;
import org.hibernate.annotations.Type;

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
    private String username;
    private String password;
    private String email;
    private Date createAt;
    private Date updateAt;
    private String createBy;
    private String updateBy;
    @Column(columnDefinition = "TINYINT", name = "deleted")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted = false;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    private UserStatus status;
    private String mobile;
    private String changePasswordToken;
    @Column(name = "admin_code")
    private String adminCode;


}
