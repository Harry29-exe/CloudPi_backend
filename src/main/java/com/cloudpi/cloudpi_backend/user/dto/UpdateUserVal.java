package com.cloudpi.cloudpi_backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Value
public class UpdateUserVal {
    String username;
    AccountType accountType;
    UpdateDetails userDetails;

    public UpdateUserVal(String username,
                         AccountType accountType,
                         String email,
                         String pathToProfilePicture) {
        this.username = username;
        this.accountType = accountType;
        this.userDetails = new UpdateDetails(email, pathToProfilePicture);
    }

    @Value
    public static class UpdateDetails {
        String email;
        String pathToProfilePicture;
    }
}
