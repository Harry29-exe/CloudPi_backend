package com.cloudpi.cloudpi_backend.user.requests;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class PostUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    private String accountType = AccountType.USER;
    @Nullable
    private String email;
}