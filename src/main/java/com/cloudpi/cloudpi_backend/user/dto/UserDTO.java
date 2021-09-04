package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private Boolean locked;
    private AccountType userRole;
    private List<String> permissions;
}