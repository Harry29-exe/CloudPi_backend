package com.cloudpi.cloudpi_backend.user.controllers.responses;

import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import lombok.Data;

@Data
public class GetUserResponse {
    private String nickname;
    private AccountType userType;
}