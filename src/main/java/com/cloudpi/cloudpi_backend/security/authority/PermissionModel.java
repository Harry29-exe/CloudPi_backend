package com.cloudpi.cloudpi_backend.security.authority;

import org.springframework.security.core.GrantedAuthority;

public interface PermissionModel extends AuthorityModel {

    GrantedAuthority getAuthority();

}
