package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private Boolean locked;
    private AccountType accountType;
    private List<GrantedAuthority> permissions;

}