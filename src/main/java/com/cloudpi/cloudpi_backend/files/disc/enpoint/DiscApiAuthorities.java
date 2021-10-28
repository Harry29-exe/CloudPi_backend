package com.cloudpi.cloudpi_backend.files.disc.enpoint;

import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Permission;

@ContainsPermissions
public class DiscApiAuthorities {

    @Permission
    public static final String GET_DISC_INFO = "GET_DISC_INFO";

    @Permission
    public static final String MODIFY_DISCS = "MODIFY_DISCS";

}
