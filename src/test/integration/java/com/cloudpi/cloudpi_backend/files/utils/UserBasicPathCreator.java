package com.cloudpi.cloudpi_backend.files.utils;

import com.cloudpi.cloudpi_backend.files.filesystem.entities.Directory;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VFilesystemRoot;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FilesystemRootRepo;
import com.cloudpi.cloudpi_backend.user.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class UserBasicPathCreator {

    @Autowired
    FilesystemRootRepo filesystemRootRepo;
    @Autowired
    UserRepository userRepository;

    public void createVirtualPathWithRootDir(Long userId, Long capacity) {
        var virtualDrive = new VFilesystemRoot(
                capacity,
                userRepository.getById(userId)
        );
        var rootDir = new Directory(null, virtualDrive, "/");
        virtualDrive.setRootDirectory(rootDir);

        filesystemRootRepo.save(virtualDrive);
    }

}
