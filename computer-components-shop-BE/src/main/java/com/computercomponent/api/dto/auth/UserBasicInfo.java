package com.computercomponent.api.dto.auth;

import com.computercomponent.api.common.UserGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicInfo {
    private String fullName;

    private String mobile;

    private String email;

    private UserGender gender;

    private String dateOfBirth;

    private String imageUrl;
}
