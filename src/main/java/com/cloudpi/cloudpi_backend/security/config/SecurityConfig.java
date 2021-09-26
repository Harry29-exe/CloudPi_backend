package com.cloudpi.cloudpi_backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final DaoAuthenticationProvider authenticationProvider;


    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userService) {
        this.passwordEncoder = passwordEncoder;
        authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return this.authenticationManager();
    }

}