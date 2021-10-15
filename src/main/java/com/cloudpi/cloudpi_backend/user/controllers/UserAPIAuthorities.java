package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority.annotations.ContainsRoles;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Permission;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Role;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@ContainsPermissions
@ContainsRoles
public class UserAPIAuthorities {

    //TODO add git issue with empty CREATE string
    //TODO change this strings



    @Permission(havingItByDefault = AccountType.USER)
    public static final String GET_DETAILS = "GET_USER_DETAILS";

    @Permission
    public static final String MODIFY = "MODIFY_USER_DETAILS";

    @Permission
    public static final String CREATE = "CREATE_USER";

    @Permission
    public static final String DELETE = "DELETE_USER";

    @Permission
    public static final String LOCK = "LOCK_USER";

    @Role(
            permissions = {GET_DETAILS, MODIFY, CREATE, DELETE, LOCK},
            havingItByDefault = AccountType.ROOT
    )
    public static final String ADMIN = "USER_MANAGEMENT_ADMIN";

    @Role(
            permissions = {GET_DETAILS, LOCK, CREATE},
            mayBeGivenBy = {ADMIN}
    )
    public static final String MOD = "USER_MANAGEMENT_MODERATOR";
}
