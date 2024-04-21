package com.computercomponent.api.service;

import com.computercomponent.api.dto.auth.UserRegistrationDto;
import com.computercomponent.api.request.EditAdminInforRequest;
import com.computercomponent.api.response.AdminInfoResponse;
import com.computercomponent.api.response.RegisterResponse;

import java.text.ParseException;

public interface AdminService {
    AdminInfoResponse getInfoAdmin();
    AdminInfoResponse editInfoAdmin(EditAdminInforRequest adminInfoRequest) throws ParseException;
    RegisterResponse createEmployeeAccount(UserRegistrationDto userRegistrationDto);
}
