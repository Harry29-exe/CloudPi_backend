package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiPermission;
import com.cloudpi.cloudpi_backend.configuration.authorization.Permissions;

@Permissions
public enum UserAPIAuthorities implements CloudPiPermission {
    GET,
    GET_SELF,
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
