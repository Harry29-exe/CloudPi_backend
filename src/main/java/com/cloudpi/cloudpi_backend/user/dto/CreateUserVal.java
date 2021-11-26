package com.cloudpi.cloudpi_backend.user.dto;

import lombok.*;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

@Value
public class CreateUserVal {

    @NotBlank String username;
    @NotBlank String nonEncodedPassword;
    @NotBlank String nickname;
    @NonNull AccountType accountType;
    @Nullable String email;

}
