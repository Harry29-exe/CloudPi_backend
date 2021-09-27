package com.cloudpi.cloudpi_backend.authorization.pojo;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthority;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.CloudPiRole;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CPAuthorityFactory {

//    private static final AtomicReference<CPAuthorityFactory> factory = new AtomicReference<>();
    private final Map<String, CloudPiRole> roleMap;
    private final Map<String, CPAuthorityPermission> permissionMap;

    private CPAuthorityFactory(Map<String, CloudPiRole> roleMap, Map<String, CPAuthorityPermission> permissionMap) {
        this.roleMap = roleMap;
        this.permissionMap = permissionMap;
//        factory.set(this);
    }

    public Set<CPAuthority> getAuthorities(String... rolesAndPermissions) {
//        if(factory.get() == null) {
//            createAndSetFactory();
//        }
//        var roleMap = authorityFactory.roleMap;
//        var permissionMap = authorityFactory.permissionMap;

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
//        if(factory.get() == null) {
//            createAndSetFactory();
//        }
//        var roleMap = authorityFactory.roleMap;
//        var permissionMap = authorityFactory.permissionMap;

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
//        if(factory.get() == null) {
//            createAndSetFactory();
//        }
//        var cpFactory = factory.get();
//        var cpRole = cpFactory.roleMap.get(role);
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


//
//    private static void createAndSetFactory() {
//        CPAuthoritiesAggregator aggregator = new CPAuthoritiesAggregator();
//        new CPAuthorityFactory(aggregator.getAllRoles(), aggregator.getAllPermissions());
//    }
}
