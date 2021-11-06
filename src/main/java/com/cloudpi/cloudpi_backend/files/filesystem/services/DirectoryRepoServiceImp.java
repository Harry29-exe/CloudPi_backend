package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectoryRepoServiceImp implements DirectoryRepoService{
    private final UserRepository userRepository;
    private final VirtualDriveRepository virtualDriveRepository;
    private final DirectoryRepository directoryRepository;

    public DirectoryRepoServiceImp(UserRepository userRepository,
                                   VirtualDriveRepository virtualDriveRepository,
                                   DirectoryRepository directoryRepository) {
        this.userRepository = userRepository;
        this.virtualDriveRepository = virtualDriveRepository;
        this.directoryRepository = directoryRepository;
    }

    @Override
    public void createDirectory(VirtualPath path) {
        var user = userRepository.findByUsername(path.getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var dir = new DirectoryEntity(
                user,
                directoryRepository.findByPath(path.getParentDirectoryPath())
                        //TODO change exception
                        .orElseThrow(IllegalArgumentException::new),
                virtualDriveRepository.findByOwner_Id(user.getId())
                        //TODO change exception
                        .orElseThrow(IllegalArgumentException::new),
                path.getEntityName(),
                path.getParentDirectoryPath() + path.getEntityName()
        );

        directoryRepository.save(dir);
    }

    @Override
    public DirectoryDto getDirectory(VirtualPath path, Integer fileStructureDepth) {
        return null;
    }

    @Override
    public DirectoryDto getDirectory(VirtualPath path) {
        return null;
    }

    @Override
    public void renameDirectory(VirtualPath path, String newName) {

    }

    @Override
    public void moveDirectory(VirtualPath path, VirtualPath newPath) {

    }

    @Override
    public void deleteDirectory(VirtualPath path) {

    }
}
