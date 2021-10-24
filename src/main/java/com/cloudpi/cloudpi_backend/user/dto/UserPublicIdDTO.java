package com.cloudpi.cloudpi_backend.user.dto;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPublicIdDTO {
    private String username;
    private AccountType accountType;
    private Boolean isLocked;
    private String pathToProfilePicture;
}
