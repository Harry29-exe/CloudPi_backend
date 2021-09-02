package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.security.AccountType;
import lombok.Data;

@Data
public class GetUserResponse {
    private String username;
    private String nickname;
    private AccountType userType;
    private String permissions;
}