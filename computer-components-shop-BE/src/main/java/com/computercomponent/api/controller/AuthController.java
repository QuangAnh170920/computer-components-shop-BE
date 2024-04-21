package com.computercomponent.api.controller;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.dto.UserPrincipal;
import com.computercomponent.api.dto.auth.JwtRequest;
import com.computercomponent.api.dto.auth.JwtResponse;
import com.computercomponent.api.entity.Admin;
import com.computercomponent.api.entity.exception.AccountDisableException;
import com.computercomponent.api.entity.exception.UnauthorizedException;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.repository.AdminRepository;
import com.computercomponent.api.service.auth.AuthAdminService;
import com.computercomponent.api.until.JwtTokenUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("auth")
@SecurityRequirement(name = "computer-components-admin-security")
@PreAuthorize("{@ComputerComponentShopAuthorizer.authorize(authentication)}")
public class AuthController {
    @Value("${jwt.prefix}")
    private String prefixToken;

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final AuthAdminService authAdminService;
    private final AdminRepository adminRepository;

    public AuthController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, AuthAdminService authAdminService, AdminRepository adminRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.authAdminService = authAdminService;
        this.adminRepository = adminRepository;
    }

    @PostMapping("login")
    public ResponseEntity<ResponseWrapper> login(@RequestBody @Valid JwtRequest jwtRequest) {
        Authentication authentication = authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        final String token = jwtTokenUtil.generateToken((UserPrincipal) authentication.getPrincipal());
        final String rfToken = jwtTokenUtil.generateRfToken((UserPrincipal) authentication.getPrincipal());
        return ResponseEntity.ok(new ResponseWrapper(new JwtResponse(
                String.format("%s %s", prefixToken, token),rfToken
        )));
    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request) {
    }

    @GetMapping(value = "/otp")
    public ResponseEntity<ResponseWrapper> generateOtp(@RequestParam @NotNull String email) {
        authAdminService.sendNewOtp(email, null);
        return ResponseEntity.ok(new ResponseWrapper(null));
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<ResponseWrapper> activate(@RequestParam @NotNull String email, @NotNull @RequestParam String otp) {
        authAdminService.activate(email, otp);
        return ResponseEntity.ok(new ResponseWrapper(null));
    }

    @PutMapping("/reset-password-user")
    public ResponseEntity<ResponseWrapper> resetPasswordUser(@RequestParam Long customerId) {
        return ResponseEntity.ok(new ResponseWrapper(authAdminService.resetPasswordUser(customerId)));
    }

    @PostMapping("refresh-token")
    public ResponseEntity<ResponseWrapper> refreshToken(@RequestParam("tokenRefresh") @Valid String tokenRf) {

        return ResponseEntity.ok(new ResponseWrapper(new JwtResponse(
                String.format("%s %s", prefixToken, authAdminService.refreshToken(tokenRf)), ""
        )));
    }

    public Authentication authenticate(String username, String password) {
        try {
            Optional<Admin> opt = com.computercomponent.until.ValidateUtil.regexValidation(username, Const.VALIDATE_INPUT.regexEmail) ? adminRepository.findOneByEmailIgnoreCaseAndDeleted(username, false) : adminRepository.findFirstByMobileAndDeleted(username, false);
            if (opt.isPresent()) {
                if (opt.get().getStatus().equals(UserStatus.DEACTIVATE)) {
                    throw new AccountDisableException(Const.MESSAGE_CODE.ACCOUNT_DISABLED);
                }
            } else {
                throw new UnauthorizedException(Const.MESSAGE_CODE.INVALID_CREDENTIALS);
            }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(Const.MESSAGE_CODE.INVALID_CREDENTIALS);
        }
    }
}
