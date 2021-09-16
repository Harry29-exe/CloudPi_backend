package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthorityPermission;

public enum UserAPIAuthorities {
    CREATE (new CPAuthorityPermission("USER_CREATE")),
    DELETE (new CPAuthorityPermission("USER_DELETE")),
    LOCK (new CPAuthorityPermission("USER_LOCK"));

    private final CPAuthorityPermission permission;

    UserAPIAuthorities(CPAuthorityPermission permission) {
        this.permission = permission;
    }

    public CPAuthorityPermission getPermission() {
        return permission;
    }
}
