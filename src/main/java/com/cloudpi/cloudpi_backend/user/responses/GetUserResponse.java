package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import lombok.Data;

@Data
public class GetUserResponse {
    private String nickname;
    private AccountType userType;
}