package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.disk.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DirectoryEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesystemRepositoryServiceImp implements FilesystemRepositoryService {
    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final DriveRepository driveRepository;
    private final VirtualDriveRepository virtualDriveRepository;

    public FilesystemRepositoryServiceImp(FileRepository fileRepository,
                                          DirectoryRepository directoryRepository,
                                          UserRepository userRepository,
                                          DriveRepository driveRepository,
                                          VirtualDriveRepository virtualDriveRepository) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
        this.userRepository = userRepository;
        this.driveRepository = driveRepository;
        this.virtualDriveRepository = virtualDriveRepository;
    }

    @Override
    public Long createFile(CreateFileDTO fileInfo) {

        var owner = userRepository
                .findByUsername(fileInfo.path().getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var fileEntity = new FileEntity(
                owner,
                directoryRepository.findByPath(fileInfo.path().getParentDirectoryPath())
                        //TODO change exception
                        .orElseThrow(IllegalStateException::new),
                virtualDriveRepository.findByOwner_Id(owner.getId())
                        .orElseThrow(IllegalStateException::new),
                driveRepository.getById(fileInfo.driveId()),
                fileInfo.path().getEntityName(),
                fileInfo.path().getParentDirectoryPath(),
                fileInfo.fileType() == null?
                        FileType.UNDEFINED:
                        fileInfo.fileType(),
                fileInfo.size()
                );
        return fileRepository.save(fileEntity).getId();
    }

    @Override
    public FileDto createFileAndReturn(CreateFileDTO fileInfo) {
        return null;
    }

    @Override
    public FileDto getFile(Long fileId) {
        return null;
    }

    @Override
    public FileDto getFile(VirtualPath path) {
        return null;
    }

    @Override
    public List<FileDto> getFilesByIds(List<Long> filesIds) {
        return null;
    }

    @Override
    public List<FileDto> getFiles(List<VirtualPath> filesPaths) {
        return null;
    }

    @Override
    public void updateFileSize(Long fileId, Long newSize) {

    }

    @Override
    public void updateFile(FileDto fileDto) {

    }

    @Override
    public void deleteFile(Long fileId) {

    }

    @Override
    public void deleteFiles(List<Long> filesIds) {

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
