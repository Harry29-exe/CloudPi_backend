package com.cloudpi.cloudpi_backend.user.requests;

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

}
