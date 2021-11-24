package com.cloudpi.cloudpi_backend.not_for_production;

import com.cloudpi.cloudpi_backend.authentication.CPUserDetailsService;
import com.cloudpi.cloudpi_backend.authentication.CPUserDetailsServiceImp;
import com.cloudpi.cloudpi_backend.authorities.repositories.PermissionRepository;
import com.cloudpi.cloudpi_backend.authorities.repositories.RoleRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FileService;
import com.cloudpi.cloudpi_backend.files.physical.entities.DiscEntity;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DiscRepository;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile({"dev"})
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
    @Autowired
    private DirectoryService dirService;
    @Autowired
    private CPUserDetailsService userDetailsService;
    @Autowired
    private FileService fileService;

    @Value("${test.value}")
    private String value;

    @PostConstruct
    public void init() {
        initRootUser();
        addDrive();
        System.out.println("\n\n" + value);
        addRootDirs();

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public void initRootUser() {
        var nickname = "mighty root";
        UserEntity userEntity = new UserEntity(
                "root",
                nickname,
                passwordEncoder.encode("123"),
                AccountType.ROOT,
                new UserDetailsEntity("root@cloud.pl", null),
                null,
                null
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
                new DiscEntity(pathToDisc)
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

    public void addRootDirs() {
        var root = userDetailsService
                .loadUserByUsername("mighty root");
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        root.getUsername(),
                        root.getPassword(),
                        root.getAuthorities()));
        dirService.createDirectory(new VirtualPath("mighty root/dir1"));
        dirService.createDirectory(new VirtualPath("mighty root/dir2"));
        dirService.createDirectory(new VirtualPath("mighty root/dir1/dir12"));
        dirService.createDirectory(new VirtualPath("mighty root/dir1/dir11"));
        dirService.createDirectory(new VirtualPath("mighty root/dir1/dir11/dir111"));
        dirService.createDirectory(new VirtualPath("mighty root/dir2/dir21"));
        fileService.createFile(new CreateFileDTO(
                new VirtualPath("mighty root/file1"), 5253343L, FileType.IMAGE));
        fileService.createFile(new CreateFileDTO(
                new VirtualPath("mighty root/file2"), 5253343L, FileType.MUSIC));
        fileService.createFile(new CreateFileDTO(
                new VirtualPath("mighty root/dir1/file11"), 5253343L, FileType.VIDEO));
        fileService.createFile(new CreateFileDTO(
                new VirtualPath("mighty root/dir1/file12"), 5253343L, FileType.COMPRESSED));
        fileService.createFile(new CreateFileDTO(
                new VirtualPath("mighty root/dir1/file13"), 5253343L, FileType.TEXT_FILE));
        fileService.createFile(new CreateFileDTO(
                new VirtualPath("mighty root/dir1/dir11/file1111"), 5253343L, FileType.IMAGE));
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
