package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.RoleClass;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Role;

public enum AccountType implements RoleClass {

    @Role(
            permissions = {},
            mayBeGivenBy = {})
    USER,

    @Role(
            permissions = {},
            mayBeGivenBy = {})
    SERVICE_WORKER,

    @Role(
            permissions = {},
            mayBeGivenBy = {})
    ROOT
    ;

    @Override
    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}
