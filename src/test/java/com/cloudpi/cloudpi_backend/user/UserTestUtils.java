package com.cloudpi.cloudpi_backend.user;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTestUtils {

    public final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static UserDTO createDefaultUserDTO() {
        return UserDTO.builder()
                .accountType(AccountType.USER)
                .id(2L)
                .email("email")
                .locked(false)
                .nickname("nickname")
                .password("password")
                .username("username").build();
    }

    public static UserDTO createRootUserDTO() {
        return UserDTO.builder()
                .accountType(AccountType.ROOT)
                .id(1L)
                .email("email")
                .locked(false)
                .nickname("root")
                .password("rootPassword")
                .username("root").build();
    }
}
