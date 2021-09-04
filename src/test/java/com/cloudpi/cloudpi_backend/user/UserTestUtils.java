package com.cloudpi.cloudpi_backend.user;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;

import java.util.List;

public class UserTestUtils {

    public static UserDTO createDefaultUser() {
        return UserDTO.builder()
                .userRole(AccountType.USER)
                .id(2L)
                .email("email")
                .locked(false)
                .permissions(List.of())
                .nickname("nickname")
                .password("password")
                .username("username").build();
    }

    public static UserDTO createRootUser() {
        return UserDTO.builder()
                .userRole(AccountType.ROOT)
                .id(1L)
                .email("email")
                .locked(false)
                .permissions(List.of())
                .nickname("root")
                .password("rootPassword")
                .username("root").build();
    }
}
