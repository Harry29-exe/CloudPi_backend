package com.cloudpi.cloudpi_backend.authorities.endpoint.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
