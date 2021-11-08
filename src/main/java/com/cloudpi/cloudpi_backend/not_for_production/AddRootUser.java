package com.cloudpi.cloudpi_backend.not_for_production;

import com.cloudpi.cloudpi_backend.authorities.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorities.repositories.RoleRepository;
import com.cloudpi.cloudpi_backend.files.physical.entities.DiscEntity;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DiscRepository;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("dev")
public class AddRootUser {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private DriveRepository driveRepository;
    @Autowired
    private DiscRepository discRepository;
    @Autowired
    private VirtualDriveRepository virtualDriveRepository;

    @PostConstruct
    public void init() {
        initRootUser();
        addDrive();
    }

    public void initRootUser() {
        var nickname = "mighty root";
        UserEntity userEntity = new UserEntity(
                "root",
                nickname,
                passwordEncoder.encode("123"),
                new UserDetailsEntity("root@cloud.pl", null)
        );
        userEntity.setAccountType(AccountType.ROOT);
        userEntity.getUserDetails().setUser(userEntity);
        userRepository.save(userEntity);

        addAllAuthorities(nickname);
    }


    public void addDrive() {
        Long space = (long) Math.pow(10, 3);
        var pathToDisc = "/run/media/kamil/Nowy";
        var disc = discRepository.save(
                new DiscEntity(pathToDisc, space, space)
        );

        driveRepository.save(
                new DriveEntity(pathToDisc + "cloud_test", space, disc)
        );

        var root = userRepository.findByUsername("mighty root").orElseThrow();
        var virtualDrive = new VirtualDriveEntity();
        virtualDrive.setAssignedCapacity(10_000_000L);
        virtualDrive.setOwner(root);

        var dir = new DirectoryEntity(
                null,
                virtualDrive,
                "mighty root"
        );
        virtualDrive.setRootDirectory(dir);

        virtualDriveRepository.save(virtualDrive);
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
