package com.cloudpi.cloudpi_backend.files.permissions.enpoints;

import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Permission;

@ContainsPermissions
public class FilePermissionAuthorities {

    @Permission
    public static final String GET_ANY = "GET_ANY";
}
