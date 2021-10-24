package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import lombok.Data;

@Data
public class GetUserResponse {
    private String username;
    private String pathToProfilePicture;
    private AccountType userType;
}