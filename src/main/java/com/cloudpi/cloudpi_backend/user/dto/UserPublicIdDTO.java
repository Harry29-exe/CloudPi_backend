package com.cloudpi.cloudpi_backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPublicIdDTO {
    private String username;
    private String nickname;
    private AccountType accountType;
    private Boolean isLocked;
    private String pathToProfilePicture;
}
