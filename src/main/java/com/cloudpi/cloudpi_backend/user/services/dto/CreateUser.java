package com.cloudpi.cloudpi_backend.user.services.dto;

import lombok.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

@Value
public class CreateUser {

    @NotBlank String username;
    @NotBlank String nonEncodedPassword;
    @NotBlank String nickname;
    @Nullable
    String email;

}
