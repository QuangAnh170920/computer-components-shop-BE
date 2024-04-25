package com.computercomponent.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    @NotNull
    private String mobileOrEmail;

    @NotNull
    private String password;
}
