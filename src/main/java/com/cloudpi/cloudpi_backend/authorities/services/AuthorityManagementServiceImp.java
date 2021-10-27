package com.cloudpi.cloudpi_backend.authorities.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityType;
import com.cloudpi.cloudpi_backend.authorities.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorities.repositories.RoleRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthorityManagementServiceImp implements AuthorityManagementService{
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public AuthorityManagementServiceImp(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void giveUserAuthority(String nickname, AuthorityDTO authority) {
        if(authority.type() == AuthorityType.ROLE) {
            roleRepository.giveUserRole(nickname, authority.authority());
        } else {
            permissionRepository.giveUserPermission(nickname, authority.authority());
        }
    }

    @Override
    public void giveUserAuthorities(String nickname, Collection<AuthorityDTO> authorities) {
        throw new NotImplementedException();
    }

    @Override
    public void removeUserAuthority(String nickname, AuthorityDTO authority) {
        throw new NotImplementedException();
    }

    @Override
    public void removeUserAuthorities(String nickname, Collection<AuthorityDTO> authority) {
        throw new NotImplementedException();
    }
}
