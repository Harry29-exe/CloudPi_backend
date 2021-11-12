package com.cloudpi.cloudpi_backend.files.filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.files.FileNotFoundException;
import com.cloudpi.cloudpi_backend.exepctions.files.PathNotFoundException;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.mappers.FileMapper;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.FileEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.DirectoryRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FileRepository;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.VirtualDriveRepository;
import com.cloudpi.cloudpi_backend.files.physical.repositories.DriveRepository;
import com.cloudpi.cloudpi_backend.files.physical.services.DrivesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {
    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    private final DrivesService drivesService;
    private final DriveRepository driveRepository;
    private final VirtualDriveRepository virtualDriveRepository;

    public FileServiceImp(FileRepository fileRepository,
                          DirectoryRepository directoryRepository,
                          DrivesService drivesService,
                          DriveRepository driveRepository,
                          VirtualDriveRepository virtualDriveRepository) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
        this.drivesService = drivesService;
        this.driveRepository = driveRepository;
        this.virtualDriveRepository = virtualDriveRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UUID createFile(CreateFileDTO fileInfo) {
        var createdFile = this.createFileEntity(fileInfo);

        return createdFile.getId();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public FileDto createAndReturnFile(CreateFileDTO fileInfo) {
        var createdFile = this.createFileEntity(fileInfo);

        return FileMapper.INSTANCE.fileEntityToDTO(createdFile);
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
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteFile(UUID fileId) {
        var fileToDelete = fileRepository.findById(fileId)
                .orElseThrow(FileNotFoundException::new);

        drivesService.freeDriveSpace(fileToDelete.getDrive().getId(), fileToDelete.getSize());

    }

    @Override
    public void deleteFiles(List<UUID> filesIds) {

    }

    private FileEntity createFileEntity(CreateFileDTO fileInfo) {
        var fileDriveId = drivesService.getDriveIdAndReserveSpaceOnIt(fileInfo.size());
        var fileEntity = new FileEntity(
                directoryRepository.findByPath(fileInfo.path().getParentDirectoryPath())
                        .orElseThrow(PathNotFoundException::noSuchDirectory),
                virtualDriveRepository.findByOwner_Username(fileInfo.path().getUsername())
                        .orElseThrow(IllegalStateException::new),
                driveRepository.getById(fileDriveId),
                fileInfo.path().getParentDirectoryPath() + fileInfo.path().getEntityName(),
                fileInfo.fileType() == null ?
                        FileType.UNDEFINED :
                        fileInfo.fileType(),
                fileInfo.size()
        );


        return fileRepository.save(fileEntity);
    }


}
