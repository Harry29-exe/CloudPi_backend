package com.cloudpi.cloudpi_backend.test.utils;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@TestConfiguration
public class TestUserDetailsService {
    private PasswordEncoder passwordEncoder;

    public TestUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static final String ROOT = "root";
    public static final String MOD = "mod";
    public static final String USER = "root";
    public static final String USER_WITHOUT_PERMISSIONS = "root";


//    @Bean
//    @Primary
//    public UserDetailsService createTestUserService() {
//        var users = List.of(
//            createUser()
//        )
//    }
//
//    private UserDetails createUser(String login, String nickname, AccountType accountType, String password) {
//        return new User()
//    }

}
