package com.cloudpi.cloudpi_backend.user.services.dto;

import lombok.Value;

@Value
public class UpdateUser {
    String username;
    String email;
    String pathToProfilePicture;

}
