package com.cloudpi.cloudpi_backend.authorities.endpoint;

import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Permission;

@ContainsPermissions
public class AuthoritiesAPIAuthorities {

    @Permission
    public static final String READ = "AUTHORITIES_READ";
    @Permission
    public static final String GIVE = "AUTHORITIES_GIVE";
    @Permission
    public static final String REMOVE = "AUTHORITIES_REMOVE";

}
