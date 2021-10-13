package com.cloudpi.cloudpi_backend.user.controllers.responses;

import org.springframework.lang.Nullable;

import java.util.List;

public record GetUserWithDetailsResponse(
        String username,
        String nickname,
        @Nullable String email,
        Boolean isLocked,
        String accountType,
        List<String> usersPermissions,
        List<String> usersRoles
        ) {
}
