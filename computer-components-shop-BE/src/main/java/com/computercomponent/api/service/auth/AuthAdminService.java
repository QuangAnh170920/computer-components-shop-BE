package com.computercomponent.api.service.auth;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.GenerateRandomToken;
import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.dto.UserPrincipal;
import com.computercomponent.api.dto.auth.AdminDto;
import com.computercomponent.api.dto.auth.OtpVerify;
import com.computercomponent.api.dto.auth.UserProfileDto;
import com.computercomponent.api.dto.auth.UserRegistrationDto;
import com.computercomponent.api.entity.Admin;
import com.computercomponent.api.entity.User;
import com.computercomponent.api.entity.exception.UnauthorizedException;
import com.computercomponent.api.mapper.AdminMapper;
import com.computercomponent.api.model.ResponseWrapper;
import com.computercomponent.api.repository.AdminRepository;
import com.computercomponent.api.repository.UserRepository;
import com.computercomponent.api.service.MailService;
import com.computercomponent.api.until.JwtTokenUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthAdminService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static LoadingCache<String, OtpVerify> otpCache;
    private static final Integer EXPIRE_MIN = 5;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final AdminMapper adminMapper = Mappers.getMapper(AdminMapper.class);

    public AuthAdminService() {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, OtpVerify>() {
                    @Override
                    public OtpVerify load(String s) throws Exception {
                        return null;
                    }
                });
    }

    public UserPrincipal findByEmail(String email) {
        Optional<Admin> userOpt = adminRepository.findOneByEmailIgnoreCaseAndDeletedAndStatus(email, false, UserStatus.ACTIVE);
        UserPrincipal userPrincipal;
        userPrincipal = new UserPrincipal();
        if (userOpt.isPresent()) {
            Admin admin = userOpt.get();
            Set<GrantedAuthority> authorities = new HashSet<>();
            userPrincipal.setUserId(admin.getAdminId());
            userPrincipal.setUsername(admin.getUsername());
            userPrincipal.setPassword(admin.getPassword());
            userPrincipal.setStatus(admin.getStatus());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

    public UserPrincipal findByMobile(String mobile) {
        Optional<Admin> userOpt = adminRepository.findOneByMobileIgnoreCaseAndDeletedAndStatus(mobile, false, UserStatus.ACTIVE);
        UserPrincipal userPrincipal;
        userPrincipal = new UserPrincipal();
        if (userOpt.isPresent()) {
            Admin admin = userOpt.get();
            Set<GrantedAuthority> authorities = new HashSet<>();

            userPrincipal.setUserId(admin.getAdminId());
            userPrincipal.setUsername(admin.getUsername());
            userPrincipal.setPassword(admin.getPassword());
            userPrincipal.setStatus(admin.getStatus());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (com.computercomponent.until.ValidateUtil.regexValidation(username, Const.VALIDATE_INPUT.regexEmail)) return findByEmail(username);
        return findByMobile(username);
    }

    @Transactional
    public void sendNewOtp(String email, String userName) {

        if (!ObjectUtils.isEmpty(email)) {
            Assert.isTrue(com.computercomponent.until.ValidateUtil.regexValidation(email, Const.VALIDATE_INPUT.regexEmail), Const.MESSAGE_CODE.INVALID_EMAIL);
            Admin admin = adminRepository.findOneByEmailIgnoreCaseAndDeletedAndStatus(email, false, UserStatus.ACTIVE).orElse(null);
            Assert.isNull(admin, Const.MESSAGE_CODE.ACCOUNT_EXISTED);
            Optional<Admin> userOld = adminRepository.findOneByEmailIgnoreCaseAndDeleted(email, false);
            if (userOld.isEmpty()) {
                throw new RuntimeException(Const.MESSAGE_CODE.EMAIL_NOT_REGISTER);
            }
        }

        String otpCode = com.computercomponent.until.OTPUtil.generateOtpCode();
        String subject = "[QAP Store] Xác nhận đăng ký tài khoản";
        String content = "<p>Xin Chào " + (userName != null ? userName : "")  + "</p>" + "<p>Mã OTP của bạn là:</p>" + "<p><b>" + otpCode
                + "</b></p>" + "<p>Note: Mã OTP sẽ hết hạn sau 5 phút nữa!.</p>"
                + "<p>Đây là mail tự động. Vui lòng không trả lời email này.</p>"
                + "<p>Trân trong!</p>";
        otpCache.invalidate(email);
        otpCache.put(email, new OtpVerify(otpCode, 0));
        mailService.sendEmail(mailFrom, email, null, null, subject, content, false, true, null);
    }

    public void activate(String email, String otp) {
        Assert.isTrue(com.computercomponent.until.ValidateUtil.regexValidation(email, Const.VALIDATE_INPUT.regexEmail), Const.MESSAGE_CODE.INVALID_EMAIL);
        OtpVerify otpVerify = otpCache.getIfPresent(email);
        Assert.notNull(otpVerify, Const.MESSAGE_CODE.OTP_EXPIRED);
        Assert.isTrue(otpVerify.getRetryCount() < 5, Const.MESSAGE_CODE.OTP_5_TIMES);
        Admin usersOURCE = adminRepository.findOneByEmailIgnoreCaseAndDeleted(email, false).orElse(null);
        if (usersOURCE == null || usersOURCE.getStatus() == UserStatus.ACTIVE) {
            throw new RuntimeException(Const.MESSAGE_CODE.ACCOUNT_ALREADY_ACTIVATED);
        }
        if (!otpVerify.getOtp().equals(otp)) {
            if (otpVerify.getRetryCount() < 5) {
                otpCache.invalidate(email);
                otpCache.put(email, new OtpVerify(otpVerify.getOtp(), otpVerify.getRetryCount() + 1));
                throw new IllegalArgumentException(Const.MESSAGE_CODE.OTP_INCORRECT);
            } else {
                throw new IllegalArgumentException(Const.MESSAGE_CODE.OTP_5_TIMES);
            }
        } else {
            Admin admin = adminRepository.findOneByEmailIgnoreCaseAndDeleted(email, false).orElse(null);
            if (admin != null) {
                admin.setStatus(UserStatus.ACTIVE);
                adminRepository.save(admin);
            }
        }
    }


    public Object resetPasswordUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(Const.RESET_PASSWORD.DEFAULT_RESET_PASSWORD));
            userRepository.save(user);
        }
        return userId;
    }


    @Transactional
    public ResponseWrapper changePassword(String currentClearTextPassword, String newPassword, String confirmNewPass) {
        Assert.isTrue(newPassword.equals(confirmNewPass), Const.MESSAGE_CODE.CONFIRMING_PASS_NOT_MATCH);
        ResponseWrapper result = new ResponseWrapper();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileDto currenUserLogin;
        try {
            currenUserLogin = adminMapper.map((Admin) principal);
        }catch (Exception e){
            throw new UnauthorizedException(Const.MESSAGE_CODE.INVALID_CREDENTIALS);
        }
        Admin changePassAdmin = adminRepository.findByAdminIdAndDeletedAndStatus(currenUserLogin.getUserId(), false, UserStatus.ACTIVE).orElse(null);
        Assert.notNull(changePassAdmin, Const.MESSAGE_CODE.USER_NOT_FOUND);
        String currentEncryptedPassword = changePassAdmin.getPassword();
        if (!new BCryptPasswordEncoder().matches(currentClearTextPassword, currentEncryptedPassword)) {
            throw new IllegalArgumentException(Const.MESSAGE_CODE.WRONG_PASSWORD);
        }
        changePassAdmin.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        adminRepository.save(changePassAdmin);
        result.setResponseData(null);
        result.setResponseMessage(Const.MESSAGE_CODE.OK);
        result.setResponseCode(String.valueOf(HttpStatus.OK));
        return result;
    }
    public String refreshToken(String tokenRf) {
        try {
            jwtTokenUtil.getExpirationDateFromRfToken(tokenRf);
            String username = jwtTokenUtil.getUsernameFromRfToken(tokenRf);
            UserPrincipal userPrincipal = new UserPrincipal();
            userPrincipal.setUsername(username);
            String token = jwtTokenUtil.generateToken(userPrincipal);
            return token;
        } catch (Exception e) {

            throw new UnauthorizedException(Const.MESSAGE_CODE.INVALID_CREDENTIALS);
        }
    }
}
