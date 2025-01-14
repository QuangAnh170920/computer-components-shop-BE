package com.computercomponent.api.config;

import com.computercomponent.api.filter.JwtAuthenticationEntryPoint;
import com.computercomponent.api.filter.JwtTokenFilter;
import com.computercomponent.api.service.auth.AuthAdminService;
import com.computercomponent.api.service.auth.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private AuthAdminService adminDetailsService;

    @Autowired
    private AuthUserService userDetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetailsService)
                .passwordEncoder(encoder());

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();  // Sử dụng AuthenticationManager mặc định của Spring Security
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers(
                        "/v3/api-docs/**", "/webjars/**", "/swagger-resources/**",
                        "/configuration/**", "/*.html", "/favicon.ico", "/**/*.html",
                        "/**/*.css", "/**/*.js", "/test", "/user/**",
                        "/swagger-ui*/**", "/auth/**", "/static-data/**",
                        "/v1/api/admin/upload/**"
                ).permitAll()
                .and().authorizeRequests()
                .antMatchers("/**").authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
