package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsRoles;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Permission;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Role;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;

@ContainsPermissions
@ContainsRoles
public class UserAPIAuthorities {

    //TODO add git issue with empty CREATE string
    //TODO change this strings

    @Permission(havingItByDefault = AccountType.user)
    public static final String GET_DETAILS = "GET_USER_DETAILS";

    @Permission
    public static final String MODIFY = "MODIFY_USER_DETAILS";

    @Permission
    public static final String CREATE = "CREATE_USER";

    @Permission
    public static final String DELETE = "DELETE_USER";

    @Permission
    public static final String SCHEDULE_DELETE = "SCHEDULE_USER_DELETE";

    @Permission
    public static final String LOCK = "LOCK_USER";

    @Role(
            permissions = {GET_DETAILS, MODIFY, CREATE, SCHEDULE_DELETE, LOCK},
            havingItByDefault = AccountType.root
    )
    public static final String ADMIN = "USER_MANAGEMENT_ADMIN";

    @Role(
            permissions = {GET_DETAILS, LOCK, CREATE},
            mayBeGivenBy = {ADMIN}
    )
    public static final String MOD = "USER_MANAGEMENT_MODERATOR";
}
