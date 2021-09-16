package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiRole;
import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthority;

import java.util.Collection;

public interface RoleService {

    boolean isValidRole(String role);

    CloudPiRole getRole(String role);

    Collection<? extends CPAuthority> getRoleAuthorities(CloudPiRole role);

    Collection<? extends CPAuthority> getRoleAuthorities(String role);
}
