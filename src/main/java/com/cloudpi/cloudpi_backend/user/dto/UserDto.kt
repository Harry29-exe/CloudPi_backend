package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.security.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private Boolean locked;
    private AccountType userRole;
    private List<String> permissions;
}