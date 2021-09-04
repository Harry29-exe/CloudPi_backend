package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import lombok.Data;

import java.util.List;

@Data
public class GetUsersResponse {
    private String username;
    private String nickname;
    private String locked;
    private AccountType userRole;
    private List<String> permissions;
}
