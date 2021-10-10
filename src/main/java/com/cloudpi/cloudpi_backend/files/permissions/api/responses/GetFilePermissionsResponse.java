package com.cloudpi.cloudpi_backend.files.permissions.api.responses;

import com.cloudpi.cloudpi_backend.files.permissions.pojo.FilePermissionType;

import java.util.List;

public record GetFilePermissionsResponse(
        Long discObjectId,
        List<UserFilePermissions> permissions
) {

    public record UserFilePermissions (
            String username,
            List<FilePermissionType> usersPermissions
    ) {}
}
