package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import org.springframework.lang.Nullable;

import java.util.List;

public record GetUserWithDetailsResponse(
        String username,
        String nickname,
        @Nullable String email,
        Boolean isLocked,
        AccountType accountType,
        List<String> usersPermissions,
        List<String> usersRoles
        ) {
}
