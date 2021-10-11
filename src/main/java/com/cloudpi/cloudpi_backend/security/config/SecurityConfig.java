package com.cloudpi.cloudpi_backend.security.config;

import com.cloudpi.cloudpi_backend.security.authentication.JWTService;
import com.cloudpi.cloudpi_backend.security.filters.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DaoAuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter authenticationFilter;


    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userService, JWTService jwtService) {
        authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userService);
        authenticationFilter = new JWTAuthenticationFilter(userService, jwtService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
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