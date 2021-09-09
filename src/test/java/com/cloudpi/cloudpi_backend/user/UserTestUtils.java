package com.cloudpi.cloudpi_backend.user;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserTestUtils {

    public final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static UserDTO createDefaultUserDTO() {
        return UserDTO.builder()
                .accountType(AccountType.USER)
                .id(2L)
                .email("email")
                .locked(false)
                .permissions(List.of())
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
                .permissions(List.of())
                .nickname("root")
                .password("rootPassword")
                .username("root").build();
    }
}
