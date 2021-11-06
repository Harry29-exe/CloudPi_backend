package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.files.PathNotFoundException;
import com.cloudpi.cloudpi_backend.exepctions.files.UserVirtualDriveNotFoundException;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers.DirectoryMapper;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DirectoryRepoServiceImp implements DirectoryRepoService{
    private final UserRepository userRepository;
    private final VirtualDriveRepository virtualDriveRepository;
    private final DirectoryRepository dirRepository;

    public DirectoryRepoServiceImp(UserRepository userRepository,
                                   VirtualDriveRepository virtualDriveRepository,
                                   DirectoryRepository dirRepository) {
        this.userRepository = userRepository;
        this.virtualDriveRepository = virtualDriveRepository;
        this.dirRepository = dirRepository;
    }

    @Override
    public void createDirectory(VirtualPath path) {
        var user = userRepository.findByUsername(path.getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var dir = new DirectoryEntity(
                user,
                dirRepository.findByPath(path.getParentDirectoryPath())
                        .orElseThrow(() -> PathNotFoundException.noSuchDirectory(path.getPath())),
                virtualDriveRepository.findByOwner_Id(user.getId())
                        .orElseThrow(() -> new UserVirtualDriveNotFoundException(path.getUsername())),
                path.getEntityName(),
                path.getParentDirectoryPath() + path.getEntityName()
        );

        dirRepository.save(dir);
    }

    @Override
    public DirectoryDto getDirectoryDto(VirtualPath path) {
        var dirEntity = dirRepository.findByPath(path.getPath())
                .orElseThrow(() -> PathNotFoundException.noSuchDirectory(path.getPath()));
        return DirectoryMapper.INSTANCE.directoryEntityToDto(dirEntity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void renameDirectory(VirtualPath path, String newName) {
        var directory = dirRepository.findByPath(path.getPath())
                .orElseThrow();
        var oldPath = path.getPath();
        var newPath = path.getParentDirectoryPath() + "/" + newName;
        this.renameDirectoryChildren(directory, oldPath, newPath);
        //TODO change this whole nonsense to UPDATE LIKE
    }

    @Override
    public void moveDirectory(VirtualPath path, VirtualPath newPath) {

    }

    @Override
    public void deleteDirectory(VirtualPath path) {

    }

    private void renameDirectoryChildren(DirectoryEntity dirEntity, String partToBeReplaced, String newPartOfPath) {
        dirEntity.getChildrenFiles().forEach(
                file -> file.setPath(file.getPath().replace(partToBeReplaced, newPartOfPath))
        );

        var childrenDirs = dirEntity.getChildrenDirectories();
        childrenDirs.forEach(
                dir -> dir.setPath(dir.getPath().replace(partToBeReplaced, newPartOfPath))
        );
        childrenDirs.forEach(
                dir -> renameDirectoryChildren(dir, partToBeReplaced, newPartOfPath)
        );
    }
}
