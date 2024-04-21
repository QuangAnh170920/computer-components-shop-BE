package com.computercomponent.api.response;

import com.computercomponent.api.common.UserGender;
import lombok.Data;

@Data
public class AdminInfoResponse {
    private Long id;
    private String fullName;

    private String dateOfBirth;

    private UserGender gender;

    private String email;

    private String mobile;
}
