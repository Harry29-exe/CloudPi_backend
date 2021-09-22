package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.CloudPiPermission;
import com.cloudpi.cloudpi_backend.security.Permissions;

@Permissions
public enum UserAPIAuthorities implements CloudPiPermission {
    GET,
    GET_SELF,
    MODIFY,
    CREATE,
    DELETE,
    DELETE_SELF,
    LOCK;

    private final CPAuthorityPermission permission;

    UserAPIAuthorities() {
        this.permission = new CPAuthorityPermission("USER_" + this.name());
    }

    public CPAuthorityPermission getPermission() {
        return permission;
    }
}
