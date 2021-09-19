package com.cloudpi.cloudpi_backend.user.requests;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserDetailsRequest {

    @Nullable
    private String nickname;
    @Nullable
    private String email;

}
