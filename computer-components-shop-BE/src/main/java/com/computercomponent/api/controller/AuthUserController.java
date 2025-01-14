package com.computercomponent.api.controller;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.dto.UserPrincipal;
import com.computercomponent.api.dto.auth.JwtRequest;
import com.computercomponent.api.dto.auth.JwtResponse;
import com.computercomponent.api.dto.auth.UserRegistrationDto;
import com.computercomponent.api.entity.User;
import com.computercomponent.api.entity.exception.AccountDisableException;
import com.computercomponent.api.entity.exception.UnauthorizedException;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.repository.AdminRepository;
import com.computercomponent.api.repository.UserRepository;
import com.computercomponent.api.service.auth.AuthAdminService;
import com.computercomponent.api.service.auth.AuthUserService;
import com.computercomponent.api.until.JwtTokenUtil;
import com.computercomponent.api.until.ValidateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("user")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class AuthUserController {
    @Value("${jwt.prefix}")
    private String prefixToken;

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager userAuthenticationManager;
    private final AuthUserService authUserService;
    private final UserRepository userRepository;

    public AuthUserController(JwtTokenUtil jwtTokenUtil, AuthenticationManager userAuthenticationManager, AuthUserService authUserService, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userAuthenticationManager = userAuthenticationManager;
        this.authUserService = authUserService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Đăng ký User", description = "Đăng ký tài khoản mới cho User")
    @PostMapping("register")
    public ResponseEntity<ResponseWrapper> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        if (authUserService.existsByEmailOrPhone(userRegistrationDto.getEmail(), userRegistrationDto.getMobile())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email hoặc số điện thoại đã tồn tại");
        }

        User newUser = authUserService.registerNewUser(userRegistrationDto);
        return ResponseEntity.ok(new ResponseWrapper(newUser));
    }

    @Operation(summary = "Đăng nhập User", description = "Đăng nhập vào hệ thống dành cho User")
    @PostMapping("login")
    public ResponseEntity<ResponseWrapper> loginUser(@RequestBody @Valid JwtRequest jwtRequest) {
        Authentication authentication = authenticateUser(jwtRequest.getMobileOrEmail(), jwtRequest.getPassword());
        final String token = jwtTokenUtil.generateToken((UserPrincipal) authentication.getPrincipal());
        final String rfToken = jwtTokenUtil.generateRfToken((UserPrincipal) authentication.getPrincipal());
        return ResponseEntity.ok(new ResponseWrapper(new JwtResponse(
                String.format("%s %s", prefixToken, token), rfToken
        )));
    }

    public Authentication authenticateUser(String username, String password) {
        try {
            Optional<User> opt = ValidateUtil.regexValidation(username, Const.VALIDATE_INPUT.regexEmail)
                    ? userRepository.findOneByEmailIgnoreCaseAndDeleted(username, false)
                    : userRepository.findFirstByMobileAndDeleted(username, false);
            if (opt.isPresent()) {
                if (opt.get().getStatus().equals(UserStatus.DEACTIVATE)) {
                    throw new AccountDisableException(Const.MESSAGE_CODE.ACCOUNT_DISABLED);
                }
            } else {
                throw new UnauthorizedException(Const.MESSAGE_CODE.INVALID_CREDENTIALS);
            }
            return userAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(Const.MESSAGE_CODE.INVALID_CREDENTIALS);
        }
    }

}
