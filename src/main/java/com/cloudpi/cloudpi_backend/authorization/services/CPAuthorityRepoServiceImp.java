package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.pojo.CPAuthorityFactory;
import com.cloudpi.cloudpi_backend.authorization.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorization.repositories.RoleRepository;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CPAuthorityRepoServiceImp implements CPAuthorityRepoService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final CPAuthorityFactory cpAuthorityFactory;

    public CPAuthorityRepoServiceImp(RoleRepository roleRepository, PermissionRepository permissionRepository, CPAuthorityFactory cpAuthorityFactory) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.cpAuthorityFactory = cpAuthorityFactory;
    }

    @Override
    public Set<CPAuthority> getUsersAuthorities(String username) {
        Set<CPAuthority> authorities = new HashSet<>(cpAuthorityFactory.getAuthorities(
                roleRepository.getAllRolesByRoleHolder(username)
        ));
        authorities.addAll(cpAuthorityFactory.getAuthorities(
                permissionRepository.getAuthoritiesByAuthorised(username)
                ));
        return authorities;
    }

    @Override
    public Set<CPAuthority> getUsersAuthorities(Long userID) {
        Set<CPAuthority> authorities = new HashSet<>(cpAuthorityFactory.getAuthorities(
                roleRepository.getAllRolesByRoleHolder(userID)
        ));
        authorities.addAll(cpAuthorityFactory.getAuthorities(
                permissionRepository.getAuthoritiesByAuthorised(userID)
        ));
        return authorities;
    }
}
