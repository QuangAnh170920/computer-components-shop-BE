package com.computercomponent.api.filter;

import com.computercomponent.api.common.Const;
import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.config.MessageTemplate;
import com.computercomponent.api.entity.Admin;
import com.computercomponent.api.repository.AdminRepository;
import com.computercomponent.api.until.JwtTokenUtil;
import com.computercomponent.until.ValidateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // TODO: Service should only contact service, in the manner of 'not breaking my tiny brain'.
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MessageTemplate messageTemplate;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.error("JWT Token has expired");
            }
        }

        // Once we get the token. Validate it
        if (Objects.nonNull(username)) {

            // TODO: recheck
            Optional<String> loggedUsernameOpt = Optional
                    .ofNullable(SecurityContextHolder.getContext().getAuthentication())
                    .map(Authentication::getName);

            if (!loggedUsernameOpt.isPresent() || !loggedUsernameOpt.get().equals(username)) {
                Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);

                Optional<Admin> opt = ValidateUtil.regexValidation(username, Const.VALIDATE_INPUT.regexEmail) ? adminRepository.findOneByEmailIgnoreCaseAndDeletedAndStatus(username, false, UserStatus.ACTIVE) : adminRepository.findFirstByMobileAndDeletedAndStatus(username, false, UserStatus.ACTIVE);

                if (opt.isPresent()) {
                    // If token is valid configure Spring Security to manually set authentication
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    opt.get(),
                                    null,
                                    null);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    /*
                     * After setting the Authentication in the context, we specify that the current user is
                     * authenticated. So it passes the Spring Security Configurations successfully
                     * */
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
