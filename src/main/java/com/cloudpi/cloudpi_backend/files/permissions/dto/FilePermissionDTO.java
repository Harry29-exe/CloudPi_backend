package com.cloudpi.cloudpi_backend.files.permissions.dto;

import com.cloudpi.cloudpi_backend.files.permissions.pojo.FilePermissionType;

public record FilePermissionDTO(
        Long id,
        FilePermissionType permissionType,
        String username,
        Long filesystemId
) {
}
