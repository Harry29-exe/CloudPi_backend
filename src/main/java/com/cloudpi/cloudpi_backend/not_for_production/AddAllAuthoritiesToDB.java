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
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Profile("!prod")
@Order(1)
public class AddAllAuthoritiesToDB {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final Logger logger = Logger.getLogger("DB DML authorities");

    public AddAllAuthoritiesToDB(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @PostConstruct
    public void addAllAuthoritiesToDB() {
        var allRoles = AuthorityModelsAggregator.getAllRoles()
                .stream()
                .map(role -> new RoleEntity(role.getAuthorityName(), null))
                .toList();

        roleRepository.saveAll(allRoles);
        logger.log(Level.CONFIG, "Added all permissions to DB");

        var allPermission = AuthorityModelsAggregator.getAllPermissions()
                .stream()
                .map(permission -> new PermissionEntity(permission.getAuthorityName(), null))
                .toList();
        permissionRepository.saveAll(allPermission);

        logger.log(Level.CONFIG, "Added all roles to DB");
    }

}
