package com.cloudpi.cloudpi_backend.authorities.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityType;
import com.cloudpi.cloudpi_backend.authorities.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorities.repositories.RoleRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

//todo test czy test po takich zmianach zacznie wreszcie nie być pozytywny
@Service
@Transactional
public class AuthorityManagementServiceImp implements AuthorityManagementService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public AuthorityManagementServiceImp(RoleRepository roleRepository, PermissionRepository permissionRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void giveUserAuthority(String username, AuthorityDTO authority) {
        if (authority.type() == AuthorityType.ROLE) {
            roleRepository.giveUserRole(username, authority.authority());
        } else {
            permissionRepository.giveUserPermission(username, authority.authority());
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
