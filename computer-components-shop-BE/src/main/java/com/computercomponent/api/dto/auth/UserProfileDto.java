package com.computercomponent.api.dto.auth;

import com.computercomponent.api.common.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String userCode;
    private UserStatus status;
    private String fullName;
    private String personInCharge;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
}
