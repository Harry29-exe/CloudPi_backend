package com.cloudpi.cloudpi_backend.security.authority_system;

import org.springframework.security.core.GrantedAuthority;

public interface PermissionModel extends AuthorityModel, Comparable<PermissionModel> {

    GrantedAuthority getAuthority();



}
