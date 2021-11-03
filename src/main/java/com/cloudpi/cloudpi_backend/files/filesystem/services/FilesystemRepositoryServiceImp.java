package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.disk.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.disk.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.files.disk.services.DrivesService;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FilesystemRepositoryServiceImp implements FilesystemRepositoryService {
    private FileRepository fileRepository;
    private DirectoryRepository directoryRepository;
    private UserRepository userRepository;
    private DriveRepository driveRepository;
    private VirtualDriveRepository virtualDriveRepository;

    @Override
    public void createFile(CreateFileDTO fileInfo, String username) {
        var dirPath = fileInfo.path().getParentDirectoryPath();

        var owner = userRepository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var fileEntity = new FileEntity(
                owner,
                directoryRepository.findByPath(fileInfo.path().getParentDirectoryPath())
                        //TODO change exception
                        .orElseThrow(IllegalStateException::new),
                virtualDriveRepository.findByOwner_Id(owner.getId())
                        .orElseThrow(IllegalStateException::new),
                driveRepository.getById(fileInfo.driveId()),
                fileInfo.path().getFileName(),
                fileInfo.fileType() == null?
                        FileType.UNDEFINED:
                        fileInfo.fileType(),
                fileInfo.size(),
                new Date(),
                new Date()
                );
        fileRepository.save(fileEntity);
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
