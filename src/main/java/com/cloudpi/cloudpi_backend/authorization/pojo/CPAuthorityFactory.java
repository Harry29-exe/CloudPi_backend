package com.cloudpi.cloudpi_backend.authorization.pojo;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthority;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.CloudPiRole;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CPAuthorityFactory {

    private final Map<String, CloudPiRole> roleMap;
    private final Map<String, CPAuthorityPermission> permissionMap;

    private CPAuthorityFactory(Map<String, CloudPiRole> roleMap, Map<String, CPAuthorityPermission> permissionMap) {
        this.roleMap = roleMap;
        this.permissionMap = permissionMap;
    }

    public Set<CPAuthority> getAuthorities(String... rolesAndPermissions) {
        Set<CPAuthority> authorities = new HashSet<>();
        for (String roleOrPermission : rolesAndPermissions) {
            if(roleOrPermission.startsWith("ROLE")) {
                authorities.add(roleMap.get(roleOrPermission).getRole());
            } else {
                authorities.add(permissionMap.get(roleOrPermission));
            }
        }

        return authorities;
    }

    public Set<CPAuthority> getAuthorities(Collection<String> rolesAndPermissions) {
        Set<CPAuthority> authorities = new HashSet<>();
        for (String roleOrPermission : rolesAndPermissions) {
            if(roleOrPermission.startsWith("ROLE")) {
                authorities.add(roleMap.get(roleOrPermission).getRole());
            } else {
                authorities.add(permissionMap.get(roleOrPermission));
            }
        }

        return authorities;
    }

    public Set<CPAuthority> getRoleAuthorities(String role) {
          var cpRole = roleMap.get(role);


        if(cpRole == null) {
            throw new IllegalArgumentException("Role with name: " + role + " does not exist");
        }

        Set<CPAuthority> authorities = new HashSet<>(cpRole.getPermissions());
        authorities.add(cpRole.getRole());

        return authorities;
    }

    public CPAuthority getAuthority(String permission) {
        var authority = permissionMap.get(permission);
        if(authority == null) {
            throw new IllegalArgumentException("Permission with name: " + permission + " does not exist");
        }

        return authority;
    }

}
