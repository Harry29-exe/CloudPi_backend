package com.cloudpi.cloudpi_backend.utils.mock_auth;

import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.security.authority_system.PermissionModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticationFactory {

    public static Authentication rootUser() {
        Set<GrantedAuthority> authorities = AuthorityModelsAggregator
                .getAllPermissions().stream()
                .map(PermissionModel::getAuthority)
                .collect(Collectors.toSet());
        AuthorityModelsAggregator
                .getAllRoles()
                .forEach(r ->
                    authorities.addAll(r.getAuthorities())
                );


        return new UsernamePasswordAuthenticationToken(
                "root",
                "123",
                authorities
        );
    }

}
