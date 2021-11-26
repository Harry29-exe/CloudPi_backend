package com.cloudpi.cloudpi_backend.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class UpdateUserVal {
    String nickname;
    AccountType accountType;
    String email;
    String pathToProfilePicture;

}
