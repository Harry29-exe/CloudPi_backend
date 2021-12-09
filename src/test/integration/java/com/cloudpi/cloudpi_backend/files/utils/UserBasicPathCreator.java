package com.cloudpi.cloudpi_backend.files.utils;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class UserBasicPathCreator {

    @Autowired
    VirtualDriveRepository virtualDriveRepository;
    @Autowired
    UserRepository userRepository;

    public void createVirtualPathWithRootDir(Long userId, Long capacity) {
        var virtualDrive = new VirtualDriveEntity(
                capacity,
                userRepository.getById(userId)
        );
        var rootDir = new DirectoryEntity(null, virtualDrive, "/");
        virtualDrive.setRootDirectory(rootDir);

        virtualDriveRepository.save(virtualDrive);
    }

}
