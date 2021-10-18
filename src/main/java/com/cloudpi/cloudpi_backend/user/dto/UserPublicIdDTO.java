package com.cloudpi.cloudpi_backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPublicIdDTO {
    private String nickname;
    private String accountType;
    private Boolean isLocked;
    private String pathToProfilePicture;
}
