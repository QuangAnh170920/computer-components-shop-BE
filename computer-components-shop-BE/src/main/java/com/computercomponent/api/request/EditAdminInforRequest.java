package com.computercomponent.api.request;

import lombok.Data;

@Data
public class EditAdminInforRequest {
    private Long id;
    private String fullName;
    private String dateOfBirth;
    private Integer gender;
}
