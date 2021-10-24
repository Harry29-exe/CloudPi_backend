package com.cloudpi.cloudpi_backend.test.utils;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestUserDetailsService {
    private PasswordEncoder passwordEncoder;

    public TestUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

//    @Bean
//    @Primary
//    public UserDetailsService createTestUserService() {
//        var users = List.of(
//
//        )
//    }

//    private UserDetails createUser(String username, String nickname, String String password)

}
