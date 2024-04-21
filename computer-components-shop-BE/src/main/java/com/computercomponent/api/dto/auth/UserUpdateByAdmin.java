package com.computercomponent.api.dto.auth;

import com.computercomponent.api.common.UserGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateByAdmin {
    private String fullName;

    private Integer personInCharge;

    private String mobile;

    private String email;

    private UserGender gender;

    private String dateOfBirth;
}
