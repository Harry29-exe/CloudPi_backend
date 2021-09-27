package com.cloudpi.cloudpi_backend.authorization.services;

import com.cloudpi.cloudpi_backend.authorization.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorization.pojo.AuthorityType;
import com.cloudpi.cloudpi_backend.authorization.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorization.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorityRepoServiceImp implements AuthorityRepoService {
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public AuthorityRepoServiceImp(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Set<AuthorityDTO> getUsersAuthorities(String username) {
        Set<AuthorityDTO> authorities = new HashSet<>();
        roleRepository.getAllRolesByRoleHolder(username)
                .forEach(r -> authorities.add(new AuthorityDTO(AuthorityType.ROLE, r)));
        permissionRepository.getAuthoritiesByAuthorised(username)
                .forEach(p -> authorities.add(new AuthorityDTO(AuthorityType.PERMISSION, p)));


        return authorities;
    }

    @Override
    public Set<AuthorityDTO> getUsersAuthorities(Long userId) {
        return null;
    }
}
