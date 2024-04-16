package com.computercomponent.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorDetails {
    private static final String defaultStatus = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String defaultMessage = "Failed";

    private String responseCode;

    @Builder.Default
    private String responseMessage = defaultMessage;

    private Object responseData;
}
