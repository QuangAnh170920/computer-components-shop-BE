package com.computercomponent.api.dto.auth;

import com.computercomponent.api.common.UserGender;
import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.config.ConvertString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long id;

    @Convert(converter = ConvertString.class)
    private String userCode;

    private Integer personInCharge;

    private String imageUrl;
    @Convert(converter = ConvertString.class)
    private String fullName;
    @Convert(converter = ConvertString.class)
    private String email;
    @Convert(converter = ConvertString.class)
    private String mobile;
    @Convert(converter = ConvertString.class)
    private String username;

    private UserGender gender;

    private String dateOfBirth;

    private UserStatus status;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;

    private String updatedBy;

    private Integer userStatus;
}
