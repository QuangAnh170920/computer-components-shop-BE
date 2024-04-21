package com.computercomponent.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtpVerify {
    private String otp;
    private Integer retryCount;
}
