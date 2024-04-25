package com.computercomponent.api.service;

import com.computercomponent.api.common.EmailStatus;
import com.computercomponent.api.entity.EmailSenderLog;
import com.computercomponent.api.repository.EmailSenderLogRepository;
import com.computercomponent.api.response.RegisterResponse;
import com.computercomponent.api.until.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
@Slf4j
public class MailService {
    @Value("${spring.mail.username}")
    private String MAIL_SENDER;

    @Value("${spring.mail.default-param.mail-image-url}")
    private String mailIconImageLink;

    @Value("${spring.mail.default-param.banner-image-url}")
    private String bannerImageUrl;

    @Value("${spring.mail.default-param.line-image-url}")
    private String lineImageLink;

    @Value("${spring.mail.default-param.logo-image-url}")
    private String logoImageLink;

    @Value("${spring.mail.default-param.company}")
    private String company;

    @Value("${spring.mail.default-param.address}")
    private String address;

    @Value("${spring.mail.default-param.phoneNumber}")
    private String phoneNumber;

    @Value("${spring.mail.default-param.email}")
    private String email;

//    @Value("${url.fe-server}")
//    private String feServer;
    private static final String email_account_infor = "email_account_infor";
    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final SpringTemplateEngine templateEngine;
    private final EmailSenderLogRepository emailSenderLogRepository;
    public MailService(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine, EmailSenderLogRepository emailSenderLogRepository) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.emailSenderLogRepository = emailSenderLogRepository;
    }

    @Async
    public void sendEmail(String from, String to, String[] ccs, String[] bccs, String subject, String content, boolean isMultipart, boolean isHtml, String filePath) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        EmailSenderLog emailSenderLog = new EmailSenderLog();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(content, isHtml);
            if (ccs != null && ccs.length > 0) {
                message.setCc(ccs);
            }
            if (bccs != null && bccs.length > 0) {
                message.setBcc(bccs);
            }

            if (!DataUtil.isNullOrEmpty(filePath)){
                File file = new File(filePath);
                if (file.exists()){
                    byte[] fileContent = FileUtils.readFileToByteArray(file);
                    ByteArrayResource fileAsResource = new ByteArrayResource(fileContent);
                    // Add attachment
                    message.addAttachment("AttachmentName", fileAsResource);
                }
            }

            disableSslVerification();
            javaMailSender.send(mimeMessage);

            log.debug("Sent email success to User '{}'", to);
            // insert email sender log
            emailSenderLog.setSendFrom(from);
            emailSenderLog.setSendBccTo(bccs != null && bccs.length > 0 ? Arrays.toString(bccs).replace("[", "").replace("]", "") : null);
            emailSenderLog.setSendCcTo(ccs != null && ccs.length > 0 ? Arrays.toString(ccs).replace("[", "").replace("]", "") : null);
            emailSenderLog.setSendTo(to);
            emailSenderLog.setSubject(subject);
            emailSenderLog.setEmailContent(content);
            emailSenderLog.setErrorLog(null);
            emailSenderLog.setStatus(EmailStatus.SUCCESS);
            emailSenderLog = emailSenderLogRepository.save(emailSenderLog);
            log.info("insert email sender log: {}", emailSenderLog.getId());
        } catch (MailException | MessagingException | IOException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
            // insert email sender log
            emailSenderLog.setSendFrom(from);
            emailSenderLog.setSendBccTo(bccs != null && bccs.length > 0 ? Arrays.toString(bccs).replace("[", "").replace("]", "") : null);
            emailSenderLog.setSendCcTo(ccs != null && ccs.length > 0 ? Arrays.toString(ccs).replace("[", "").replace("]", "") : null);
            emailSenderLog.setSendTo(to);
            emailSenderLog.setSubject(subject);
            emailSenderLog.setEmailContent(content);
            emailSenderLog.setErrorLog(e.getMessage());
            emailSenderLog.setStatus(EmailStatus.FAIL);
            emailSenderLogRepository.save(emailSenderLog);
            log.info("insert email sender log: {}", emailSenderLog.getId());
        }
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HostnameVerifier allHostsValid = (hostname, session) -> {
                return true;
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void setMoreVariableContextObject(Context context, Map<String, Object> params) {
        if (params == null) {
            return;
        }
        for (Map.Entry<String, Object> param : params.entrySet()) {
            context.setVariable(param.getKey(), param.getValue());
        }
    }

    @Async
    public void sendMailAccountInfor(RegisterResponse registerResponse){
        Map<String, Object> params = new HashMap<>();
        params.put("customerName", registerResponse.getFullName());
        params.put("username", registerResponse.getMobile());
        params.put("password", registerResponse.getPassword());
        String subject = "[QAP Store] - Thông tin tài khoản";
        sendEmailFromTemplateObject(Collections.singletonList(registerResponse.getEmail()), null, null, email_account_infor, params, subject);
    }

    public void sendEmailFromTemplateObject(List<String> emailTo, String[] ccs, String[] bccs, String templateName, Map<String, Object> params, String subject) {
        Locale locale = LocaleContextHolder.getLocale();
        Context context = new Context(locale);
        params.putAll(setDefaultParam());
        setMoreVariableContextObject(context, params);
        String content = templateEngine.process(templateName, context);
        String from = Objects.requireNonNull(MAIL_SENDER);
        String to =  DataUtil.convertListStringToString(emailTo, ", ");
        sendEmail(from, to, ccs, bccs, subject, content, false, true, null);
    }

    public Map<String, Object> setDefaultParam(){
        Map<String, Object> params = new HashMap<>();
        params.put("bannerImageLink", bannerImageUrl);
        params.put("mailIconImageLink", mailIconImageLink);
        params.put("lineImageLink", lineImageLink);
        params.put("logoImageLink", logoImageLink);
        params.put("company", company);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
        params.put("email", email);
        return params;
    }
}
