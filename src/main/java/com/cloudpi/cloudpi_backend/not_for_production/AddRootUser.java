package com.cloudpi.cloudpi_backend.not_for_production;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.pojo.AuthorityType;
import com.cloudpi.cloudpi_backend.authorities.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorities.repositories.RoleRepository;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class AddRootUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void initRootUser() {
        var nickname = "mighty root";
        UserEntity userEntity = new UserEntity(
                "root",
                passwordEncoder.encode("123"),
                new UserDetailsEntity(nickname, "root@cloud.pl", null)
        );
        userEntity.setAccountType(AccountType.ROOT);
        userEntity.getUserDetails().setUser(userEntity);
        userRepository.save(userEntity);

        addAllAuthorities(nickname);
    }

    private void addAllAuthorities(String nickname) {
        AuthorityModelsAggregator.getAllRoles()
                .forEach(role -> roleRepository.giveUserRole(
                        nickname, role.getAuthorityName()));

        AuthorityModelsAggregator.getAllPermissions()
                .forEach(permission -> permissionRepository.giveUserPermission(
                        nickname, permission.getAuthorityName()));
    }
}
