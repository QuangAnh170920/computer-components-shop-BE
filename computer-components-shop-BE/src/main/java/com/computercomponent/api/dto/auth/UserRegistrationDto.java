package com.computercomponent.api.dto.auth;

import com.computercomponent.api.config.ConvertString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotNull
    @Convert(converter = ConvertString.class)
    private String fullName;
    @NotNull
    @Convert(converter = ConvertString.class)
    private String userName;
    @Convert(converter = ConvertString.class)
    private String email;
    @NotNull
    @Convert(converter = ConvertString.class)
    private String mobile;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
}
