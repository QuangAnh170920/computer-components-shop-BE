package com.computercomponent.api.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseWrapper {
    private static final String DEFAULT_STATUS = String.valueOf(HttpStatus.OK.value());
    private static final String DEFAULT_MESSAGE = "Thành công";


    private String responseCode;

    @Builder.Default
    private String responseMessage = DEFAULT_MESSAGE;

    private Object responseData;


    public ResponseWrapper(Object data) {
        this(DEFAULT_STATUS, DEFAULT_MESSAGE, data);
    }

    //
    public ResponseWrapper(String message, Object data) {
        this(DEFAULT_STATUS, message, data);
    }

    //
    public ResponseWrapper(String status, String message) {
        this(status, message, null);
    }
}
