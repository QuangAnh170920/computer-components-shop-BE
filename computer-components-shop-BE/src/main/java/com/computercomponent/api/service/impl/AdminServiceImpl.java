package com.computercomponent.api.service.impl;

import com.computercomponent.api.dto.auth.UserRegistrationDto;
import com.computercomponent.api.request.EditAdminInforRequest;
import com.computercomponent.api.response.AdminInfoResponse;
import com.computercomponent.api.response.RegisterResponse;
import com.computercomponent.api.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Override
    public AdminInfoResponse getInfoAdmin() {
        return null;
    }

    @Override
    public AdminInfoResponse editInfoAdmin(EditAdminInforRequest adminInfoRequest) throws ParseException {
        return null;
    }

    @Override
    public RegisterResponse createEmployeeAccount(UserRegistrationDto userRegistrationDto) {
        return null;
    }
}
