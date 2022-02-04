package com.cloudpi.cloudpi_backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsDTO {

    private String email;
    private String pathToProfilePicture;
    private String nickname;
    private AccountType accountType;

}
