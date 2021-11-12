package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.utils.RepoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VirtualDriveServiceImp implements VirtualDriveService {

    private final Long defaultSpaceOnVirtualDrive;
    private final RepoService<UserEntity, Long> userEntityRepo;
    private final VirtualDriveRepository virtualDriveRepo;

    public VirtualDriveServiceImp(
            @Value("${cloud-pi.storage.default-space-on-virtual-drive}")
                    String spaceOnVD,
            RepoService<UserEntity, Long> userEntityRepo, VirtualDriveRepository virtualDriveRepo) {

        this.defaultSpaceOnVirtualDrive =
                Long.parseLong(spaceOnVD.replace("_", ""));
        this.userEntityRepo = userEntityRepo;
        this.virtualDriveRepo = virtualDriveRepo;
    }

    @Override
    public void createVirtualDriveAndRootDir(Long userId, Long driveSize) {
        var usersDrive = new VirtualDriveEntity(driveSize, userEntityRepo.getReference(userId).getReference());
        var rootDir = new DirectoryEntity(null, usersDrive, "/");
        usersDrive.setRootDirectory(rootDir);

        virtualDriveRepo.save(usersDrive);
    }

    @Override
    public void createVirtualDriveAndRootDir(Long userId) {
        this.createVirtualDriveAndRootDir(userId, this.defaultSpaceOnVirtualDrive);
    }
}
