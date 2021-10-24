package com.cloudpi.cloudpi_backend.user.requests;

import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
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
    private AccountType accountType = AccountType.USER;
    @Nullable
    private String email;

    public UserWithDetailsDTO toUserWithDetails() {
        return new UserWithDetailsDTO(
            null,
                username,
                false,
                accountType,
                new UserDetailsDTO(nickname, email, null)
        );
    }
}