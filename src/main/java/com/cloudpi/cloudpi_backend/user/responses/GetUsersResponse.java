package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import lombok.Data;

@Data
public class GetUsersResponse {
    private String username;
    private String nickname;
    private String locked;
    private AccountType userRole;
}
