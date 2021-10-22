package com.cloudpi.cloudpi_backend.authorities.responses;

import java.util.List;

public record GetAuthoritiesInfoResponse (
        List<String> permissions,
        List<RoleInfo> roles
) {

    public static record RoleInfo(
            String roleName,
            List<String> aggregatedPermissions
    ) {}
}
