package com.cloudpi.cloudpi_backend.authorization.pojo;

import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthority;
import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiRole;
import com.cloudpi.cloudpi_backend.configuration.authorization.CPAuthoritiesAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class CPAuthorityFactory {

    private static AtomicReference<CPAuthorityFactory> factory;
    private final Map<String, CloudPiRole> roleMap;
    private final Map<String, CPAuthorityPermission> permissionMap;

    protected CPAuthorityFactory(Map<String, CloudPiRole> roleMap, Map<String, CPAuthorityPermission> permissionMap) {
        this.roleMap = roleMap;
        this.permissionMap = permissionMap;
        factory.set(this);
    }

    public static Set<CPAuthority> getAuthorities(String... rolesAndPermissions) {
        if(factory.get() == null) {
            createAndSetFactory();
        }
        var authorityFactory = factory.get();
        var roleMap = authorityFactory.roleMap;
        var permissionMap = authorityFactory.permissionMap;

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

    public static Set<CPAuthority> getRoleAuthorities(String role) {
        if(factory.get() == null) {
            createAndSetFactory();
        }
        var cpFactory = factory.get();
        var cpRole = cpFactory.roleMap.get(role);

        if(cpRole == null) {
            throw new IllegalArgumentException("Role with name: " + role + " does not exist");
        }

        Set<CPAuthority> authorities = new HashSet<>(cpRole.getPermissions());
        authorities.add(cpRole.getRole());

        return authorities;
    }

    public static CPAuthority getAuthority(String permission) {
        if(factory.get() == null) {
            createAndSetFactory();
        }
        var authority = factory.get().permissionMap.get(permission);
        if(authority == null) {
            throw new IllegalArgumentException("Permission with name: " + permission + " does not exist");
        }

        return authority;
    }



    private static void createAndSetFactory() {
        CPAuthoritiesAggregator aggregator = new CPAuthoritiesAggregator();
        new CPAuthorityFactory(aggregator.getAllRoles(), aggregator.getAllPermissions());
    }
}
