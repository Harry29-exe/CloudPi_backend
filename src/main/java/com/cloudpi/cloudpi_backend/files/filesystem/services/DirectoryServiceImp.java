package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.files.DirectoryNotEmptyException;
import com.cloudpi.cloudpi_backend.exepctions.files.PathNotFoundException;
import com.cloudpi.cloudpi_backend.exepctions.files.UserVirtualDriveNotFoundException;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers.DirectoryMapper;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.Directory;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepo;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FilesystemRootRepo;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.PathRepo;
import com.cloudpi.cloudpi_backend.user.domain.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.utils.EntityReference;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DirectoryServiceImp implements DirectoryService {
    private final UserRepository userRepository;
    private final FilesystemRootRepo filesystemRootRepo;
    private final DirectoryRepo dirRepository;
    private final PathRepo pathRepo;
//    private final FileInDBService fileInDBService;

    public DirectoryServiceImp(UserRepository userRepository,
                               FilesystemRootRepo filesystemRootRepo,
                               DirectoryRepo dirRepository,
                               PathRepo pathRepo
    ) {
        this.userRepository = userRepository;
        this.filesystemRootRepo = filesystemRootRepo;
        this.dirRepository = dirRepository;
        this.pathRepo = pathRepo;
//        this.fileInDBService = fileInDBService;
    }

    @Override
    @Transactional
    public DirectoryDto create(VirtualPath path) {
        var user = userRepository.findByUsername(path.getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var dir = new Directory(
                dirRepository.findByPath(path.getParentDirectoryPath())
                        .orElseThrow(() -> PathNotFoundException.noSuchDirectory(path.getPath())),
                filesystemRootRepo.findByOwner_Id(user.getId())
                        .orElseThrow(() -> new UserVirtualDriveNotFoundException(path.getUsername())),
                path.getParentDirectoryPath() + "/" + path.getEntityName()
        );

        var createdDir = dirRepository.save(dir);
        return DirectoryMapper.INSTANCE.directoryEntityToDto(createdDir);
    }

    @Override
    public DirectoryDto get(VirtualPath path) {
        var dirEntity = dirRepository.findByPath(path.getPath())
                .orElseThrow(() -> PathNotFoundException.noSuchDirectory(path.getPath()));
        return DirectoryMapper.INSTANCE.directoryEntityToDto(dirEntity);
    }

    @Override
    public void updateDirsAfterFileUpdate(VirtualPath modifiedFilePAth, Long fileSizeChange, Date fileModificationDate) {
        List<String> paths = new ArrayList<>();
        String lastPath = "";
        for (var dir : modifiedFilePAth.getDirectoriesInPath()) {
            lastPath = lastPath + "/" + dir;
            paths.add(lastPath);
        }

        dirRepository.updateDirsSizeAndModificationAt(fileModificationDate, fileSizeChange, paths);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void renameDirectory(VirtualPath path, String newDirName) {
        var dir = dirRepository.findByPath(path.getPath())
                .orElseThrow(PathNotFoundException::noSuchDirectory);
        dir.setName(newDirName);
        pathRepo.changeDirectoryPath(dir.getRoot().getId(), path.getPath(), path.getParentDirectoryPath() + "/" + newDirName);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void moveDirectory(VirtualPath path, VirtualPath newPath) {
        var dir = dirRepository.findByPath(path.getPath())
                .orElseThrow(PathNotFoundException::noSuchDirectory);
        var newParentDir = dirRepository.findByPath(newPath.getParentDirectoryPath())
                .orElseThrow(PathNotFoundException::noSuchDirectory);

        dir.setParent(newParentDir);
        pathRepo.changeDirectoryPath(dir.getRoot().getId(), path.getPath(), newPath.getPath());
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteDirectory(VirtualPath path) {
        var dir = dirRepository.findByPath(path.getPath())
                .orElseThrow(PathNotFoundException::noSuchDirectory);
        if (dirRepository.countChildren(dir.getId()) > 0) {
            throw new DirectoryNotEmptyException();
        }

        dirRepository.delete(dir);
    }

    @Override
    public void forceDeleteDirectory(VirtualPath path) {
//        var dir = dirRepository.findByPath(path.getPath())
//                .orElseThrow(PathNotFoundException::noSuchDirectory);
//        dir.getChildrenFiles().forEach(f ->
//                fileInDBService.deleteFile(f.getId()));
//        dir.getChildrenDirectories().forEach(d ->
//                this.forceDeleteDirectory(new VirtualPath(d.getPath()))
//        );
        throw new NotImplementedException();
    }

    @Override
    public EntityReference<Directory> getReference(UUID entityId) {
        return EntityReference.of(dirRepository.getById(entityId));
    }
}
