package com.computercomponent.api.service.auth;

import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.dto.auth.UserRegistrationDto;
import com.computercomponent.api.entity.User;
import com.computercomponent.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmailOrPhone(String email, String mobile) {
        return userRepository.existsByEmailIgnoreCaseOrMobile(email, mobile);
    }

    public User registerNewUser(UserRegistrationDto userRegistrationDto) {
        if (existsByEmailOrPhone(userRegistrationDto.getEmail(), userRegistrationDto.getMobile())) {
            throw new IllegalArgumentException("Email hoặc số điện thoại đã tồn tại");
        }

        User newUser = new User();
        newUser.setFullName(userRegistrationDto.getFullName());
        newUser.setUsername(userRegistrationDto.getUsername());
        newUser.setEmail(userRegistrationDto.getEmail());
        newUser.setMobile(userRegistrationDto.getMobile());
        newUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setDeleted(false);

        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
