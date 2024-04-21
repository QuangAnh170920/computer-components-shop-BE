package com.computercomponent.api.service.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("ComputerComponentShopAuthorizer")
@Slf4j
public class AppAuthorizerImpl implements AppAuthorizer{
    @Override
    public boolean authorize(Authentication authentication) {
        return true;
    }
}
