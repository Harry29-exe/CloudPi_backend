package com.cloudpi.cloudpi_backend.not_for_production;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.authorities.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorities.repositories.RoleRepository;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("!prod")
@Order(1)
@AllArgsConstructor
public class AddAllAuthoritiesToDB {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void addAllAuthoritiesToDB() {
        var allRoles = AuthorityModelsAggregator.getAllRoles()
                .stream()
                .map(role -> new RoleEntity(role.getAuthorityName(), null))
                .toList();
        System.out.println("\n\n" + allRoles.size() + "\n\n");
        roleRepository.saveAll(allRoles);
        var allPermission = AuthorityModelsAggregator.getAllPermissions()
                .stream()
                .map(permission -> new PermissionEntity(permission.getAuthorityName(), null))
                .toList();
        permissionRepository.saveAll(allPermission);
    }

}
