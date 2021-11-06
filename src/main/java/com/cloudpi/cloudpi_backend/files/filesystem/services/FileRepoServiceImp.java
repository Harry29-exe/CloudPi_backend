package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers.FileMapper;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FileRepoServiceImp implements FileRepoService {
    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final DrivesService drivesService;
    private final DriveRepository driveRepository;
    private final VirtualDriveRepository virtualDriveRepository;

    public FileRepoServiceImp(FileRepository fileRepository,
                              DirectoryRepository directoryRepository,
                              UserRepository userRepository,
                              DrivesService drivesService,
                              DriveRepository driveRepository,
                              VirtualDriveRepository virtualDriveRepository) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
        this.userRepository = userRepository;
        this.drivesService = drivesService;
        this.driveRepository = driveRepository;
        this.virtualDriveRepository = virtualDriveRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UUID createFile(CreateFileDTO fileInfo) {

        var owner = userRepository
                .findByUsername(fileInfo.path().getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var fileDriveId =  drivesService.getDriveIdAndReserveSpaceOnIt(fileInfo.size());
        var fileEntity = new FileEntity(
                owner,
                directoryRepository.findByPath(fileInfo.path().getParentDirectoryPath())
                        //TODO change exception
                        .orElseThrow(IllegalStateException::new),
                virtualDriveRepository.findByOwner_Id(owner.getId())
                        .orElseThrow(IllegalStateException::new),
                new DriveEntity(fileDriveId),
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
    public FileDto createAndReturnFile(CreateFileDTO fileInfo) {
        var owner = userRepository
                .findByUsername(fileInfo.path().getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        var fileDriveId =  drivesService.getDriveIdAndReserveSpaceOnIt(fileInfo.size());
        var fileEntity = new FileEntity(
                owner,
                directoryRepository.findByPath(fileInfo.path().getParentDirectoryPath())
                        //TODO change exception
                        .orElseThrow(IllegalStateException::new),
                virtualDriveRepository.findByOwner_Id(owner.getId())
                        .orElseThrow(IllegalStateException::new),
                driveRepository.getById(fileDriveId),
                fileInfo.path().getEntityName(),
                fileInfo.path().getParentDirectoryPath() + fileInfo.path().getEntityName(),
                fileInfo.fileType() == null?
                        FileType.UNDEFINED:
                        fileInfo.fileType(),
                fileInfo.size()
        );

        var entity = fileRepository.save(fileEntity);
        return FileMapper.INSTANCE.fileEntityToDTO(entity);
    }

    @Override
    public FileDto getFile(UUID fileId) {
        return null;
    }

    @Override
    public FileDto getFile(VirtualPath path) {
        return null;
    }

    @Override
    public List<FileDto> getFilesByIds(List<UUID> filesIds) {
        return null;
    }

    @Override
    public List<FileDto> getFiles(List<VirtualPath> filesPaths) {
        return null;
    }

    @Override
    public void updateFileSize(UUID fileId, Long newSize) {

    }

    @Override
    public void updateFile(FileDto fileDto) {

    }

    @Override
    public void deleteFile(UUID fileId) {

    }

    @Override
    public void deleteFiles(List<UUID> filesIds) {

    }


}
