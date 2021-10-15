package com.cloudpi.cloudpi_backend.user.enpoints.requests;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class UpdateUserDetailsRequest {

    @Nullable
    private String nickname;
    @Nullable
    private String email;

}
