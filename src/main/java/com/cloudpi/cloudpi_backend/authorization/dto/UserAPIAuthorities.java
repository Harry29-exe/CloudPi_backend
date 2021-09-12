package com.cloudpi.cloudpi_backend.authorization.dto;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;

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
