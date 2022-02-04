package com.cloudpi.cloudpi_backend.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class UpdateUserDetailsRequest {

    @Nullable
    private String nickname;
    @Nullable
    private String email;
    @Nullable
    private String pathToProfilePicture;

}
